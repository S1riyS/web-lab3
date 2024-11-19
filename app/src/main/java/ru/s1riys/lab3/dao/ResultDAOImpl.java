package ru.s1riys.lab3.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import jakarta.enterprise.inject.Default;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import ru.s1riys.lab3.models.ResultModel;
import ru.s1riys.lab3.utils.HibernateSessionFactoryUtil;

@Default
public class ResultDAOImpl implements IResultDAO, Serializable {
    @Override
    public void save(ResultModel result) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.persist(result);
        tx.commit();
        session.close();
    }

    @Override
    public List<ResultModel> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ResultModel> cq = cb.createQuery(ResultModel.class);
            Root<ResultModel> rootEntry = cq.from(ResultModel.class);
            CriteriaQuery<ResultModel> all = cq.select(rootEntry);

            TypedQuery<ResultModel> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        }
    }
}
