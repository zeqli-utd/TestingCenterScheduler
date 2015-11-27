package core.event;

import javax.persistence.*;

@Entity
public class Course {
    @Id
    @Column(name = "course_id" )
    @Basic(optional = false)
    private String courseId;

    @Basic(optional = false)
    @Column(name = "subject" )
    private String subject;

    @Basic(optional = false)
    @Column(name = "catalog_number")
    private int catalog;

    @Basic(optional = false)
    @Column(name = "session")
    private int session;

    @Basic(optional = false)
    @Column(name = "instructored")
    private String instructorID;

//TODO To be complete
//    @OneToMany(mappedBy = "course_id")
//    private List<Student> enrolledStudents;

    // Empty Constructor For Hibernate
    public Course() {
    }

    public Course(String courseId, String subject, String catalog, String session, String instructorID) {
        this.courseId = courseId;
        this.subject = subject;
        this.catalog = Integer.parseInt(catalog);
        this.session = Integer.parseInt(session);
        this.instructorID = instructorID;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getInstructorID() {
        return instructorID;
    }

    public void setInstructorID(String instructorID) {
        this.instructorID = instructorID;
    }

//    public List<Student> getEnrolledStudents() {
//        return this.enrolledStudents;
//    }
//
//    public void setEnrolledStudents(List<Student> enrolledStudents) {
//        this.enrolledStudents = enrolledStudents;
//    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getCatalog() {
        return catalog;
    }

    public void setCatalog(int catalog) {
        this.catalog = catalog;
    }

    public int getSession() {
        return session;
    }

    public void setSession(int session) {
        this.session = session;
    }
}
