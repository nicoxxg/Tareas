package com.universidad.tareas.services;

import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {
    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}") private String sender;

    public void sendEmail(String toEmail,
                          String subject,
                          String body){
        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("unitariaescuela@gmail.com");
            message.setTo(toEmail);
            message.setText(body);
            message.setSubject(subject);

            mailSender.send(message);
            System.out.println("el email se ah enviado con exito");

        }catch (Exception e){
            System.out.println("Error while Sending Mail :" + e);

        }
    }
}
