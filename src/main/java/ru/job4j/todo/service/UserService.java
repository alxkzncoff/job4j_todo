package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistence.UserDBStore;

import java.util.Optional;

@ThreadSafe
@Service
public class UserService {

    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    /**
     * Метод добавляет пользователя.
     * @param user Пользователь.
     */
    public void add(User user) {
        store.add(user);
    }

    /**
     * Метод возвращает пользователя по id, если такой есть.
     * @param id Идентификационный номер пользователя.
     * @return Найденный пользователь.
     */
    public Optional<User> findById(int id) {
        return store.findById(id);
    }

    /**
     * Метод возвращает пользователя по почте.
     * @param email Почта пользователя.
     * @return Найденный пользователь или Optional.empty().
     */
    public Optional<User> findByEmail(String email) {
        return store.findByEmail(email);
    }

    /**
     * Метод возвращает пользователя по почте и паролю.
     * @param email Почта пользователя.
     * @param password Пароль пользователя.
     * @return Найденный пользователь или Optional.empty().
     */
    public Optional<User> findByEmailAndPwd(String email, String password) {
        return store.findByMailAndPwd(email, password);
    }
}
