package ru.job4j.todo.persistence;

import net.jcip.annotations.ThreadSafe;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.todo.model.Item;

import java.util.List;

@ThreadSafe
@Repository
public class ItemDBStore {
    private static final Logger LOG = LoggerFactory.getLogger(ItemDBStore.class);
    private final SessionFactory sf;

    public ItemDBStore(SessionFactory sf) {
        this.sf = sf;
    }

    /**
     * Метод добавляет задачу в БД.
     * @param item задача.
     * @return Добавленная задача.
     */
    public Item add(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
        return item;
    }

    /**
     * Метод обновляет задачу в БД.
     * @param id Идентификационный номер задачи.
     * @param item Описание задачи.
     * @return Обновленная задача.
     */
    public Item update(int id, Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("update Item i set i.description = :newDesc, "
                        + "i.created = :newCreated, i.done = :newDone where i.id = :fId")
                .setParameter("newDesc", item.getDescription())
                .setParameter("newCreated", item.getCreated())
                .setParameter("newDone", item.isDone())
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return item;
    }

    /**
     * Метод ищет задачу по id БД.
     * @param id Идентификационный номер задачи.
     * @return Найденная задачу или null.
     */
    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = (Item) session.createQuery("from Item i where i.id = :fId")
                .setParameter("fId", id).uniqueResult();
        session.getTransaction();
        session.close();
        return result;
    }

    /**
     * Метод возвращает список всех задач из БД.
     * @return Список задач.
     */
    public List findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        List result = session.createQuery("from Item").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Метод удаляет задачу из БД.
     * @param id Идентификационный номер задачи.
     */
    public void delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Item where id = :fId")
                .setParameter("fId", id)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
