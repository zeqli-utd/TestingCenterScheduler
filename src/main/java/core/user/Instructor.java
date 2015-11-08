package core.user;

import javax.persistence.Entity;
import javax.persistence.Table;
//import javax.persistence.Entity;

@Entity
@Table( name = "Instructor")
public class Instructor extends UserType {
//    @Basic(optional = false)
//    public static final Authorization authLevel = Authorization.INSTRUCTOR;

    // Comments this because it cause Runtime Error When Hibernate Do Mapping
//
//    @OneToMany(mappedBy = "intructor_id")
//    private List<Reservation> reservations;

    // Empty Constructor for Hibernate
    public Instructor() { }

    public Instructor(String netId, String pwd, String firstName, String lastName, String email) {
        this.netId = netId;
        this.password = pwd;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

//    public List<Reservation> getReservations() {
//        return reservations;
//    }
//
//    public void setReservations(List<Reservation> reservations) {
//        this.reservations = reservations;
//    }


}
