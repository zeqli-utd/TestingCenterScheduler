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


    public boolean insertTerm (Term term){

        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.saveOrUpdate(term);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) {
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    public Term getTermById(int termId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        Term term = null;
        try {
            tx = session.beginTransaction();
            term = (Term)session.get(Term.class, termId);
            tx.commit();
        } catch (HibernateException e) {
            if(tx != null) {
                tx.rollback();
            }
            term = new Term();
            return term;
        } finally {
            session.close();
        }
        return term;

    }
}
