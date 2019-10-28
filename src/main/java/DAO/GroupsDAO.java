package DAO;

import DataSets.GroupsDataSet;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class GroupsDAO {

    private Session session;

    public GroupsDAO(Session session) {
        this.session = session;
    }

    public GroupsDataSet get(long id) throws HibernateException {
        return (GroupsDataSet) session.get(GroupsDataSet.class, id);
    }

    public List<GroupsDataSet> getGroupsByName(String name) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GroupsDataSet> cq = cb.createQuery(GroupsDataSet.class);
        Root<GroupsDataSet> root = cq.from(GroupsDataSet.class);
        cq.select(root);
        cq.where(cb.equal(root.get("name"), name));
        List<GroupsDataSet> groups;
        Query<GroupsDataSet> query = session.createQuery(cq);
        try{
            groups = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Групп с таким названием не найдено");
            return null;
        }
        return groups;
    }

    public List<GroupsDataSet> getGroupsByTeacher(long teacher_id) throws HibernateException {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<GroupsDataSet> cq = cb.createQuery(GroupsDataSet.class);
        Root<GroupsDataSet> root = cq.from(GroupsDataSet.class);
        cq.select(root);
        cq.where(cb.equal(root.get("teacher_id"), teacher_id));
        List<GroupsDataSet> groups = new ArrayList<GroupsDataSet>();
        Query<GroupsDataSet> query = session.createQuery(cq);
        try{
            groups = query.getResultList();
        }catch (NoResultException ex){
            System.out.println("Групп созданнs[ этим преподавателем не существует");
            return null;
        }
        return groups;
    }

    public void insertGroup(String name) throws HibernateException {
        session.save(new GroupsDataSet(name));
    }
}
