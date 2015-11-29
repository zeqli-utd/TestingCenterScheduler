package core.user;

import core.event.Appointment;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Student")
public class Student extends UserType {


    // Empty Constructor for Hibernate
    public Student() {
    }

    public Student(String netId, String firstName, String lastName, String email) {
        this.netId = netId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

}
