package DAO;

import DataSets.AnswersDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.List;

public class AnswersDAO {

    private Session session;

    public AnswersDAO(Session session) {
        this.session = session;
    }

    public AnswersDataSet get(long id) throws HibernateException {
        return (AnswersDataSet) session.get(AnswersDataSet.class, id);
    }

    public List<AnswersDataSet> getAnswersByStudent(int test_id, int students_id) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<AnswersDataSet> cq = cb.createQuery(AnswersDataSet.class);
        Root<AnswersDataSet> root = cq.from(AnswersDataSet.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("test_id"), test_id);
        predicates[1] = cb.equal(root.get("students_id"), students_id);
        cq.select(root).where(predicates);
        List<AnswersDataSet> answers;
        Query<AnswersDataSet> query = session.createQuery(cq);
        try{
            answers = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Ответов этого студента в данном тесте не найдено");
            return null;
        }
        return answers;
    }

    public List<AnswersDataSet> getAnswersByQuestion(int test_id, int question_id) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<AnswersDataSet> cq = cb.createQuery(AnswersDataSet.class);
        Root<AnswersDataSet> root = cq.from(AnswersDataSet.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("test_id"), test_id);
        predicates[1] = cb.equal(root.get("question_id"), question_id);
        cq.select(root).where(predicates);
        List<AnswersDataSet> answers;
        Query<AnswersDataSet> query = session.createQuery(cq);
        try{
            answers = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Ответов для такого вопроса в данном тесте не найдено");
            return null;
        }
        return answers;
    }

    public void setTeacherComment(String comment, int answer_id) throws HibernateException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<AnswersDataSet> criteria = builder.createCriteriaUpdate(AnswersDataSet.class);
        Root<AnswersDataSet> root = criteria.from(AnswersDataSet.class);
        criteria.set(root.get("teacher_comment"), comment);
        criteria.where(builder.equal(root.get("id"), answer_id));
        session.createQuery(criteria).executeUpdate();
    }

    public void insertAnswer(String answer_text, boolean right) throws HibernateException {
        session.save(new AnswersDataSet(answer_text, right));
    }
}
