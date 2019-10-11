package DataSets;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "teacher")

public class TeachersDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name", nullable = false, length = 35)
    private String name;

    @Column(name = "surname", nullable = false, length = 40)
    private String surname;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "email", unique = true, nullable = false, length = 50)
    private String email;

    @Column(name = "password", unique = true, nullable = false, length = 50)
    private String password;

    @Column(name = "regdate")
    private String regDate;

    @OneToMany(mappedBy = "teacher_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestsDataSet> createdTests;

    @Column(name = "organization")
    private String organization;

    public TeachersDataSet(){
    }

    public TeachersDataSet(String name, String surname, String patronymic, String email, String password, String regDate) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
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

    public List<TestsDataSet> getCreatedTests() {
        return createdTests;
    }

    public void setCreatedTests(List<TestsDataSet> createdTests) {
        this.createdTests = createdTests;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "TeachersDataSet{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}