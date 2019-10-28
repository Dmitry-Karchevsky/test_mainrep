package DataSets;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "Student")
public class StudentsDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;

    @Column(name = "name", nullable = false, length = 35)
    private String name;

    @Column(name = "surname", nullable = false, length = 40)
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "email", updatable = false, unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "password", nullable = false, length = 50)
    private String password;

    @Column(name = "regdate")
    private String regDate;

    @Column(name = "organization", length = 50)
    private String organization;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "mark_student", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "mark_id"))
    private Set<MarksDataSet> mark_id = new HashSet<>();

    @OneToOne(mappedBy = "student_id")
    private AnswersDataSet answer_id;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "test_student", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "test_id"))
    private Set<TestsDataSet> passedTests_id = new HashSet<>();

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "students_groups", joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    private Set<GroupsDataSet> groups_id = new HashSet<>();

    public StudentsDataSet(){
    }

    public StudentsDataSet(String name, String surname, String patronymic, String email, String password, String regDate, String organization) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
        this.email = email;
        this.password = password;
        this.regDate = regDate;
        this.organization = organization;
    }

    public StudentsDataSet(String name, String surname, String email, String password, String regDate) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.regDate = regDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "StudentsDataSet{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }

    public Set<MarksDataSet> getMark_id() {
        return mark_id;
    }

    public void setMark_id(Set<MarksDataSet> mark_id) {
        this.mark_id = mark_id;
    }

    public Set<TestsDataSet> getPassedTests_id() {
        return passedTests_id;
    }

    public void setPassedTests_id(Set<TestsDataSet> passedTests_id) {
        this.passedTests_id = passedTests_id;
    }

    public Set<GroupsDataSet> getGroups_id() {
        return groups_id;
    }

    public void setGroups_id(Set<GroupsDataSet> groups_id) {
        this.groups_id = groups_id;
    }

    public AnswersDataSet getAnswer_id() {
        return answer_id;
    }

    public void setAnswer_id(AnswersDataSet answer_id) {
        this.answer_id = answer_id;
    }
}
