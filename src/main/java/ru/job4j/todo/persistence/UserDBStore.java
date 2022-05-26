package ru.job4j.todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;
import java.util.function.Function;

@ThreadSafe
@Repository
public class UserDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(ItemDBStore.class);
    private final SessionFactory sf;

    public UserDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            LOG.error("HQL Exception", e);
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Метод добавляет пользователя в БД.
     * @param user Пользователь.
     */
    public void add(User user) {
        this.tx(
                session -> session.save(user)
        );
    }

    /**
     * Метод возвращает пользователя по id, если он есть в БД.
     * @param id Идентификационный номер пользователя.
     * @return Найденный пользователь.
     */
    public Optional<User> findById(int id) {
        return Optional.ofNullable((User) this.tx(
                session -> session.createQuery("from User where id = :fId")
                        .setParameter("fId", id)
                        .uniqueResult()
        ));
    }

    /**
     * Метод возвращает пользователя из БД по почте.
     * @param email Почта пользователя.
     * @return Найденный пользователь или Optional.empty().
     */
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable((User) this.tx(
                session -> session.createQuery("from User where email = :fEmail")
                        .setParameter("fEmail", email)
                        .uniqueResult()
        ));
    }

    /**
     * Метод возвращает пользователя из БД по почте и паролю.
     * @param email Почта пользователя.
     * @param password Пароль пользователя.
     * @return Найденный пользователь или Optional.empty().
     */
    public Optional<User> findByMailAndPwd(String email, String password) {
        return Optional.ofNullable((User) this.tx(
                session -> session.createQuery("from User where email = :fEmail and password = :fPassword")
                        .setParameter("fEmail", email)
                        .setParameter("fPassword", password)
                        .uniqueResult()
        ));
    }
}
