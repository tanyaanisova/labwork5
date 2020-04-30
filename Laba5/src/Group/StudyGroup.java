package Group;

import java.util.*;

public class StudyGroup {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int studentsCount; //Значение поля должно быть больше 0
    private Integer expelledStudents; //Значение поля должно быть больше 0, Поле не может быть null
    private float averageMark; //Значение поля должно быть БОЛЬШЕ 0
    private Semester semesterEnum; //Поле не может быть null
    private Person groupAdmin; //Поле не может быть null

    public StudyGroup(int id, String name, Coordinates coordinates, Date creationDate, int studentsCount, Integer expelledStudents, float averageMark, Semester semesterEnum, Person groupAdmin){
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.studentsCount = studentsCount;
        this.expelledStudents = expelledStudents;
        this.averageMark = averageMark;
        this.semesterEnum = semesterEnum;
        this.groupAdmin = groupAdmin;
    }

    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Coordinates getCoordinates(){
        return coordinates;
    }
    public Date getCreationDate() {
        return creationDate;
    }
    public int getStudentsCount() {
        return studentsCount;
    }
    public Integer getExpelledStudents() {
        return expelledStudents;
    }
    public float getAverageMark() {
        return averageMark;
    }
    public Semester getSemesterEnum() {
        return semesterEnum;
    }
    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public int compareTo(StudyGroup e) {
        int rez = semesterEnum.compareTo(e.getSemesterEnum());
        if (rez == 0) rez = (int) (averageMark - e.getAverageMark());
        if (rez == 0) rez = studentsCount - e.getStudentsCount();
        if (rez == 0) rez = e.getExpelledStudents() - expelledStudents;
        if (rez == 0) rez = name.compareTo(e.getName());
        if (rez == 0) rez = groupAdmin.getName().compareTo(e.getGroupAdmin().getName());
        return rez;
    }

    @Override
    public String toString() {
        return "StudyGroup {" +
                "id: " + id + ", name: " + name + ", coordinates: " + coordinates.toString() + ", creation date: " + creationDate.toString() + ", students count: "
                + studentsCount + ", expelled students: " + expelledStudents + ", average mark: " + averageMark +
                ", semester enum: " + semesterEnum.toString() + ", group admin: " + groupAdmin.toString() + "}";
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudyGroup)) return false;
        StudyGroup group = (StudyGroup) o;
        return id == group.getId() &&
                studentsCount == group.getStudentsCount() &&
                averageMark == group.getAverageMark() &&
                name.equals(group.getName()) &&
                coordinates.equals(group.getCoordinates()) &&
                creationDate.equals(group.getCreationDate()) &&
                expelledStudents.equals(group.getExpelledStudents()) &&
                semesterEnum.equals(group.getSemesterEnum()) &&
                groupAdmin.equals(group.getGroupAdmin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, studentsCount, expelledStudents, averageMark, semesterEnum, groupAdmin);
    }
}