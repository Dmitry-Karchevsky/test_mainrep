package Services;

import DAO.AnswersDAO;
import DataSets.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class AnswerService {

    private final SessionFactory sessionFactory;
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    public AnswerService(){
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

    public void addAnswer(String answer_text, boolean right) throws Exception {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            AnswersDAO dao = new AnswersDAO(session);
            dao.insertAnswer(answer_text, right);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<AnswersDataSet> getAnswersByQuestion(int test_id, int question_id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            AnswersDAO dao = new AnswersDAO(session);
            List<AnswersDataSet> answer = dao.getAnswersByQuestion(test_id, question_id);
            session.close();
            return answer;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<AnswersDataSet> getAnswersOfStudent(int test_id, int student_id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            AnswersDAO dao = new AnswersDAO(session);
            List<AnswersDataSet> answer = dao.getAnswersByStudent(test_id, student_id);
            session.close();
            return answer;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public AnswersDataSet getAnswerById(int id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            AnswersDAO dao = new AnswersDAO(session);
            AnswersDataSet answer = dao.get(id);
            session.close();
            return answer;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public void setCommentOfTeacher(String comment, int answer_id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            AnswersDAO dao = new AnswersDAO(session);
            dao.setTeacherComment(comment, answer_id);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }
}
