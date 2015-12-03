package core.event;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "roster")
public class Roster implements Serializable{

    @Id
    private String classId;

    @Id
    private String netId;

    private int termId;

    public Roster() {
        classId = "DEFAULT_CLASSID";
        netId = "DEFAULT_NETID";
        termId = 1158;
    }

    public Roster(String classId, String netIds, int termId) {
        this.classId = classId;
        this.netId = netIds;
        this.termId = termId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getNetId() {
        return netId;
    }

    public void setNetId(String netId) {
        this.netId = netId;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }
}
