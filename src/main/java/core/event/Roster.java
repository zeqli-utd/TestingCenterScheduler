package core.event;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Roster {
    @Id
    @Basic(optional = false)
    @Column(name = "net_id")
    private String netID;

    @Id
    @Basic(optional = false)
    @Column(name = "course_id")
    private String CourseID;

    public Roster(String netID, String courseID) {
        this.netID = netID;
        this.CourseID = courseID;
    }

    public String getNetID() {
        return netID;
    }

    public String getCourseID() {
        return CourseID;
    }

    public void setNetID(String netID) {
        this.netID = netID;
    }

    public void setCourseID(String courseID) {
        CourseID = courseID;
    }

}
