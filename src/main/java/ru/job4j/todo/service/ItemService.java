package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.ItemDBStore;

import java.sql.Timestamp;
import java.util.List;

@ThreadSafe
@Service
public class ItemService {

    private final ItemDBStore store;

    public ItemService(ItemDBStore store) {
        this.store = store;
    }

    /**
     * Метод добавляет задачу.
     * @param item Задача.
     * @return Добавленная задача.
     */
    public Item add(Item item) {
        return store.add(item);
    }

    /**
     * Метод обновляет задачу.
     * @param id Идентификационный номер задачи.
     * @param description Описание задачи.
     * @param created Дата создания задачи.
     * @param done Статус задачи.
     * @return Обновленная задача.
     */
    public Item update(int id, String description, Timestamp created, boolean done) {
        return store.update(id, description, created, done);
    }

    /**
     * Метод ищет задачу по id.
     * @param id Идентификационный номер задачи.
     * @return Найденная задачу или null.
     */
    public Item findById(int id) {
        return store.findById(id);
    }

    /**
     * Метод возвращает список всех задач.
     * @return Список задач.
     */
    public List findAll() {
        return store.findAll();
    }
}
