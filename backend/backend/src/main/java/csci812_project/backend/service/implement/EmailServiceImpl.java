package csci812_project.backend.service.implement;

import csci812_project.backend.service.EmailService;
import lombok.RequiredArgsConstructor;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendBudgetAlert(String email, String categoryName, BigDecimal budgetLimit) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("⚠️ Budget Alert: You're close to exceeding your limit!");
            helper.setText("Dear user, <br><br>"
                    + "You have spent nearly all of your budget for **" + categoryName + "**. <br>"
                    + "Your limit is: **$" + budgetLimit + "**. <br>"
                    + "Please manage your expenses wisely! <br><br>"
                    + "Regards, <br>Finance Tracker Team", true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email alert: " + e.getMessage());
        }
    }
}
