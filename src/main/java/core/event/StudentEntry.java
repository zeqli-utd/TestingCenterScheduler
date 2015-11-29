package core.event;


import javax.persistence.Embeddable;

/**
 * This Class Hold Attendees information in Ad Hoc Exam
 */
@Embeddable
public class StudentEntry {


    String netId;

    String firstName;

    String lastName;

    public StudentEntry() {

    }

    public StudentEntry(String netId, String firstName, String lastName) {
        this.netId = netId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


}
