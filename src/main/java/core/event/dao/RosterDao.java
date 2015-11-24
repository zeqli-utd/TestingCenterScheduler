package core.event.dao;

import core.event.Exam;
import core.event.Roster;

import java.util.List;

public interface RosterDao {

    List<Exam> findBynetId(String netId);

    boolean addRoster(Roster roster);

    boolean updateRoster(Roster roster, String netId);

    boolean deleteRoster(String netId);


}
