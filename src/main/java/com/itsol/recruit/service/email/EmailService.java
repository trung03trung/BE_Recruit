package com.itsol.recruit.service.email;

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

    public String buildMailInterview(String jobName,String time,
                                     String date,String mediatype,
                                     String jeName,String jePhone,
                                     String userName){
        return "<p>Dear anh/chị "+userName+" <p><br>"
                +"<p>Công ty ITSOL rất vui và vinh hạnh khi nhận được hồ sơ ứng tuyển của anh/chị vào vị trí "
                +jobName+". Chúng tôi đã nhận được CV của anh/chị và mong muốn có một cuộc phỏng vấn để trao "
                +"đổi trực tiếp về kiến thức cũng như công việc mà anh/chị đã ứng tuyển.</p><br>"
                +"<p>Thời gian phỏng vấn dự kiến vào lúc "+time+" ngày "+date+" qua công cụ "+mediatype
                +"(chúng tôi sẽ gửi lại link sau khi anh/chị xác nhận đồng ý phỏng vấn bằng các reply lại mail này).</p><br>"
                +"<p>Chúng tôi rất hy vọng anh/chị sớm phản hồi và mong rằng chúng ta sẽ được hợp tác cùng nhau trong tương lai.</p><br>"
                +"<p>Mọi thắc mắc xin vui lòng liên hệ tới anh "+jeName+", SĐT: "+jePhone+" trong giờ hành chính để được giải đáp.</p><br>"
                +"<p>Thanks & best regards,</p><br><p>ITSOL JSC</p><br><p>Head office: Tầng 3, tòa nhà 3A, ngõ 82, phố Duy Tân, phường Dịch Vọng Hậu,quận Cầu Giấy, Hà Nội</p><br>"
                +"<p>Hotline: 0123456789</p>";
    }
}
