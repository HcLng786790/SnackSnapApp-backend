package com.huuduc.snacksnap.service.impl;

import com.huuduc.snacksnap.data.dto.MailDTPRequest;
import com.huuduc.snacksnap.service.MailService;
import com.huuduc.snacksnap.service.UserService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MailServiceImpl implements MailService {

    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${url.redirect.path}")
    private String urlRedirect;


    @Override
    public String send(MailDTPRequest mailDTPRequest) {
        try {

            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom(fromMail);
            helper.setTo(mailDTPRequest.getTo());
            helper.setSubject(mailDTPRequest.getSubject());
            helper.setText(mailDTPRequest.getBody(), true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "Success";
    }

    @Override
    public void sendOtpEmail(String email, String otp) {

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.setFrom(fromMail);
            helper.setTo(email);
            helper.setSubject("Verify account");
            helper.setText("Your otp: " + otp);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
