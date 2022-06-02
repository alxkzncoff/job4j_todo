package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.junit.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ItemDBStoreTest {

    @Test
    public void whenAdd() {
        SessionFactory sf = new Main().sf();
        User user = User.of("user", "user@mail.com", "password");
        user.setId(1);
        Category category = Category.of("category");
        UserDBStore userDBStore = new UserDBStore(sf);
        userDBStore.add(user);
        CategoryDBStore categoryDBStore = new CategoryDBStore(sf);
        categoryDBStore.add(category);
        ItemDBStore store = new ItemDBStore(sf);
        Item expected = Item.of("test", false, user);
        expected.addCategory(category);
        store.add(expected);
        List result = store.findAll();
        assertThat((Item) result.get(0), is(expected));
    }

    @Test
    public void whenUpdate() {
        SessionFactory sf = new Main().sf();
        User user = User.of("user", "user@mail.com", "password");
        user.setId(1);
        UserDBStore userDBStore = new UserDBStore(sf);
        userDBStore.add(user);
        CategoryDBStore categoryDBStore = new CategoryDBStore(sf);
        Category category = Category.of("category");
        categoryDBStore.add(category);
        ItemDBStore store = new ItemDBStore(sf);
        Item expected = Item.of("test1", false, user);
        expected.addCategory(category);
        store.add(expected);
        expected.setDescription("test2");
        expected.setDone(true);
        store.update(1, expected);
        Item result = store.findById(1);
        assertEquals(expected, result);
    }

    @Test
    public void whenUpdateDone() {
        SessionFactory sf = new Main().sf();
        User user = User.of("user", "user@mail.com", "password");
        user.setId(1);
        UserDBStore userDBStore = new UserDBStore(sf);
        userDBStore.add(user);
        CategoryDBStore categoryDBStore = new CategoryDBStore(sf);
        Category category = Category.of("category");
        categoryDBStore.add(category);
        ItemDBStore store = new ItemDBStore(sf);
        Item item = Item.of("test", false, user);
        item.setId(1);
        item.addCategory(category);
        store.add(item);
        store.updateDone(1);
        assertTrue(store.findById(1).isDone());
    }

    @Test
    public void whenFindById() {
        SessionFactory sf = new Main().sf();
        User user = User.of("user", "user@mail.com", "password");
        user.setId(1);
        UserDBStore userDBStore = new UserDBStore(sf);
        userDBStore.add(user);
        CategoryDBStore categoryDBStore = new CategoryDBStore(sf);
        Category category = Category.of("category");
        categoryDBStore.add(category);
        ItemDBStore store = new ItemDBStore(sf);
        Item expected = Item.of("test", false, user);
        expected.addCategory(category);
        store.add(expected);
        Item result = store.findById(1);
        assertThat(result, is(expected));
    }

    @Test
    public void whenFindAll() {
        SessionFactory sf = new Main().sf();
        User user = User.of("user", "user@mail.com", "password");
        user.setId(1);
        UserDBStore userDBStore = new UserDBStore(sf);
        userDBStore.add(user);
        CategoryDBStore categoryDBStore = new CategoryDBStore(sf);
        Category cat1 = Category.of("category 1");
        Category cat2 = Category.of("category 2");
        categoryDBStore.add(cat1);
        categoryDBStore.add(cat2);
        ItemDBStore store = new ItemDBStore(sf);
        Item item1 = Item.of("test1", false, user);
        item1.addCategory(cat1);
        Item item2 = Item.of("test2", true, user);
        item2.addCategory(cat2);
        store.add(item1);
        store.add(item2);
        List result = store.findAll();
        assertThat(result.size(), is(2));
    }

    @Test
    public void whenDelete() {
        SessionFactory sf = new Main().sf();
        User user = User.of("user", "user@mail.com", "password");
        user.setId(1);
        UserDBStore userDBStore = new UserDBStore(sf);
        userDBStore.add(user);
        CategoryDBStore categoryDBStore = new CategoryDBStore(sf);
        Category category = Category.of("category");
        categoryDBStore.add(category);
        ItemDBStore store = new ItemDBStore(sf);
        Item item = Item.of("test", true, user);
        item.addCategory(category);
        store.add(item);
        store.delete(1);
        assertThat(store.findAll().size(), is(0));
    }
}