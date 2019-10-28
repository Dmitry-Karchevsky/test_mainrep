package Services;

import DAO.GroupsDAO;
import DataSets.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class GroupService {

    private final SessionFactory sessionFactory;
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "update";

    public GroupService(){
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

    public void addGroup(String name) throws Exception {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            GroupsDAO dao = new GroupsDAO(session);
            dao.insertGroup(name);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public GroupsDataSet getGroupById(long id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            GroupsDAO dao = new GroupsDAO(session);
            GroupsDataSet group = dao.get(id);
            session.close();
            return group;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<GroupsDataSet> getGroupsByName(String name) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            GroupsDAO dao = new GroupsDAO(session);
            List<GroupsDataSet> groups = dao.getGroupsByName(name);
            session.close();
            return groups;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }

    public List<GroupsDataSet> getGroupsByTeacher(long teacher_id) throws Exception{
        try {
            Session session = sessionFactory.openSession();
            GroupsDAO dao = new GroupsDAO(session);
            List<GroupsDataSet> groups = dao.getGroupsByTeacher(teacher_id);
            session.close();
            return groups;
        } catch (HibernateException e) {
            throw new Exception(e);
        }
    }
}
