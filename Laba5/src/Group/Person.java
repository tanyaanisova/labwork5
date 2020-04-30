package Group;

import java.util.Objects;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Float height; //Поле может быть null, Значение поля должно быть больше 0
    private Double weight; //Поле не может быть null, Значение поля должно быть больше 0
    private Location location; //Поле не может быть null

    Person(String name, Float height, Double weight, Location location) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.location = location;
    }

    public Double getWeight() {
        return weight;
    }
    public Float getHeight() {
        return height;
    }
    public Location getLocation() {
        return location;
    }
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Person {" +
                "name: " + name + ", height: " + height + ", weight: " + weight + ", location: " + location.toString() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return name.equals(person.getName()) &&
                height.equals(person.getHeight()) &&
                weight.equals(person.getWeight()) &&
                location.equals(person.getLocation());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, weight, location);
    }
}
