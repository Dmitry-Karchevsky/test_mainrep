package DAO;

import DataSets.StudentsDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.*;
import java.util.List;


public class StudentsDAO {

    private Session session;

    public StudentsDAO(Session session) {
        this.session = session;
    }

    public StudentsDataSet get(long id) throws HibernateException {
        return (StudentsDataSet) session.get(StudentsDataSet.class, id);
    }

    public StudentsDataSet getStudentByLogin(String login) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<StudentsDataSet> cq = cb.createQuery(StudentsDataSet.class);
        Root<StudentsDataSet> root = cq.from(StudentsDataSet.class);
        cq.select(root).where(cb.equal(root.get("email"), login));
        StudentsDataSet student;
        Query<StudentsDataSet> query = session.createQuery(cq);
        try{
            student = (StudentsDataSet) query.getSingleResult();
        }catch (NoResultException ex){
            System.out.println("Студента с таким логином не найдено");
            return null;
        }
        return student;
    }

    public List<StudentsDataSet> getStudentsBySurname (String surname) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<StudentsDataSet> cq = cb.createQuery(StudentsDataSet.class);
        Root<StudentsDataSet> root = cq.from(StudentsDataSet.class);
        cq.select(root);
        cq.where(cb.equal(root.get("surname"), surname));
        List<StudentsDataSet> students;
        Query<StudentsDataSet> query = session.createQuery(cq);
        try{
            students = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Студентов с такой фамилией не найдено");
            return null;
        }
        return students;
    }

    public List<StudentsDataSet> getStudentsByOrganization (String organization) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<StudentsDataSet> cq = cb.createQuery(StudentsDataSet.class);
        Root<StudentsDataSet> root = cq.from(StudentsDataSet.class);
        cq.select(root);
        cq.where(cb.equal(root.get("organization"), organization));
        List<StudentsDataSet> students;
        Query<StudentsDataSet> query = session.createQuery(cq);
        try{
            students = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Студентов с такой организации не найдено");
            return null;
        }
        return students;
    }

    public List<StudentsDataSet> getStudentsByFIO(String name, String surname, String patronymic) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<StudentsDataSet> cq = cb.createQuery(StudentsDataSet.class);
        Root<StudentsDataSet> root = cq.from(StudentsDataSet.class);
        Predicate[] predicates = new Predicate[3];
        predicates[0] = cb.equal(root.get("name"), name);
        predicates[1] = cb.equal(root.get("surname"), surname);
        predicates[2] = cb.equal(root.get("patronymic"), patronymic);
        cq.select(root).where(predicates);
        List<StudentsDataSet> students;
        Query<StudentsDataSet> query = session.createQuery(cq);
        try{
            students = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Студентов с таким ФИО не найдено");
            return null;
        }
        return students;
    }

    public void setStudent_Organization(int student_id, String organization) throws HibernateException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<StudentsDataSet> criteria = builder.createCriteriaUpdate(StudentsDataSet.class);
        Root<StudentsDataSet> root = criteria.from(StudentsDataSet.class);
        criteria.where(builder.equal(root.get("id"), student_id));
        criteria.set(root.get("organization"), organization);
        session.createQuery(criteria).executeUpdate();
    }

    public void setStudent_Patronymic(int student_id, String patronymic) throws HibernateException{
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<StudentsDataSet> criteria = builder.createCriteriaUpdate(StudentsDataSet.class);
        Root<StudentsDataSet> root = criteria.from(StudentsDataSet.class);
        criteria.where(builder.equal(root.get("id"), student_id));
        criteria.set(root.get("patronymic"), patronymic);
        session.createQuery(criteria).executeUpdate();
    }

    public void insertStudent(String name, String surname, String email, String password, String regDate) throws HibernateException {
         session.save(new StudentsDataSet(name, surname, email, password, regDate));
    }

}
