package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.persistence.CategoryDBStore;

import java.util.List;

@ThreadSafe
@Service
public class CategoryService {
    private final CategoryDBStore store;

    public CategoryService(CategoryDBStore store) {
        this.store = store;
    }

    /**
     * Метод добавляет категорию.
     * @param category категория.
     * @return Добавленная категория.
     */
    public Category add(Category category) {
        return store.add(category);
    }

    /**
     * Метод ищет категорию по id.
     * @param id Идентификационный номер категории.
     * @return Найденная категория или null.
     */
    public Category findById(int id) {
        return store.findById(id);
    }

    /**
     * Метод возвращает список всех категорий.
     * @return Список категорий.
     */
    public List findAll() {
        return store.findAll();
    }
}
