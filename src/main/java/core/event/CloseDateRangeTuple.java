package core.event;

import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class CloseDateRangeTuple implements Serializable{

    @Type(type = "org.hibernate.type.LocalDate")
    private LocalDate closeDateFrom;

    @Type(type = "org.hibernate.type.LocalDate")
    private LocalDate closeDateTo;

    public CloseDateRangeTuple() {
    }

    public LocalDate getCloseDateFrom() {
        return closeDateFrom;
    }

    public void setCloseDateFrom(LocalDate closeDateFrom) {
        this.closeDateFrom = closeDateFrom;
    }

    public LocalDate getCloseDateTo() {
        return closeDateTo;
    }

    public void setCloseDateTo(LocalDate closeDateTo) {
        this.closeDateTo = closeDateTo;
    }
}
