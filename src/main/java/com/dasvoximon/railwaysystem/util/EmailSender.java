package com.dasvoximon.railwaysystem.util;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@RequiredArgsConstructor
@Component
public class EmailSender {

    @Value("${spring.mail.username}")
    private String senderEmail;
    private final JavaMailSender mailSender;

    public void sendTicketEmail(String to, String subject, String body, ByteArrayInputStream pdfStream, String pnr)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(senderEmail);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        helper.addAttachment("ticket_" + pnr + ".pdf", new ByteArrayResource(pdfStream.readAllBytes()));

        mailSender.send(message);
    }

}
