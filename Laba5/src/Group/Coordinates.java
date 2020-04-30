package Group;

import java.util.Objects;

public class Coordinates {
    private Long x; //Поле не может быть null
    private long y;

    Coordinates(Long x, long y){
        this.x = x;
        this.y = y;
    }

    public Long getX() {
        return x;
    }
    public long getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Coordinates {" +
                "x: " + x + ", y: " + y +  "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates coordinates = (Coordinates) o;
        return y == coordinates.getY() &&
                x.equals(coordinates.getX());
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
