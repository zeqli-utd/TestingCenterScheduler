package core.service;


import core.event.Term;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TermManagerService {

    public List<Term> getAllPopulatedTerms(){

        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        List<Term> terms = new ArrayList<>();
        try {
            tx = session.beginTransaction();
            terms = session.createQuery("FROM core.event.Term").list();
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) {
                tx.rollback();
            }
        } finally {
            session.close();
        }
        return terms;
    }
}
