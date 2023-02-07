package com.rapipay.wrapperutility.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class EmailUtility {
    private static Logger log = LogManager.getLogger(EmailUtility.class);

    @Autowired
    private static JavaMailSender javaMailSender;

    public EmailUtility(JavaMailSender javaMailSender,String sender) {
        this.javaMailSender=javaMailSender;
        this.sender=sender;
    }


    @Value("${spring.mail.username}") private static String sender;

    public String sendSimpleMail(String recipient, String msgBody, String subject)
    {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(recipient);
            mailMessage.setText(msgBody);
            mailMessage.setSubject(subject);
            javaMailSender.send(mailMessage);
            return "Mail Sent Successfully...";
        }
        catch (Exception e) {
            log.error("Error while sending Simple email ", e);
            return "Error while Sending Mail";
        }
    }

    public static String sendMailWithAttachment(String recipient, String msgBody, String subject, String attachment)  {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(recipient);
            mimeMessageHelper.setText(msgBody);
            mimeMessageHelper.setSubject(subject);
            FileSystemResource file = new FileSystemResource(new File(attachment));
            mimeMessageHelper.addAttachment(file.getFilename(), file);//NOSONAR
            javaMailSender.send(mimeMessage);
            return "Mail sent Successfully";
        }
        catch (MessagingException e) {
            log.error("Error while sending Simple email ", e);
            return "Error while sending mail!!!";
        }
    }
}
