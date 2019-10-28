package DAO;

import DataSets.GroupsDataSet;
import DataSets.TestsDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

public class TestsDAO {

    private Session session;

    public TestsDAO(Session session) {
        this.session = session;
    }

    public TestsDataSet get(long id) throws HibernateException {
        return (TestsDataSet) session.get(TestsDataSet.class, id);
    }

    public List<TestsDataSet> getTestByTeacher(int teacher_id) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TestsDataSet> cq = cb.createQuery(TestsDataSet.class);
        Root<TestsDataSet> root = cq.from(TestsDataSet.class);
        cq.select(root);
        cq.where(cb.equal(root.get("teacher_id"), teacher_id));
        List<TestsDataSet> tests;
        Query<TestsDataSet> query = session.createQuery(cq);
        try{
            tests = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Тестов от этого учителя не найдено");
            return null;
        }
        return tests;
    }

    public List<TestsDataSet> getTestsByName(String name) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<TestsDataSet> cq = cb.createQuery(TestsDataSet.class);
        Root<TestsDataSet> root = cq.from(TestsDataSet.class);
        cq.select(root);
        cq.where(cb.equal(root.get("name"), name));
        List<TestsDataSet> tests;
        Query<TestsDataSet> query = session.createQuery(cq);
        try{
            tests = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Тестов с таким названием не найдено");
            return null;
        }
        return tests;
    }

    public void setAbout_Test(int test_id, String about_test) throws HibernateException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<TestsDataSet> criteria = builder.createCriteriaUpdate(TestsDataSet.class);
        Root<TestsDataSet> root = criteria.from(TestsDataSet.class);
        criteria.where(builder.equal(root.get("id"), test_id));
        criteria.set(root.get("about_test"), about_test);
        session.createQuery(criteria).executeUpdate();
    }

    public void setTests_Attempts(int test_id, int attempts) throws HibernateException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<TestsDataSet> criteria = builder.createCriteriaUpdate(TestsDataSet.class);
        Root<TestsDataSet> root = criteria.from(TestsDataSet.class);
        criteria.where(builder.equal(root.get("id"), test_id));
        criteria.set(root.get("attempts"), attempts);
        session.createQuery(criteria).executeUpdate();
    }

    public void setSolution_Time(int test_id, int solution_time) throws HibernateException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<TestsDataSet> criteria = builder.createCriteriaUpdate(TestsDataSet.class);
        Root<TestsDataSet> root = criteria.from(TestsDataSet.class);
        criteria.where(builder.equal(root.get("id"), test_id));
        criteria.set(root.get("solution_time"), solution_time);
        session.createQuery(criteria).executeUpdate();
    }


    public void insertTest(String name, String creation_time, boolean test_type) throws HibernateException {
        session.save(new TestsDataSet(name, creation_time, test_type));
    }
}
