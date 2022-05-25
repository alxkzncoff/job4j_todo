package ru.job4j.todo.persistence;

import org.junit.Test;
import ru.job4j.todo.Main;
import ru.job4j.todo.model.Item;

import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ItemDBStoreTest {

    @Test
    public void whenAdd() {
        ItemDBStore store = new ItemDBStore(new Main().sf());
        Item expected = Item.of("test", false);
        store.add(expected);
        List result = store.findAll();
        assertThat((Item) result.get(0), is(expected));
    }

    @Test
    public void whenUpdate() {
        ItemDBStore store = new ItemDBStore(new Main().sf());
        Item item = Item.of("test1", false);
        Item updatedItem = Item.of("test2", true);
        updatedItem.setId(1);
        store.add(item);
        store.update(1, updatedItem);
        Item result = store.findById(1);
        assertEquals(updatedItem, result);
    }

    @Test
    public void whenUpdateDone() {
        ItemDBStore store = new ItemDBStore(new Main().sf());
        Item item = Item.of("test", false);
        item.setId(1);
        store.add(item);
        store.updateDone(1);
        assertTrue(store.findById(1).isDone());
    }

    @Test
    public void whenFindById() {
        ItemDBStore store = new ItemDBStore(new Main().sf());
        Item expected = Item.of("test", false);
        store.add(expected);
        Item result = store.findById(1);
        assertThat(result, is(expected));
    }

    @Test
    public void whenFindAll() {
        ItemDBStore store = new ItemDBStore(new Main().sf());
        Item item1 = Item.of("test1", false);
        Item item2 = Item.of("test2", true);
        store.add(item1);
        store.add(item2);
        List result = store.findAll();
        assertThat(result.size(), is(2));
    }

    @Test
    public void whenDelete() {
        ItemDBStore store = new ItemDBStore(new Main().sf());
        Item item = Item.of("test", true);
        store.add(item);
        store.delete(1);
        assertThat(store.findAll().size(), is(0));
    }
}