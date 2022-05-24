package ru.job4j.todo.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс описывающий задачу.
 * @author Aleksandr Kuznetsov.
 * @version 1.0
 */
@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    public Timestamp created = Timestamp.valueOf(LocalDateTime.now());
    private boolean done = false;

    public Item() {
    }

    public static Item of(String description, boolean done) {
        Item item = new Item();
        item.description = description;
        item.done = done;
        return item;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public String toString() {
        return "Item: "
                + "id=" + id
                + ", description='" + description + '\''
                + ", created=" + created
                + ", done=" + done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return id == item.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
