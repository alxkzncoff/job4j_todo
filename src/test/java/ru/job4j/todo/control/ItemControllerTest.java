package ru.job4j.todo.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @Test
    public void whenAll() {
        Item task1 = Item.of("task 1", false);
        task1.setId(1);
        Item task2 = Item.of("task 2", true);
        task1.setId(2);
        Item task3 = Item.of("task 3", false);
        task1.setId(3);
        List<Item> items = Arrays.asList(task1, task2, task3);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        when(itemService.findAll()).thenReturn(items);
        ItemController itemController = new ItemController(itemService);
        String page = itemController.all(model);
        verify(model).addAttribute("items", items);
        assertThat(page, is("all"));
    }

    @Test
    public void whenDone() {
        Item task1 = Item.of("task 1", true);
        task1.setId(1);
        Item task2 = Item.of("task 2", true);
        task1.setId(2);
        List<Item> items = Arrays.asList(task1, task2);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        when(itemService.findAll()).thenReturn(items);
        ItemController itemController = new ItemController(itemService);
        String page = itemController.done(model);
        verify(model).addAttribute("items", items);
        assertThat(page, is("done"));
    }

    @Test
    public void whenUndone() {
        Item task1 = Item.of("task 1", false);
        task1.setId(1);
        Item task2 = Item.of("task 2", false);
        task1.setId(2);
        List<Item> items = Arrays.asList(task1, task2);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        when(itemService.findAll()).thenReturn(items);
        ItemController itemController = new ItemController(itemService);
        String page = itemController.undone(model);
        verify(model).addAttribute("items", items);
        assertThat(page, is("undone"));
    }

    @Test
    public void whenAddItem() {
        Item item = Item.of("task 1", false);
        item.setId(1);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        ItemController itemController = new ItemController(itemService);
        itemController.addItem(item);
        verify(itemService).add(item);
    }

    @Test
    public void whenDescription() {
        Item item = Item.of("task 1", true);
        item.setId(1);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        when(itemService.findById(1)).thenReturn(item);
        ItemController itemController = new ItemController(itemService);
        String page = itemController.description(model, 1);
        verify(model).addAttribute("item", item);
        assertThat(page, is("description"));
    }

    @Test
    public void whenMakeDone() {
        Item item = Item.of("task 1", true);
        item.setId(1);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        ItemController itemController = new ItemController(itemService);
        itemController.makeDone(model, item, 1);
        verify(itemService).update(1, item);
    }

    @Test
    public void whenEdit() {
        Item item = Item.of("task 1", true);
        item.setId(1);
        ItemService itemService = mock(ItemService.class);
        ItemController itemController = new ItemController(itemService);
        itemController.edit(item);
        verify(itemService).update(1, item);
    }

    @Test
    public void whenDelete() {
        Item item = Item.of("task 1", true);
        item.setId(1);
        ItemService itemService = mock(ItemService.class);
        ItemController itemController = new ItemController(itemService);
        itemController.delete(1);
        verify(itemService).delete(1);
    }
}