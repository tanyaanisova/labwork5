package Group;

import java.util.Objects;

public class Location {
        private Double x; //Поле не может быть null
        private float y;
        private float z;
        private String name; //Поле не может быть null

        Location(Double x, float y, float z, String name) {
                this.x = x;
                this.y = y;
                this.z = z;
                this.name = name;
        }

        public String getName() {
                return name;
        }
        public Double getX() {
                return x;
        }
        public float getY() {
                return y;
        }
        public float getZ() {
                return z;
        }

        @Override
        public String toString() {
                return "Location { " +
                        "x: " + x + ", y: " + y + ", z: " + z + ", name: " + name + "}";
        }

        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (!(o instanceof Location)) return false;
                Location location = (Location) o;
                return x.equals(location.getX()) &&
                        y == location.getY() &&
                        z == location.getZ() &&
                        name.equals(location.getName());
        }

        @Override
        public int hashCode() {
                return Objects.hash(x, y, z, name);
        }
}