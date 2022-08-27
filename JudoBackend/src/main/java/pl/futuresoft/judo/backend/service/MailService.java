package pl.futuresoft.judo.backend.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.futuresoft.judo.backend.configuration.MailConfiguration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

@Slf4j
@Service
public class MailService {

    private final MailConfiguration mailConfiguration;

    public MailService( MailConfiguration mailConfiguration) {
        this.mailConfiguration = mailConfiguration;
    }

    private void sendEmail(Session session, String toEmail, String subject, String body){
        try
        {
            MimeMessage msg = new MimeMessage(session);
            StringTokenizer stringTokenizer= new StringTokenizer(mailConfiguration.getFromEmail(),"@");
            String personalInternetAddress = stringTokenizer.nextToken();
            msg.setFrom(new InternetAddress(mailConfiguration.getFromEmail(),personalInternetAddress));
            msg.setReplyTo(InternetAddress.parse(mailConfiguration.getFromEmail(), false));
            msg.setSubject(MimeUtility.encodeText(subject, "UTF-8","B"), "UTF-8");
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.setContent(body, "text/html; charset=UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            log.info("Message is ready for "+ toEmail);
            Transport.send(msg);
            log.info("Email Sent Successfully to: " + toEmail);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void confSmtpHostAndSendEmail(String toEmail, String subject, String body) {
        log.info("Email Start");
        Properties props = new Properties();
        props.put("mail.smtp.host", mailConfiguration.getMailSmtpHost()); //SMTP Host
        props.put("mail.smtp.port", mailConfiguration.getMailSmtpPort()); //TLS Port
        props.put("mail.smtp.auth", mailConfiguration.getMailSmtpAuth()); //enable authentication
        props.put("mail.smtp.starttls.enable", mailConfiguration.getMailSmtpStarttlsEnable()); //enable STARTTLS

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(mailConfiguration.getFromEmail(), mailConfiguration.getPassword());
            }
        };

        Session session = Session.getInstance(props, auth);
        session.setDebug(true);
        session.setDebugOut(System.out);
        sendEmail(session, toEmail, subject, body);
      }
}

