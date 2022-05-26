package ru.job4j.todo.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/all")
    public String all(Model model, HttpSession session) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("items", itemService.findAll());
        return "all";
    }

    @GetMapping("/done")
    public String done(Model model, HttpSession session) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("items", itemService.findAll());
        return "done";
    }

    @GetMapping("/undone")
    public String undone(Model model, HttpSession session) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("items", itemService.findAll());
        return "undone";
    }

    @GetMapping("/addItem")
    public String addItemForm(Model model, HttpSession session) {
        model.addAttribute("user", currentUser(session));
        return "addItem";
    }

    @PostMapping("/addItem")
    public String addItem(@ModelAttribute Item item) {
        itemService.add(item);
        return "redirect:/all";
    }

    @GetMapping("/description/{itemId}")
    public String description(Model model, @PathVariable("itemId") int id, HttpSession session) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("item", itemService.findById(id));
        return "description";
    }

    @PostMapping("/makeDone/{itemId}")
    public String makeDone(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        itemService.updateDone(id);
        return String.format("redirect:/description/%d", id);
    }

    @GetMapping("/edit/{itemId}")
    public String editForm(Model model, @PathVariable("itemId") int id, HttpSession session) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("item", itemService.findById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Item item) {
        itemService.update(item.getId(), item);
        return String.format("redirect:/description/%d", item.getId());
    }

    @PostMapping("/delete/{itemId}")
    public String delete(@PathVariable("itemId") int id) {
        itemService.delete(id);
        return "redirect:/all";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }

    private User currentUser(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        return user;
    }
}
