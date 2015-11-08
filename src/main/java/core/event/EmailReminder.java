package core.event;//package core.event;
//
//import javax.mail.*;
//import javax.mail.internet.*;
//import java.time.LocalDateTime;
//import java.util.Properties;
//
//public class EmailReminder {
//    String from = "appointmentReminder@stonybrook.edu";
//
//    private boolean remind(String[] to, String semester, String subjectNum, int catalogNumber, int sessionNum,
//                           LocalDateTime[] range){
//        boolean status=false;
//        try {
//            Properties p = System.getProperties();
//            String Host_Name = "Put your Host Name";
//            p.put("mail.smtp.host", Host_Name);
//            Session session = Session.getDefaultInstance(p, null);
//            MimeMessage message = new MimeMessage(session);
//            String mailFrom = "Put From Info";
//            String mailTo = "Put TO Info";
//            message.setFrom(new InternetAddress(mailFrom));
//            InternetAddress[] mailAddressTO = new InternetAddress[to.length];
//            for (int i = 0; i < to.length; i++) {
//                mailAddressTO[i] = new InternetAddress(to[i]);
//            }
//            message.addRecipients(Message.RecipientType.TO, mailAddressTO);
//            String subject = "Reminder of Appointment";
//            message.setSubject(subject);
//            String content = "Appointment for " + subjectNum + catalogNumber + " session " + sessionNum + " in " +
//                    semester + " is successfully made.\nStartDateTime: " + range[0] + "\nEndDateTime: " +range[1];
//            message.setContent(content, "text/html");
//            Transport.send(message);
//            status = true;
//        }
//        catch(SendFailedException sfe){
//        }
//        catch(MessagingException me){
//        }
//        return status;
//    }
//}
