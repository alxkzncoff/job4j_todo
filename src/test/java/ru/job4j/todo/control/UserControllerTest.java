package ru.job4j.todo.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Test
    public void whenRegistration() {
        User user = User.of("user", "email", "password");
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        userController.registration(model, user);
        verify(userService).add(user);
    }

    @Test
    public void whenRegistrationSuccess() {
        User user = User.of("user", "email", "password");
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        UserController userController = new UserController(userService);
        String page = userController.registration(model, user);
        verify(userService).add(user);
        assertThat(page, is("redirect:/loginPage?regSuccess=true"));
    }

    @Test
    public void whenRegistrationFail() {
        User user = User.of("user", "email", "password");
        user.setId(1);
        Model model = mock(Model.class);
        UserService userService = mock(UserService.class);
        when(userService.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
        UserController userController = new UserController(userService);
        String page = userController.registration(model, user);
        verify(model).addAttribute("message", "Пользователь с такой почтой уже существует!");
        assertThat(page, is("redirect:/registration?fail=true"));
    }

    @Test
    public void whenLogin() {
        User user = User.of("user", "email", "password");
        UserService userService = mock(UserService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(userService.findByEmailAndPwd(user.getEmail(), user.getPassword())).thenReturn(Optional.of(user));
        when(req.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.login(user, req);
        verify(session).setAttribute("user", user);
        assertThat(page, is("redirect:/all"));
    }

    @Test
    public void whenLoginFail() {
        User user = User.of("user", "email", "password");
        UserService userService = mock(UserService.class);
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpSession session = mock(HttpSession.class);
        when(userService.findByEmailAndPwd(user.getEmail(), user.getPassword())).thenReturn(Optional.empty());
        when(req.getSession()).thenReturn(session);
        UserController userController = new UserController(userService);
        String page = userController.login(user, req);
        assertThat(page, is("redirect:/loginPage?fail=true"));
    }

}