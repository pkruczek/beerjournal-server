package com.beerjournal.infrastructure.mail;

import com.beerjournal.infrastructure.error.BeerJournalException;
import com.beerjournal.infrastructure.error.ErrorInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    private final JavaMailSender javaMailSender;

    @Autowired
    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(MailModel mailModel) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        setMailData(mimeMessage, mailModel);
        javaMailSender.send(mimeMessage);
    }

    private void setMailData(MimeMessage mimeMessage, MailModel mailModel) {
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        try {
            helper.setTo(mailModel.getTo());
            helper.setText(mailModel.getBody(), mailModel.isHtml());
            helper.setSubject(mailModel.getSubject());
        } catch (MessagingException e) {
            throw new BeerJournalException(ErrorInfo.MAIL_NOT_SENT);
        }
    }
}
