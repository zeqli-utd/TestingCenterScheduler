package core.event.dao;

import core.event.Exam;
import core.event.Roster;

import java.util.List;

public interface RosterDao {


    boolean addRoster(Roster roster);

    boolean updateRoster(Roster roster);

    boolean deleteRoster(String netId);

    boolean deleteRostersByTerm(int termId);

    Roster findRoster(String classId, String netId, int term);
}
