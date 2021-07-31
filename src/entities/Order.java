package entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order {
    private String id;
    private LocalDateTime dateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Order order = (Order) obj;
        return id == order.getId() &
                dateTime == order.getDateTime();
    }

    @Override
    public String toString() {
        return "entities.Order{" +
                "id='" + id + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }
}
