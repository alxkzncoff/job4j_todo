package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.junit.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.Category;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CategoryDBStoreTest {

    @Test
    public void whenFindById() {
        SessionFactory sf = new Main().sf();
        Category expected = Category.of("Category");
        CategoryDBStore store = new CategoryDBStore(sf);
        store.add(expected);
        assertThat(store.findById(1), is(expected));
    }

    @Test
    public void whenFindAll() {
        SessionFactory sf = new Main().sf();
        Category cat1 = Category.of("Category 1");
        Category cat2 = Category.of("Category 2");
        CategoryDBStore store = new CategoryDBStore(sf);
        store.add(cat1);
        store.add(cat2);
        assertThat(store.findAll().size(), is(2));
    }

}