package ru.s1riys.lab3.repositories;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.s1riys.lab3.models.DotModel;
import ru.s1riys.lab3.utils.HibernateSessionFactoryUtil;

@Default
public class DotRepositoryImpl implements IDotRepository, Serializable {
    @Override
    public void save(DotModel result) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(result);
        tx.commit();
        session.close();
    }

    @Override
    public List<DotModel> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<DotModel> cq = cb.createQuery(DotModel.class);
            Root<DotModel> rootEntry = cq.from(DotModel.class);
            CriteriaQuery<DotModel> all = cq.select(rootEntry);

            all = all.orderBy(cb.desc(rootEntry.get("createdAt")));

            TypedQuery<DotModel> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        }
    }
}
