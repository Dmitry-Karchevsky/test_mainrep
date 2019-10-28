package DAO;

import DataSets.QuestionsDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class QuestionsDAO {

    private Session session;

    public QuestionsDAO(Session session) {
        this.session = session;
    }

    public QuestionsDataSet get(long id) throws HibernateException {
        return (QuestionsDataSet) session.get(QuestionsDataSet.class, id);
    }

    public List<QuestionsDataSet> getQuestionByTest(int test_id) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<QuestionsDataSet> cq = cb.createQuery(QuestionsDataSet.class);
        Root<QuestionsDataSet> root = cq.from(QuestionsDataSet.class);
        cq.select(root);
        cq.where(cb.equal(root.get("test_id"), test_id));
        List<QuestionsDataSet> questions;
        Query<QuestionsDataSet> query = session.createQuery(cq);
        try{
            questions = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Вопросов в тесте не найдено");
            return null;
        }
        return questions;
    }

    public List<QuestionsDataSet> getQuestionByTestAndType(int test_id, String type) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<QuestionsDataSet> cq = cb.createQuery(QuestionsDataSet.class);
        Root<QuestionsDataSet> root = cq.from(QuestionsDataSet.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("test_id"), test_id);
        predicates[0] = cb.equal(root.get("type"), type);
        cq.select(root).where(predicates);
        List<QuestionsDataSet> questions;
        Query<QuestionsDataSet> query = session.createQuery(cq);
        try{
            questions = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Вопросов такого типа в тесте не найдено");
            return null;
        }
        return questions;
    }

    public void insertQuestion(String question, String type) throws HibernateException {
        session.save(new QuestionsDataSet(question, type));
    }
}
