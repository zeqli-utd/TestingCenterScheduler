package core.event;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
public class Roster {
    @Id
    @Basic(optional = false)
    @OneToOne(mappedBy = "roster")
    private String classId;


    @ElementCollection
    @CollectionTable(name="netids", joinColumns=@JoinColumn(name="netid"))
    private Set<String> netIds;

    public Roster(String classId, Set<String> netIds) {
        this.classId = classId;
        this.netIds = netIds;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Set<String> getNetIds() {
        return netIds;
    }

    public void setNetIds(Set<String> netIds) {
        this.netIds = netIds;
    }
}
