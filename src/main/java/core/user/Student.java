package core.user;

import javax.persistence.*;

@Entity
@Table(name = "Student")
public class Student extends UserType {
//    @Basic(optional = false)
//    public static final Authorization permission = Authorization.STUDENT;
//
//    @OneToMany(mappedBy = "studentID")
//    private List<Appointment> appointments;

//    private String name;
//    private int student_Id;
    // Edit by Zeqing Li. We don't need them right now

    // Empty Constructor for Hibernate
    public Student() {
    }

    public Student(String netId, String pwd, String firstName, String lastName, String email) {
        this.netId = netId;
        this.password = pwd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }


 /*   public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStudent_Id() {
        return student_Id;
    }

    public void setStudent_Id(int student_Id) {
        this.student_Id = student_Id;
    }
    */
//    public List<Appointment>
//            (){
//        return appointments;
//    }
//
//    public void setAppointments(List<Appointment> appointments) {
//        this.appointments = appointments;
//    }
//
//    public String toString(){
//        String s  = "Student Information: \n" +
//                "NetId: " + getNetId() + "\n" +
//                "First Name: " + getFirstName() + "\n" +
//                "Last Name " + getLastName() + "\n";
//        return s;
//    }
}
