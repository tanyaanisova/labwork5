package Group;

import java.io.*;
import java.util.*;


public class Commander {

    private CollectionManager manager;
    private String userCommand;
    private String[] finalUserCommand;

    {
        userCommand = "";
    }

    public Commander(CollectionManager manager) {
        this.manager = manager;
    }
    public void interactiveMod() throws IOException {
        try(Scanner commandReader = new Scanner(System.in)) {
            while (!userCommand.equals("exit")) {
                userCommand = commandReader.nextLine();
                finalUserCommand = userCommand.trim().split(" ", 2);
                try {
                    switch (finalUserCommand[0]) {
                        case "":
                            break;
                        case "exit":
                            if (finalUserCommand.length > 1) {
                                if (!finalUserCommand[1].equals("")) System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            }
                            break;
                        case "help":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.help();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.help();
                            break;
                        case "info":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) System.out.println(manager.toString());
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else System.out.println(manager.toString());
                            break;
                        case "show":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.show();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.show();
                            break;
                        case "add":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.add();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.add();
                            break;
                        case "update":
                            manager.update(finalUserCommand[1]);
                            break;
                        case "remove_by_id":
                            manager.remove_by_id(finalUserCommand[1]);
                            break;
                        case "clear":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.clear();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.clear();
                            break;
                        case "save":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.save();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.save();
                            break;
                        case "execute_script":
                            manager.execute_script(finalUserCommand[1]);
                            break;
                        case "remove_first":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.remove_first();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.remove_first();
                            break;
                        case "add_if_max":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.add_if_max();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.add_if_max();
                            break;
                        case "remove_greater":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.remove_greater();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.remove_greater();
                            break;
                        case "sum_of_students_count":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.sum_of_students_count();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.sum_of_students_count();
                            break;
                        case "average_of_expelled_students":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.average_of_expelled_students();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.average_of_expelled_students();
                            break;
                        case "group_counting_by_group_admin":
                            if (finalUserCommand.length > 1) {
                                if (finalUserCommand[1].equals("")) manager.group_counting_by_group_admin();
                                else System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                            } else manager.group_counting_by_group_admin();
                            break;
                        default:
                            System.out.println("Неопознанная команда. Наберите 'help' для справки.");
                    }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("Отсутствует аргумент");
                }
            }
        }
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Commander)) return false;
        Commander commander = (Commander) o;
        return manager.equals(commander.manager);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(manager, userCommand);
        result = 27 * result + Arrays.hashCode(finalUserCommand);
        return result;
    }
}

