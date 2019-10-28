package Services;

import DAO.StudentsDAO;
import DataSets.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class StudentService implements UserService_Interface{

    private final SessionFactory sessionFactory;
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    public StudentService() {
        Configuration configuration = getPostgreConfiguration();
        sessionFactory = createSessionFactory(configuration);
    }

    public Configuration getPostgreConfiguration() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(AnswersDataSet.class);
        configuration.addAnnotatedClass(GroupsDataSet.class);
        configuration.addAnnotatedClass(MarksDataSet.class);
        configuration.addAnnotatedClass(QuestionsDataSet.class);
        configuration.addAnnotatedClass(StudentsDataSet.class);
        configuration.addAnnotatedClass(TeachersDataSet.class);
        configuration.addAnnotatedClass(TestsDataSet.class);
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

    public void addUser(String name, String surname, String email, String password, String regDate) throws Exception {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            StudentsDAO dao = new StudentsDAO(session);
            dao.insertStudent(name, surname, email, password, regDate);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public StudentsDataSet getCurUserById(int id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            StudentsDAO dao = new StudentsDAO(session);
            StudentsDataSet student = dao.get(id);
            session.close();
            return student;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public StudentsDataSet getCurUserByLogin(String login) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            StudentsDAO dao = new StudentsDAO(session);
            StudentsDataSet student = dao.getStudentByLogin(login);
            session.close();
            return student;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<StudentsDataSet> getCurUserBySurname(String surname) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            StudentsDAO dao = new StudentsDAO(session);
            List<StudentsDataSet> students = dao.getStudentsBySurname(surname);
            session.close();
            return students;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<StudentsDataSet> getCurUserByOrganization(String surname) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            StudentsDAO dao = new StudentsDAO(session);
            List<StudentsDataSet> students = dao.getStudentsByOrganization(surname);
            session.close();
            return students;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<StudentsDataSet> getCurUserByFIO(String name, String surname, String patronymic) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            StudentsDAO dao = new StudentsDAO(session);
            List<StudentsDataSet> student = dao.getStudentsByFIO(name, surname, patronymic);
            session.close();
            return student;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<StudentsDataSet> getAllUsers () throws Exception{
        try {
            Session session = sessionFactory.openSession();
            StudentsDAO dao = new StudentsDAO(session);
            List<StudentsDataSet> students = dao.getAllStudents();
            session.close();
            return students;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public void setUser_Organization(int student_id, String organization) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            StudentsDAO dao = new StudentsDAO(session);
            dao.setStudent_Organization(student_id, organization);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public void setUser_Patronymic(int student_id, String patronymic) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            StudentsDAO dao = new StudentsDAO(session);
            dao.setStudent_Patronymic(student_id, patronymic);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public void removeUser(int student_id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            StudentsDataSet test = getCurUserById(student_id);
            session.remove(test);
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }
}
