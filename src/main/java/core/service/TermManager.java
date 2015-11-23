package core.service;

import core.event.Term;

import java.util.List;

public interface TermManager {
    Term getCurrentTerm ();
    List getAllTerms ();
}
