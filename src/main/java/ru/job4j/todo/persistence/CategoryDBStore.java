package ru.job4j.todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;
import java.util.function.Function;

@ThreadSafe
@Repository
public class CategoryDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(ItemDBStore.class);
    private final SessionFactory sf;

    public CategoryDBStore(SessionFactory sf) {
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
     * Метод добавляет категорию в БД.
     * @param category задача.
     * @return Добавленная задача.
     */
    public Category add(Category category) {
        this.tx(
                session -> session.save(category)
        );
        return category;
    }

    /**
     * Метод ищет категорию по id БД.
     * @param id Идентификационный номер категории.
     * @return Найденная категория или null.
     */
    public Category findById(int id) {
        return this.tx(
                session -> (Category) session.createQuery("from Category c where c.id = :fId")
                        .setParameter("fId", id)
                        .uniqueResult()
        );
    }

    /**
     * Метод возвращает список всех категорий из БД.
     * @return Список категорий.
     */
    public List findAll() {
        return this.tx(
                session -> session.createQuery("from Category ").list()
        );
    }
}
