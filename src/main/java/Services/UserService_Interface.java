package Services;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public interface UserService_Interface {

    Configuration getPostgreConfiguration();

    static SessionFactory createSessionFactory(Configuration configuration){
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    void addUser(String name, String surname, String email, String password, String regDate) throws Exception;

    <T> T getCurUserByLogin(String login) throws Exception;

    <T> T getCurUserById(int id) throws Exception;

    <T> List<T> getCurUserBySurname(String surname) throws Exception;

    <T> List<T> getCurUserByOrganization(String organization) throws Exception;

    <T> List<T> getCurUserByFIO(String name, String surname, String patronymic) throws Exception;

    void setStudent_Organization(int student_id, String organization) throws Exception;

    void setStudent_Patronymic(int student_id, String patronymic) throws Exception;
}
