package test;

import core.event.Term;
import core.service.TermManagerService;

import java.time.LocalDate;


public class DBInitialization {

    public static void main(String args[]){
        // 1. Init Term
        initTerm();

        // 2. Init User
    }

    public static void initTerm(){
        // 1. Initialize Term
        Term winter2015 = new Term(1151, LocalDate.of(2015, 1, 6), LocalDate.of(2015, 1,24) );
        Term spring2015 = new Term(1154,LocalDate.of(2015, 1, 26), LocalDate.of(2016, 5, 22));
        Term summer2015 = new Term(1156,LocalDate.of(2016, 5, 25), LocalDate.of(2016, 8, 15));
        Term fall2015 = new Term(1158,LocalDate.of(2016, 8, 24), LocalDate.of(2016, 12, 17));

        Term winter2016 = new Term(1161,LocalDate.of(2016, 1,18), LocalDate.of(2016, 1,24) );
        Term spring2016 = new Term(1164,LocalDate.of(2016, 1, 25), LocalDate.of(2016, 5,20));
        Term summer2016 = new Term(1166,LocalDate.of(2016, 5,30), LocalDate.of(2016, 8,20));
        Term fall2016 = new Term(1168,LocalDate.of(2016, 8, 29), LocalDate.of(2016, 12, 22));

        Term winter2017 = new Term(1171,LocalDate.of(2017, 1, 2), LocalDate.of(2017, 1, 21) );
        Term spring2017 = new Term(1174,LocalDate.of(2017, 1, 23), LocalDate.of(2017, 5,19));
        Term summer2017 = new Term(1176,LocalDate.of(2017, 5, 29), LocalDate.of(2017, 8,19));
        Term fall2017 = new Term(1178,LocalDate.of(2017, 1, 24), LocalDate.of(2017, 5, 20));

        TermManagerService tms = new TermManagerService();
        tms.insertTerm(winter2015);
        tms.insertTerm(spring2015);
        tms.insertTerm(summer2015);
        tms.insertTerm(fall2015);

        tms.insertTerm(winter2016);
        tms.insertTerm(spring2016);
        tms.insertTerm(summer2016);
        tms.insertTerm(fall2016);

        tms.insertTerm(winter2017);
        tms.insertTerm(spring2017);
        tms.insertTerm(summer2017);
        tms.insertTerm(fall2017);

    }

    public static void initUserAccount(){



    }
}
