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

    public Roster() {
    }

    public Roster(String classId, String netIds) {
        this.classId = classId;
        this.netId = netIds;
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
}
