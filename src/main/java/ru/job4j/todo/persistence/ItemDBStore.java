package ru.job4j.todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.model.Item;

import java.util.List;
import java.util.function.Function;

@ThreadSafe
@Repository
public class ItemDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(ItemDBStore.class);
    private final SessionFactory sf;

    public ItemDBStore(SessionFactory sf) {
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
     * Метод добавляет задачу в БД.
     * @param item задача.
     * @return Добавленная задача.
     */
    public Item add(Item item) {
        this.tx(
                session -> session.save(item)
        );
        return item;
    }

    /**
     * Метод обновляет задачу в БД.
     * @param id Идентификационный номер задачи.
     * @param item Описание задачи.
     * @return Обновленная задача.
     */
    public Item update(int id, Item item) {
        this.tx(
                session -> session.createQuery("update Item i set i.description = :newDesc, "
                                + "i.created = :newCreated, i.done = :newDone where i.id = :fId")
                        .setParameter("newDesc", item.getDescription())
                        .setParameter("newCreated", item.getCreated())
                        .setParameter("newDone", item.isDone())
                        .setParameter("fId", id)
                        .executeUpdate()
        );
        return item;
    }

    /**
     * Метод меняет статус задачи на "Выполнено" в БД.
     * @param id Идентификационный номер задачи.
     */
    public void updateDone(int id) {
        this.tx(
                session -> session.createQuery("update Item i set i.done = :newDone"
                                + " where i.id = :fId")
                        .setParameter("newDone", true)
                        .setParameter("fId", id)
                        .executeUpdate()
        );
    }

    /**
     * Метод ищет задачу по id БД.
     * @param id Идентификационный номер задачи.
     * @return Найденная задачу или null.
     */
    public Item findById(int id) {
        return this.tx(
                session -> (Item) session.createQuery("select distinct i "
                                + "from Item i join fetch i.categories "
                                + "where i.id = :fId")
                        .setParameter("fId", id)
                        .uniqueResult()
        );
    }

    /**
     * Метод возвращает список всех задач из БД.
     * @return Список задач.
     */
    public List findAll() {
        return this.tx(
                session -> session.createQuery("select distinct i from Item i join fetch i.categories")
                        .list()
        );
    }

    /**
     * Метод удаляет задачу из БД.
     * @param id Идентификационный номер задачи.
     */
    public void delete(int id) {
        this.tx(
                session -> session.createQuery("delete from Item where id = :fId")
                        .setParameter("fId", id)
                        .executeUpdate()
        );
    }
}
