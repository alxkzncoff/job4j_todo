package ru.job4j.todo.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.todo.service.ItemService;

@ThreadSafe
@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "index";
    }
}
