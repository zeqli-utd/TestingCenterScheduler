package core.event.dao;

import core.event.Roster;
import core.service.SessionManager;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class RosterDaoImp implements RosterDao{

    public RosterDaoImp() {
    }

    @Override
    public boolean addRoster(Roster roster) {
            Session session = SessionManager.getInstance().openSession();
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(roster);
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

    @Override
    public boolean updateRoster(Roster roster) {
        return false;
    }

    @Override
    public boolean deleteRoster(String netId) {
        return false;
    }

    @Override
    public boolean deleteRostersByTerm(int termId){
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Query query = session.createQuery
                    ("delete from Roster where Roster.termId = :termId");
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

    @Override
    public Roster findRoster(String classId, String netId, int term) {
        Session session = SessionManager.getInstance().openSession();
        Transaction tx = null;
        Roster roster = null;
        try {
            tx = session.beginTransaction();
            roster = (Roster)session.get(Roster.class, new Roster(classId, netId, term));
            tx.commit();
        }
        catch (HibernateException he){
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
            return roster;
        }
    }
}
