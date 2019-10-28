package Services;

import DAO.TestsDAO;
import DataSets.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class TestService {

    private final SessionFactory sessionFactory;
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    public TestService(){
        Configuration configuration = getPostgreConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    public Configuration getPostgreConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(AnswersDataSet.class)
                .addAnnotatedClass(GroupsDataSet.class)
                .addAnnotatedClass(MarksDataSet.class)
                .addAnnotatedClass(QuestionsDataSet.class)
                .addAnnotatedClass(StudentsDataSet.class)
                .addAnnotatedClass(TeachersDataSet.class)
                .addAnnotatedClass(TestsDataSet.class);
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL94Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");
        configuration.setProperty("hibernate.connection.username", "postgres");
        configuration.setProperty("hibernate.connection.password", "kainen");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", hibernate_hbm2ddl_auto);
        return configuration;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void addTest(String name, String creation_time, boolean test_type) throws Exception {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            TestsDAO dao = new TestsDAO(session);
            dao.insertTest(name, creation_time, test_type);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<TestsDataSet> getTestsByTeacher(int teacher_id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            TestsDAO dao = new TestsDAO(session);
            List<TestsDataSet> tests = dao.getTestByTeacher(teacher_id);
            session.close();
            return tests;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<TestsDataSet> getTestsByName (String name) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            TestsDAO dao = new TestsDAO(session);
            List<TestsDataSet> tests = dao.getTestsByName(name);
            session.close();
            return tests;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<TestsDataSet> getAllTests () throws Exception{
        try {
            Session session = sessionFactory.openSession();
            TestsDAO dao = new TestsDAO(session);
            List<TestsDataSet> tests = dao.getAllTests();
            session.close();
            return tests;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public TestsDataSet getTestById(int id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            TestsDAO dao = new TestsDAO(session);
            TestsDataSet test = dao.get(id);
            session.close();
            return test;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public void setAbout_Test(int test_id, String about_test) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            TestsDAO dao = new TestsDAO(session);
            dao.setAbout_Test(test_id, about_test);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public void setTests_Attempts(int test_id, int attempts) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            TestsDAO dao = new TestsDAO(session);
            dao.setTests_Attempts(test_id, attempts);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public void setSolution_Time(int test_id, int solution_time) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            TestsDAO dao = new TestsDAO(session);
            dao.setSolution_Time(test_id, solution_time);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public void removeTest(int test_id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            TestsDataSet test = getTestById(test_id);
            session.remove(test);
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }
}
