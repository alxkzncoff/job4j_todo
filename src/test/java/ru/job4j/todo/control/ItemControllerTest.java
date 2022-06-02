package ru.job4j.todo.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class ItemControllerTest {

    @Test
    public void whenAll() {
        Item task1 = Item.of("task 1", false, new User());
        task1.setId(1);
        Item task2 = Item.of("task 2", true, new User());
        task1.setId(2);
        Item task3 = Item.of("task 3", false, new User());
        task1.setId(3);
        List<Item> items = Arrays.asList(task1, task2, task3);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        CategoryService categoryService = mock(CategoryService.class);
        HttpSession session = mock(HttpSession.class);
        when(itemService.findAll()).thenReturn(items);
        ItemController itemController = new ItemController(itemService, categoryService);
        String page = itemController.all(model, session);
        verify(model).addAttribute("items", items);
        assertThat(page, is("all"));
    }

    @Test
    public void whenDone() {
        Item task1 = Item.of("task 1", true, new User());
        task1.setId(1);
        Item task2 = Item.of("task 2", true, new User());
        task1.setId(2);
        List<Item> items = Arrays.asList(task1, task2);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        CategoryService categoryService = mock(CategoryService.class);
        HttpSession session = mock(HttpSession.class);
        when(itemService.findAll()).thenReturn(items);
        ItemController itemController = new ItemController(itemService, categoryService);
        String page = itemController.done(model, session);
        verify(model).addAttribute("items", items);
        assertThat(page, is("done"));
    }

    @Test
    public void whenUndone() {
        Item task1 = Item.of("task 1", false, new User());
        task1.setId(1);
        Item task2 = Item.of("task 2", false, new User());
        task1.setId(2);
        List<Item> items = Arrays.asList(task1, task2);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        CategoryService categoryService = mock(CategoryService.class);
        HttpSession session = mock(HttpSession.class);
        when(itemService.findAll()).thenReturn(items);
        ItemController itemController = new ItemController(itemService, categoryService);
        String page = itemController.undone(model, session);
        verify(model).addAttribute("items", items);
        assertThat(page, is("undone"));
    }

    @Test
    public void whenAddItem() {
        Item item = Item.of("task 1", false, new User());
        item.setId(1);
        ItemService itemService = mock(ItemService.class);
        CategoryService categoryService = mock(CategoryService.class);
        ItemController itemController = new ItemController(itemService, categoryService);
        itemController.addItem(item, new ArrayList<>());
        verify(itemService).add(item);
    }

    @Test
    public void whenDescription() {
        Item item = Item.of("task 1", true, new User());
        item.setId(1);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        CategoryService categoryService = mock(CategoryService.class);
        HttpSession session = mock(HttpSession.class);
        when(itemService.findById(1)).thenReturn(item);
        ItemController itemController = new ItemController(itemService, categoryService);
        String page = itemController.description(model, 1, session);
        verify(model).addAttribute("item", item);
        assertThat(page, is("description"));
    }

    @Test
    public void whenMakeDone() {
        Item item = Item.of("task 1", true, new User());
        item.setId(1);
        Model model = mock(Model.class);
        ItemService itemService = mock(ItemService.class);
        CategoryService categoryService = mock(CategoryService.class);
        ItemController itemController = new ItemController(itemService, categoryService);
        itemController.makeDone(model, 1);
        verify(itemService).updateDone(1);
    }

    @Test
    public void whenEdit() {
        Item item = Item.of("task 1", true, new User());
        item.setId(1);
        ItemService itemService = mock(ItemService.class);
        CategoryService categoryService = mock(CategoryService.class);
        ItemController itemController = new ItemController(itemService, categoryService);
        itemController.edit(item);
        verify(itemService).update(1, item);
    }

    @Test
    public void whenDelete() {
        Item item = Item.of("task 1", true, new User());
        item.setId(1);
        ItemService itemService = mock(ItemService.class);
        CategoryService categoryService = mock(CategoryService.class);
        ItemController itemController = new ItemController(itemService, categoryService);
        itemController.delete(1);
        verify(itemService).delete(1);
    }
}