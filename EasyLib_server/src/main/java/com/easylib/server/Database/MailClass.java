package com.easylib.server.Database;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class MailClass {
    private final String serverMail = "neofy2018@gmail.com";
    private final String serverPassword = "neofy1995";

    public void sendMail( String to, String newPassword){
        try{

            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); // for gmail use smtp.gmail.com
            props.put("mail.smtp.auth", "true");
            props.put("mail.debug", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");

            Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {

                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(serverMail, serverPassword);
                }
            });

            mailSession.setDebug(true); // Enable the debug mode

            javax.mail.Message msg = new MimeMessage( mailSession );

            //--[ Set the FROM, TO, DATE and SUBJECT fields
            msg.setFrom( new InternetAddress( serverMail ) );
            msg.setRecipients( Message.RecipientType.TO,InternetAddress.parse(to) );
            msg.setSentDate( new Date());
            String title = "PASSWORD RECOVERY SERVICE";
            msg.setSubject(title);

            //--[ Create the body of the mail
            String message = "Your new password is: ";
            msg.setText( message + newPassword );

            //--[ Ask the Transport class to send our mail message
            Transport.send( msg );

        }catch(Exception E){
            System.out.println( "Oops something has gone pearshaped!");
            System.out.println( E );
        }
    }
}
