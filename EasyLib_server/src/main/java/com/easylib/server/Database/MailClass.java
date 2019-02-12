package com.easylib.server.Database;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;

public class MailClass {
    private final String serverMail = "easylib2k18";
    private final String serverPassword = "progettodima2018";

//    public void sendMail(String to_, String newPassword) {
//
//        String USER_NAME = "easylib2k18";  // GMail user name (just the part before "@gmail.com")
//        String PASSWORD = "progettodima2018"; // GMail password
//
//        String from = USER_NAME;
//        String pass = PASSWORD;
//        String[] to = {to_}; // list of recipient email addresses
//        String subject = "Java send mail example";
//        String body = "Welcome to JavaMail!";
//
//
//        Properties props = System.getProperties();
//        String host = "smtp.gmail.com";
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.user", from);
//        props.put("mail.smtp.password", pass);
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//
//        Session session = Session.getInstance(props);
//        MimeMessage message = new MimeMessage(session);
//
//        try {
//            message.setFrom(new InternetAddress(from));
//            InternetAddress[] toAddress = new InternetAddress[to.length];
//
//            // To get the array of addresses
//            for (int i = 0; i < to.length; i++) {
//                toAddress[i] = new InternetAddress(to[i]);
//            }
//
//            for (int i = 0; i < toAddress.length; i++) {
//                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
//            }
//
//            message.setSubject(subject);
//            message.setText(body);
//            Transport transport = session.getTransport("smtp");
//            transport.connect(host, from, pass);
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
//        } catch (Exception ae) {
//            ae.printStackTrace();
//        }
//    }
//}


    void sendMail(String to_, String newPassword) {

//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(serverMail, serverPassword);
//                    }
//                });
//
//        try {
//        Message message = new MimeMessage(session);
//
//            message.setFrom(new InternetAddress(serverMail));
//
//        message.setRecipients(Message.RecipientType.TO,
//                InternetAddress.parse(to_));
//        message.setSubject("Testing Subject");
//        message.setText("Dear Mail Crawler,"
//                + "\n\n No spam to my email, please!");
//
//
//        Transport.send(message);
//        System.out.println("Done");
//
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//    }
//}


        try {

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // for gmail use smtp.gmail.com
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.debug", "true");
            props.put("mail.smtps.starttls.enable", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.setup.user", "easylib2k18");
            props.put("mail.session.mail.smtp.password","progettodima2018");
            props.put("mail.session.mail.transport.protocol","smtp");


            Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(serverMail, serverPassword);
                }
            });

            mailSession.setDebug(true); // Enable the debug mode

            javax.mail.Message msg = new MimeMessage(mailSession);

            //--[ Set the FROM, TO, DATE and SUBJECT fields
            msg.setFrom(new InternetAddress(serverMail));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to_));
            msg.setSentDate(new Date());
            String title = "PASSWORD RECOVERY SERVICE";
            msg.setSubject(title);

            //--[ Create the body of the mail
            String message = "Your new password is: ";
            msg.setText(message + newPassword);

            //--[ Ask the Transport class to send our mail message
//            Transport.send(msg);

            Transport transport = mailSession.getTransport("smtp");
            transport.connect ("smtp.gmail.com", 587, "easylib2k18@gmail.com", "progettodima2018");
            transport.sendMessage(msg, msg.getAllRecipients());
            transport.close();
        } catch (Exception E) {
//            E.printStackTrace();
            System.out.println("Oops something has gone pearshaped!");
        }
    }
}

//}


