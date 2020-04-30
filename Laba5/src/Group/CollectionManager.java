package Group;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class CollectionManager {
    private ArrayDeque<StudyGroup> groups;
    private int GId;
    private ArrayDeque<String> scripts = new ArrayDeque<>();
    private File Collection;
    private Date initDate;
    private boolean wasStart;
    protected static HashMap<String, String> manual;

    {
        GId = 0;
        groups = new ArrayDeque<>();
        manual = new HashMap<>();
        manual.put("help","вывести справку по доступным командам");
        manual.put("info","вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)");
        manual.put("show","вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        manual.put("add {element}","добавить новый элемент в коллекцию");
        manual.put("update id {element}","обновить значение элемента коллекции, id которого равен заданному");
        manual.put("remove_by_id id","удалить элемент из коллекции по его id");
        manual.put("clear","очистить коллекцию");
        manual.put("save","сохранить коллекцию в файл");
        manual.put("execute_script file_name","считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме");
        manual.put("exit","завершить программу (без сохранения в файл)");
        manual.put("remove_first","удалить первый элемент из коллекции");
        manual.put("add_if_max {element}","добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции");
        manual.put("remove_greater {element}","удалить из коллекции все элементы, превышающие заданный");
        manual.put("sum_of_students_count","вывести сумму значений поля studentsCount для всех элементов коллекции");
        manual.put("average_of_expelled_students","вывести среднее значение поля expelledStudents для всех элементов коллекции");
        manual.put("group_counting_by_group_admin","сгруппировать элементы коллекции по значению поля groupAdmin, вывести количество элементов в каждой группе");
    }

    public CollectionManager(String collectionPath) throws IOException {
        try {
            File file = new File(collectionPath);
            if (file.exists()) {
                this.Collection = file;
            }
            else throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Путь до файла csv нужно передать через переменную окружения FILE. Файл по указанному пути не существует.");
            System.exit(1);
        }
        this.load();
        this.initDate = new Date();
        wasStart = true;
    }

    /**
     * Выводит на экран список доступных для пользователя команд
     */
    public void help() {
        System.out.println("Доступные к использованию команды:");
        manual.keySet().forEach(p -> System.out.println(p + " - " + manual.get(p)));
    }

    /**
     * Выводит все элементы коллекции
     */
    public void show() {
        if (groups.size() != 0) {
            groups.forEach(p -> System.out.println(p.toString()));
        }
        else System.out.println("В коллекции отсутствуют элементы. Выполнение команды невозможно.");
    }

    /**
     * Считывает новый элемент
     */
    public StudyGroup newElement() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Введите name: ");
        String name = reader.nextLine();
        while (name == null || name.equals("")) {
            System.out.println("Поле не может быть null или пустой строкой");
            System.out.print("Введите name: ");
            name = reader.nextLine();
        }
        System.out.println("Введите coordinates: ");
        String a;
        boolean p = true;
        long x = 0;
        while (p) {
            System.out.print("    Введите x: ");
            a = reader.nextLine();
            try {
                x = Long.parseLong(a);
                p = false;
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа Long");
            }
        }
        p = true;
        long y = 0;
        while (p) {
            System.out.print("    Введите y: ");
            a = reader.nextLine();
            try {
                y = Long.parseLong(a);
                p = false;
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа Long");
            }
        }
        int studentsCount = 0;
        while (!p) {
            System.out.print("Введите studentsCount: ");
            a = reader.nextLine();
            try {
                studentsCount = Integer.parseInt(a);
                if (studentsCount > 0)
                    p = true;
                else {
                    System.out.println("Значение поля должно быть больше 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа float");
            }
        }
        int expelledStudents = 0;
        while (p) {
            System.out.print("Введите expelledStudents: ");
            a = reader.nextLine();
            try {
                expelledStudents = Integer.parseInt(a);
                if (expelledStudents > 0)
                    p = false;
                else {
                    System.out.println("Значение поля должно быть больше 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа Integer");
            }
        }
        float averageMark = 0;
        while (!p) {
            System.out.print("Введите averageMark: ");
            a = reader.nextLine();
            try {
                averageMark = Float.parseFloat(a);
                if (averageMark > 0)
                    p = true;
                else {
                    System.out.println("Значение поля должно быть больше 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа float");
            }
        }
        System.out.print("Введите semesterEnum (SECOND, THIRD, FOURTH, SEVENTH): ");
        String semester_s = reader.nextLine();
        while (!semester_s.equals("SECOND") && !semester_s.equals("THIRD") && !semester_s.equals("FOURTH") && !semester_s.equals("SEVENTH")) {
            System.out.println("Значение поля неверное");
            System.out.print("Введите semesterEnum (SECOND, THIRD, FOURTH, SEVENTH): ");
            semester_s = reader.nextLine();
        }

        Semester semester = null;
        switch (semester_s) {
            case "SECOND":
                semester = Semester.SECOND;
                break;
            case "THIRD":
                semester = Semester.THIRD;
                break;
            case "FOURTH":
                semester = Semester.FOURTH;
                break;
            case "SEVENTH":
                semester = Semester.SEVENTH;
                break;
        }

        System.out.println("Введите groupAdmin: ");
        System.out.print("    Введите name: ");
        String nameAdmin = reader.nextLine();
        while (nameAdmin == null || nameAdmin.equals("")) {
            System.out.println("Поле не может быть null или пустой строкой ");
            System.out.print("    Введите name: ");
            nameAdmin = reader.nextLine();
        }
        float height = 0;
        while (p) {
            System.out.print("    Введите height: ");
            a = reader.nextLine();
            try {
                height = Float.parseFloat(a);
                if (height > 0)
                    p = false;
                else {
                    System.out.println("Значение поля должно быть больше 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа Float");
            }
        }
        double weight = 0;
        while (!p) {
            System.out.print("    Введите weight: ");
            a = reader.nextLine();
            try {
                weight = Double.parseDouble(a);
                if (weight > 0)
                    p = true;
                else {
                    System.out.println("Значение поля должно быть больше 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа Double");
            }
        }
        System.out.println("    Введите Location: ");
        double xLoc = 0;
        while (p) {
            System.out.print("        Введите xLoc: ");
            a = reader.nextLine();
            try {
                xLoc = Double.parseDouble(a);
                p = false;
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа Double");
            }
        }
        float yLoc = 0;
        while (!p) {
            System.out.print("        Введите yLoc: ");
            a = reader.nextLine();
            try {
                yLoc = Float.parseFloat(a);
                p = true;
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа float");
            }
        }
        float zLoc = 0;
        while (p) {
            System.out.print("        Введите zLoc: ");
            a = reader.nextLine();
            try {
                zLoc = Float.parseFloat(a);
                p = false;
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа float");
            }
        }
        System.out.print("        Введите name: ");
        String nameLoc = reader.nextLine();
        while (nameLoc == null || nameLoc.equals("")) {
            System.out.println("Поле не может быть null или пустой строкой ");
            System.out.print("        Введите name: ");
            nameLoc = reader.nextLine();
        }

        int id = 0;
        if (GId == 0) {
            while (!p) {
                p = true;
                Random random = new Random();
                id = random.nextInt(10000) + 1;
                for (StudyGroup h : groups) {
                    if (h.getId() == id) {
                        p = false;
                        break;
                    }
                }
            }
        } else {
            id = GId;
        }

        Date creationDate = new Date();
        System.out.println("Все значения элемента успешно получены!");
        return new StudyGroup(id, name, new Coordinates(x, y), creationDate, studentsCount, expelledStudents, averageMark, semester, new Person(nameAdmin, height, weight, new Location(xLoc, yLoc, zLoc, nameLoc)));
    }

    /**
     * Добавляет новый элемент в коллекцию
     */
    public void add() {
        groups.add(newElement());
        System.out.println("Элемент успешно добавлен");
    }

    /**
     * Обновляет значение элемента коллекции, id которого равен заданному
     * @param n : Id элемента, который требуется заменить
     */
    public void update(String n){
        if (groups.size() != 0) {
            try {
                int id = Integer.parseInt(n);
                boolean b = false;
                ArrayDeque<StudyGroup> st = new ArrayDeque<>();
                for (int i = 0; i < groups.size(); i++) {
                    StudyGroup p = groups.pop();
                    if (p.getId() == id) {
                        GId = id;
                        p = newElement();
                        st.push(p);
                        b = true;
                        break;
                    } else {
                        st.push(p);
                    }
                }
                if (!b) {
                    System.out.println("В коллекции не найдено элемента с указанным id.");
                } else {
                    GId = 0;
                    for (int i = 0; i < st.size(); i++) {
                        groups.push(st.pop());
                    }
                    System.out.println("Элемент коллекции успешно обновлен.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Аргумент не является значением типа int");
            }
        }
        else System.out.println("В коллекции отсутствуют элементы. Выполнение команды не возможно.");
    }

    /**
     * Удаляет элемент из коллекции по его id
     * @param n : Id элемента, который требуется удалить
     */
    public void remove_by_id(String n){
        if (groups.size() != 0) {
            try {
                int id = Integer.parseInt(n);
                boolean b = false;
                for (StudyGroup p : groups) {
                    if (p.getId() == id) {
                        groups.remove(p);
                        System.out.println("Элемент коллекции успешно удален.");
                        b = true;
                        break;
                    }
                }
                if (!b) System.out.println("В коллекции не найдено элемента с указанным id.");
            } catch (NumberFormatException ex) {
                System.out.println("Аргумент не является значением типа int");
            }
        }
        else System.out.println("В коллекции отсутствуют элементы. Выполнение команды не возможно.");
    }

    /**
     * Удаляет все элементы коллекции.
     */
    public void clear() {
        groups.clear();
        System.out.println("Коллекция очищена.");
    }

    /**
     * Сериализует коллекцию в файл csv.
     */
    public void save() {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(Collection))) {
            String s = "id,name,coordinate x,coordinate y,creation date,students count,expelled students,average mark,"+
                    "semester enum,group admin name,group admin height,group admin weight," +
                    "location x,location y,location z,location name\n";
            writer.write(s);
            for (StudyGroup p: groups) {
                s = p.getId() +","+ p.getName() +","+ p.getCoordinates().getX() +","+ p.getCoordinates().getY() +","+ p.getCreationDate() +","+
                        p.getStudentsCount() +","+ p.getExpelledStudents() +","+ p.getAverageMark() +","+ p.getSemesterEnum().toString() +","+
                        p.getGroupAdmin().getName()+","+ p.getGroupAdmin().getHeight() +","+ p.getGroupAdmin().getWeight() +","+
                        p.getGroupAdmin().getLocation().getX() +","+ p.getGroupAdmin().getLocation().getY() +","+
                        p.getGroupAdmin().getLocation().getZ() +","+ p.getGroupAdmin().getLocation().getName() + "\n";
                writer.write(s);
            }
            System.out.println("Коллекция успешно сохранена в файл.");
        } catch (Exception ex) {
            System.out.println("Возникла непредвиденная ошибка. Коллекция не может быть записана в файл");
        }
    }

    /**
     * Считывает и исполняет скрипт из указанного файла.
     * В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме
     */
    public void execute_script(String file) throws IOException {
        try {
            String userCommand;
            String[] finalUserCommand;
            {
                userCommand = "";
            }
            if (file.charAt(0) != '/') file = System.getenv("PWD") + "/" + file;
            if (scripts.contains(file)) throw new RecursiveException();
            scripts.push(file);
            try (BufferedReader commandReader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(file))))) {
                while (!userCommand.equals("exit") && commandReader.ready()) {
                    userCommand = commandReader.readLine();
                    finalUserCommand = userCommand.trim().split(" ", 2);
                    try {
                        switch (finalUserCommand[0]) {
                            case "":
                                break;
                            case "exit":
                                if (finalUserCommand.length > 1) {
                                    if (!finalUserCommand[1].equals(""))
                                        System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                }
                                break;
                            case "help":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) help();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else help();
                                break;
                            case "info":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) System.out.println(toString());
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else System.out.println(toString());
                                break;
                            case "show":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) show();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else show();
                                break;
                            case "add":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) add();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else add();
                                break;
                            case "update":
                                update(finalUserCommand[1]);
                                break;
                            case "remove_by_id":
                                remove_by_id(finalUserCommand[1]);
                                break;
                            case "clear":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) clear();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else clear();
                                break;
                            case "save":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) save();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else save();
                                break;
                            case "execute_script":
                                execute_script(finalUserCommand[1]);
                                break;
                            case "remove_first":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) remove_first();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else remove_first();
                                break;
                            case "add_if_max":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) add_if_max();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else add_if_max();
                                break;
                            case "remove_greater":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) remove_greater();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else remove_greater();
                                break;
                            case "sum_of_students_count":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) sum_of_students_count();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else sum_of_students_count();
                                break;
                            case "average_of_expelled_students":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) average_of_expelled_students();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else average_of_expelled_students();
                                break;
                            case "group_counting_by_group_admin":
                                if (finalUserCommand.length > 1) {
                                    if (finalUserCommand[1].equals("")) group_counting_by_group_admin();
                                    else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                                } else group_counting_by_group_admin();
                                break;
                            default:
                                System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                        }
                    } catch (ArrayIndexOutOfBoundsException ex) {
                        System.out.println("Отсутствует аргумент");
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Скрипт по указанному пути не существует");
            scripts.pop();
        } catch (SecurityException ex) {
            System.out.println("Файл защищён от чтения и/или записи. Для работы программы нужны оба разрешения.");
            scripts.pop();
        } catch (RecursiveException ex) {
            System.out.println("Могло произойти зацикливание при исполнении скрипта: " + file + "\nКоманда не будет выполнена. Переход к следующей команде");
        }
    }

    /**
     * Удаляет элемент из коллекции по его индексу
     */
    public void remove_first() {
        if (groups.size() != 0) {
            groups.poll();
            System.out.println("Элемент коллекции успешно удален");
        } else System.out.println("В коллекции отсутствуют элементы. Выполнение команды не возможно");
    }

    /**
     * Добавляет новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции
     */
    public void add_if_max() {
        if (groups.size() != 0) {
            StudyGroup group = newElement();
            boolean b = true;
            for (StudyGroup p: groups) {
                if (group.compareTo(p) < 0) b = false;
            }
            if (b) {
                groups.push(group);
                System.out.println("Элемент успешно добавлен!");
            }
            else System.out.println("Не удалось добавить элемент.");
        }
        else System.out.println("Элемент не с чем сравнивать. Коллекция пуста.");
    }

    /**
     * Удаляет из коллекции все элементы, превышающие заданный
     */
    public void remove_greater() {
        if (groups.size() != 0) {
            StudyGroup group = newElement();
            groups.removeIf(p -> p.compareTo(group) > 0);
            System.out.println("Команда выполнена");
        }
        else System.out.println("Элемент не с чем сравнивать. Коллекция пуста.");
    }

    /**
     * Выводит сумму значений поля studentsCount для всех элементов коллекции
     */
    public void sum_of_students_count() {
        if (groups.size() != 0) {
            int sum = 0;
            for (StudyGroup p: groups) {
                sum += p.getStudentsCount();
            }
            System.out.println("Cумма значений studentsCount всех элементов коллекции: "+ sum);
        }
        else System.out.println("Коллекция пуста.");
    }

    /**
     * Группирует элементы коллекции по значению поля groupAdmin, выводит количество элементов в каждой группе
     */
    public void group_counting_by_group_admin() {
        if (groups.size() != 0) {
            HashMap<Person,Integer> hm = new HashMap<>();
            for (StudyGroup p: groups) {
                if (hm.containsKey(p.getGroupAdmin())) {
                    hm.replace(p.getGroupAdmin(),hm.get(p.getGroupAdmin()) + 1);
                } else {
                    hm.put(p.getGroupAdmin(), 1);
                }
            }
            hm.forEach((p,k) -> System.out.println("Значение поля groupAdmin: " + p.toString() + "\nКоличество элементов в группе: " + k));
        }
        else System.out.println("Коллекция пуста.");
    }

    /**
     * Выводит среднее значение поля expelledStudents для всех элементов коллекции
     */
    public void average_of_expelled_students() {
        if (groups.size() != 0) {
            float average = 0;
            for (StudyGroup p: groups) {
                average += p.getStudentsCount();
            }
            System.out.print("Среднее значение поля expelledStudents всех элементов коллекции: ");
            System.out.printf("%.2f", average/groups.size());
            System.out.println();
        }
        else System.out.println("Коллекция пуста.");
    }


    /**
    *  Десериализует коллекцию из файла csv.
    * @throws IOException если файл пуст или защищён.
    */
    public void load() throws IOException {
        int beginSize = groups.size();
        try {
            if (!Collection.exists()) throw new FileNotFoundException();
        } catch (FileNotFoundException ex) {
            System.out.println("Файла по указанному пути не существует.");
            if (!wasStart) System.exit(1);
            else return;
        }
        try {
            if (!Collection.canRead() || !Collection.canWrite()) throw new SecurityException();
        } catch (SecurityException ex) {
            System.out.println("Файл защищён от чтения и/или записи. Для работы программы нужны оба разрешения.");
            if (!wasStart) System.exit(1);
            else return;
        }
        try {
            if (Collection.length() == 0) throw new SecurityException();
        } catch (SecurityException ex) {
            System.out.println("Файл пуст.");
            if (!wasStart) System.exit(1);
            else return;
        }
        try (BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(Collection)))) {
            System.out.println("Идёт загрузка коллекции " + Collection.getAbsolutePath());
            String nextLine = inputStreamReader.readLine();
            nextLine = inputStreamReader.readLine();
            String[] data;
            try {
                while (nextLine != null) {
                    data = nextLine.split(",");
                    int id = Integer.parseInt(data[0]);
                    String name = data[1];
                    long x = Long.parseLong(data[2]);
                    long y = Long.parseLong(data[3]);
                    int m = 0;
                    switch (data[4].substring(4,7)) {
                        case "Jan":
                            m=1;
                            break;
                        case "Feb":
                            m=2;
                            break;
                        case "Mar":
                            m=3;
                            break;
                        case "Apr":
                            m=4;
                            break;
                        case "May":
                            m=5;
                            break;
                        case "Aug":
                            m=8;
                            break;
                        case "Oct":
                            m=10;
                            break;
                        case "Nov":
                            m=11;
                            break;
                        case "Dec":
                            m=12;
                            break;
                    }
                    switch (data[4].substring(4,8)) {
                        case "June":
                            m = 6;
                            break;
                        case "July":
                            m = 7;
                            break;
                        case "Sept":
                            m = 9;
                            break;
                    }
                    String date = data[4].substring(8,10) + "-" + m + "-" + data[4].substring(24) + " " +
                            data[4].substring(11,19);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
                    formatter.setTimeZone(TimeZone.getTimeZone("GMT+03:00"));
                    Date creationDate = formatter.parse(date);
                    int studentsCount = Integer.parseInt(data[5]);
                    int expelledStudents = Integer.parseInt(data[6]);
                    float averageMark = Float.parseFloat(data[7]);
                    Semester semester = null;
                    switch (data[8]) {
                        case "SECOND":
                            semester = Semester.SECOND;
                            break;
                        case "THIRD":
                            semester = Semester.THIRD;
                            break;
                        case "FOURTH":
                            semester = Semester.FOURTH;
                            break;
                        case "SEVENTH":
                            semester = Semester.SEVENTH;
                            break;
                    }
                    String nameAdmin = data[9];
                    float height = Float.parseFloat(data[10]);
                    double weight = Double.parseDouble(data[11]);
                    double xLoc = Double.parseDouble(data[12]);
                    float yLoc = Float.parseFloat(data[13]);
                    float zLoc = Float.parseFloat(data[14]);
                    String nameLoc = data[15];

                    groups.push(new StudyGroup(id, name, new Coordinates(x, y), creationDate, studentsCount, expelledStudents, averageMark, semester, new Person(nameAdmin, height, weight, new Location(xLoc, yLoc, zLoc, nameLoc))));
                    //
                    nextLine = inputStreamReader.readLine();
                }
            } catch (IOException | ParseException | NumberFormatException ex) {
                System.out.println("IOException. Ошибка синтаксиса CSV. Коллекция не может быть загружена.");
                System.exit(1);
            }
            System.out.println("Коллекция успешно загружена. Добавлено " + (groups.size() - beginSize) + " элементов.");
        }
    }



    /**
     * Выводит информацию о коллекции.
     */
    @Override
    public String toString() {
        return "Тип коллекции: " + groups.getClass() +
                "\nДата инициализации: " + initDate +
                "\nКоличество элементов: " + groups.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CollectionManager)) return false;
        CollectionManager manager = (CollectionManager) o;
        return groups.equals(manager.groups) &&
                Collection.equals(manager.Collection) &&
                initDate.equals(manager.initDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groups, initDate);
    }
}
