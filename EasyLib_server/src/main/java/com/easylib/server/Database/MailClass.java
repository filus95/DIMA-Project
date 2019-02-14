package com.easylib.server.Database;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

import static javax.mail.Transport.send;

public class MailClass {
    private final String emailid = "info.easylib@yahoo.com";
    private final String username = "info.easylib";
    private final String password = "progettodima2018";
    private final String host = "smtp.mail.yahoo.com";
    private final String port = "465";
    private Session l_session = null;
    private Properties props = System.getProperties();

    public MailClass() {

        emailSettings();
        createSession();
    }

    private void emailSettings() {
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "*");
          props.put("mail.smtp.socketFactory.port", port);
          props.put("mail.smtp.user", emailid);
          props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
          props.put("mail.smtp.password", password);
          props.put("mail.smtp.socketFactory.fallback", "false");
    }

    private void createSession() {

        l_session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        l_session.setDebug(true); // Enable the debug mode

    }

    public void sendMessage(String toEmail, String newPassword) {
        //System.out.println("Inside sendMessage 2 :: >> ");
        try {
            //System.out.println("Sending Message *********************************** ");
            MimeMessage message = new MimeMessage(l_session);
            //System.out.println("mail id in property ============= >>>>>>>>>>>>>> " + emailid);
            //message.setFrom(new InternetAddress(emailid));
            message.setFrom(new InternetAddress(this.emailid));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.addRecipient(Message.RecipientType.BCC, new InternetAddress(toEmail));
            message.setSubject("EASYLIB - PASSWORD RECOVERY SYSTEM");
            message.setContent("Your new password is: "+newPassword, "text/html");

            //message.setText(msg);
            Transport.send(message);
            System.out.println("Message Sent");
        } catch (Exception e) {
            e.printStackTrace();
        }//end catch block
    }

}

//    void sendMail(String to_, String newPassword) {
//



//        try {
//
//            Properties props = new Properties();
//            props.put("mail.smtp.host", "smtp.gmail.com"); // for gmail use smtp.gmail.com
//            props.put("mail.smtp.auth", "true");
//            props.put("mail.smtp.debug", "true");
//            props.put("mail.smtps.starttls.enable", "true");
//            props.put("mail.smtp.port", "587");
//            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
//            props.put("mail.setup.user", "easylib2k18");
//            props.put("mail.session.mail.smtp.password","progettodima2018");
//            props.put("mail.session.mail.transport.protocol","smtp");
//
//
//            Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {
//
//                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(serverMail, serverPassword);
//                }
//            });
//
//            mailSession.setDebug(true); // Enable the debug mode
//
//            javax.mail.Message msg = new MimeMessage(mailSession);
//
//            //--[ Set the FROM, TO, DATE and SUBJECT fields
//            msg.setFrom(new InternetAddress(serverMail));
//            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to_));
//            msg.setSentDate(new Date());
//            String title = "PASSWORD RECOVERY SERVICE";
//            msg.setSubject(title);
//
//            //--[ Create the body of the mail
//            String message = "Your new password is: ";
//            msg.setText(message + newPassword);
//
//            //--[ Ask the Transport class to send our mail message
////            Transport.send(msg);
//
//            Transport transport = mailSession.getTransport("smtp");
//            transport.connect ("smtp.gmail.com", 587, "easylib2k18@gmail.com", "progettodima2018");
//            transport.sendMessage(msg, msg.getAllRecipients());
//            transport.close();
//        } catch (Exception E) {
////            E.printStackTrace();
//            System.out.println("Oops something has gone pearshaped!");
//        }
//    }
//}

//}


