package DAO;


import DataSets.MarksDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public class MarksDAO {

    private Session session;

    public MarksDAO(Session session) {
        this.session = session;
    }

    public MarksDataSet get(long id) throws HibernateException {
        return (MarksDataSet) session.get(MarksDataSet.class, id);
    }

    public List<MarksDataSet> getMarksByTest(int test_id) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MarksDataSet> cq = cb.createQuery(MarksDataSet.class);
        Root<MarksDataSet> root = cq.from(MarksDataSet.class);
        cq.select(root);
        cq.where(cb.equal(root.get("test_id"), test_id));
        List<MarksDataSet> marks;
        Query<MarksDataSet> query = session.createQuery(cq);
        try{
            marks = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Оценок в тесте не найдено");
            return null;
        }
        return marks;
    }

    public MarksDataSet getMarksByTestAndStudent(int test_id, int student_id) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<MarksDataSet> cq = cb.createQuery(MarksDataSet.class);
        Root<MarksDataSet> root = cq.from(MarksDataSet.class);
        Predicate[] predicates = new Predicate[2];
        predicates[0] = cb.equal(root.get("test_id"), test_id);
        predicates[1] = cb.equal(root.get("student_id"), student_id);
        cq.select(root).where(predicates);
        MarksDataSet mark;
        Query<MarksDataSet> query = session.createQuery(cq);
        try{
            mark = query.getSingleResult();
        }catch (NoResultException ex){
            System.out.println("Оценки у ученика по этому тесту не найдено");
            return null;
        }
        return mark;
    }

    public void insertMark(String mark) throws HibernateException {
        session.save(new MarksDataSet(mark));
    }
}
