package core.event.dao;

import core.event.Exam;
import core.event.Roster;
import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by Zeqli on 11/26/2015.
 */
public class RosterDaoImp implements RosterDao{

    @Override
    public boolean addRoster(Roster roster) {
            Session session = SessionManager.getInstance().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(roster);
            }
            catch (HibernateException he){
                if(tx != null){
                    tx.rollback();
                }
                return false;
            } finally {
                session.close();
            }
            return true;
    }

    @Override
    public boolean updateRoster(Roster roster) {
        return false;
    }

    @Override
    public boolean deleteRoster(String netId) {
        return false;
    }

    @Override
    public boolean deleteRostersByTerm(String termId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("delete from roster where roster.term = :termId");
            query.setParameter("termId", termId);
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
            return false;
        } finally {
            session.close();
        }
        return true;
    }
}
