package core.event;


import org.hibernate.annotations.Type;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDateTime;

@Embeddable
public class ETSTestTimeRangeTuple implements Serializable{

    @Type(type = "org.hibernate.type.LocalDateTime")
    private LocalDateTime testDateTimeFrom;

    @Type(type = "org.hibernate.type.LocalDateTime")
    private LocalDateTime teseDateTimeTo;

    public ETSTestTimeRangeTuple() {
    }

    public LocalDateTime getTestDateTimeFrom() {
        return testDateTimeFrom;
    }

    public void setTestDateTimeFrom(LocalDateTime testDateTimeFrom) {
        this.testDateTimeFrom = testDateTimeFrom;
    }

    public LocalDateTime getTeseDateTimeTo() {
        return teseDateTimeTo;
    }

    public void setTeseDateTimeTo(LocalDateTime teseDateTimeTo) {
        this.teseDateTimeTo = teseDateTimeTo;
    }
}
