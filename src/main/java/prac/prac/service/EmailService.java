package prac.prac.service;

import jakarta.activation.FileDataSource;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prac.prac.global.BaseException;

import static prac.prac.global.BaseResponseStatus.UNABLE_TO_SEND_EMAIL;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender emailSender;

    public void sendEmail(String toEmail, String title, String imageUrl, String content) throws BaseException {
        try {
            MimeMessage message = createEmailForm(toEmail, title, imageUrl, content);
            emailSender.send(message);
        } catch (MessagingException e) {
            throw new BaseException(UNABLE_TO_SEND_EMAIL);
        }
    }

    private MimeMessage createEmailForm(String toEmail, String title, String imageUrl, String content) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject(title);
        helper.setText(content, true);
        helper.addInline("image", new ClassPathResource(imageUrl));

        return message;
    }
}