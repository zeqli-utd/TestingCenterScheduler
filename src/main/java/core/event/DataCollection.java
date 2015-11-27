package core.event;

import core.event.dao.AppointmentDaoImp;
import core.service.SessionManager;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import test.Log4J;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DataCollection {
    public static final Logger log = Logger.getLogger(Log4J.class);

    static final private String PATH = "/course_registration_data/";
    static final private String fileExt = ".csv";

    private List<String[]> list;

    private AppointmentDaoImp appointmentDaoImpl = new AppointmentDaoImp();

    //import ata from CSV file
    //0: fail; 1: user; 2: class; 3: rooster
    public int readFile(String path) {
        List<String[]> readList = new ArrayList<String[]>();
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        List<Appointment> appointmentList = appointmentDaoImpl.findAllAppointment();
        try {
            br = new BufferedReader(new FileReader(path));
            log.info("---------- readFile(String path) ----------");
            while ((line = br.readLine()) != null) {
                String[] listItem = line.split(cvsSplitBy);
                readList.add(listItem);
            }
            setList(readList);
            if (path.contains("user.csv")) {
                log.info("|  Import User Information: ");
                //add users to table;
                return 1;
            } else if (path.contains("class.csv")) {
                log.info("|  Import Class Information: ");
                return 2;
            } else if (path.contains("roster.csv")) {
                log.info("|  Import Roster Information: ");
                //change the list to hashmap
                HashMap<String, String> hash = new HashMap();
                for (int i = 0; i < list.size(); i++) {
                    hash.put(list.get(i)[0], list.get(i)[1]);
                }
                markSuperfluous(appointmentList, list);
                Session session = SessionManager.getInstance().openSession();
                Transaction tx = null;
                try {
                    tx = session.beginTransaction();
                    List e = session.createQuery("FROM Appointment WHERE Appointment.status = 's' ").list();
                    Iterator iterator = e.iterator();
                    while (iterator.hasNext()) {
                        Appointment appt = (Appointment) iterator.next();
                        if (hash.get(appt.getStudentId()) == appt.getExamId()) {
                            appt.setStatus(null);
                            //reinstate appointment
                        } else {
                            //cancel appointment
                        }
                    }
                } catch (HibernateException var9) {
                    if (tx != null) {
                        tx.rollback();
                    }

                    var9.printStackTrace();
                } finally {
                    session.close();
                }
            }
            for (int i = 0; i < list.size(); i++) {
                String[] items = list.get(i);
                for (int j = 0; j < items.length; j++) {
                    log.info("|  " + items[j]);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    return 3;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
    }

    public void markSuperfluous(List<Appointment> appointmentList, List<String[]> list) {
        Iterator databaseIterator = appointmentList.iterator();
        while (databaseIterator.hasNext()) {
            Appointment appt = (Appointment) databaseIterator.next();
            appt.setStatus("s");
            for (int i = 0; i < list.size(); i++) {
                if ((appt.getStudentId() == list.get(i)[0]) && (appt.getExamId() == list.get(i)[1])) {
                    appt.setStatus(null);
                }
            }
        }
    }

    public List<String[]> getList() {
        return list;
    }

    public void setList(List<String[]> list) {
        this.list = list;
    }
}