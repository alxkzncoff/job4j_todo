package ru.job4j.todo.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationForm(Model model, HttpSession session,
                                   @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail);
        model.addAttribute("user", currentUser(session));
        return "/registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = userService.findByEmail(user.getEmail());
        if (regUser.isPresent()) {
            model.addAttribute("message", "Пользователь с такой почтой уже существует!");
            return "redirect:/registration?fail=true";
        }
        userService.add(user);
        return "redirect:/loginPage?regSuccess=true";
    }

    @GetMapping("/loginPage")
    public String loginPage(Model model,
                            @RequestParam(name = "fail", required = false) Boolean fail,
                            @RequestParam(name = "regSuccess", required = false) Boolean regSuccess) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("regSuccess", regSuccess != null);
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = userService.findByEmailAndPwd(
                user.getEmail(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/all";
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
