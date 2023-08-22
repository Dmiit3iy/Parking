package model;

import java.util.Objects;

public class Transport {
    private static int count = 0;
    private int countPlace;
    private int id;

    public Transport(int countPlace) {

        this.countPlace = countPlace;
        this.id=count;
        count++;
    }

    public Transport() {
        count++;
    }

    public int getId() {
        return id;
    }

    public int getCountPlace() {
        return countPlace;
    }

    public void setCountPlace(int countPlace) {
        this.countPlace = countPlace;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transport transport = (Transport) o;
        return countPlace == transport.countPlace;
    }

    @Override
    public int hashCode() {
        return Objects.hash(countPlace);
    }

    @Override
    public String toString() {
        return "Transport " +
                "countPlace=" + countPlace+";";
    }
}
