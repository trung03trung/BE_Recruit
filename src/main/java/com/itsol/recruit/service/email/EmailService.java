package com.itsol.recruit.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendEmail(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Confirm your email");
            helper.setFrom("trung03trung@gmail.com");
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new IllegalStateException("failed to send email");
        }

    }
    public String buildOtpEmail(String name,String otp){
        return "<p>Xin chào "+ name +".Nhập mã OTP như dưới đây dể đổi mật khẩu </p>"
                +"<br>"+"<h3>"+otp+"</h3>"+"<br>"
                +"<p>Mã OTP này sẽ hết hạn trong 5 phút</p>";

    }

    public String buildActiveLink(String link){
        return  "Link active account"+
                "<a href=\" " + link + "\">Click vào đây để kích hoạt tài khoản</a>";

    }


}
