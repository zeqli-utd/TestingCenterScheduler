package core.user;

import core.event.Exam;

import javax.persistence.*;
import java.util.List;
//import javax.persistence.Entity;

@Entity
@Table( name = "Instructor")
public class Instructor extends UserType {

    // Empty Constructor for Hibernate
    public Instructor() { }

    public Instructor(String netId, String firstName, String lastName, String email) {
        this.netId = netId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
