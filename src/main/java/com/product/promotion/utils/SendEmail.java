package com.product.promotion.utils;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SendEmail {

    private final JavaMailSender javaMailSender;

    @Autowired
    public SendEmail(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(String setTo, String setSubject, String setText) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(setTo);
        msg.setSubject(setSubject);
        msg.setText(setText);
        javaMailSender.send(msg);
    }
}
