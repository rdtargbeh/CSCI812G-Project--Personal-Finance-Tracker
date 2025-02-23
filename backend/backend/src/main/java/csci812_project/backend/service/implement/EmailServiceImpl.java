package csci812_project.backend.service.implement;

import csci812_project.backend.service.EmailService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.math.BigDecimal;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

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

    /**
     * ✅ Sends a loan payment reminder email
     */
    @Override
    public void sendLoanReminder(String toEmail, String loanName, String dueDate, BigDecimal amountDue) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("Loan Payment Reminder - " + loanName);
            helper.setText(
                    "Hello,\n\n" +
                            "This is a friendly reminder that your loan payment for **" + loanName + "** is due on **" + dueDate + "**.\n" +
                            "The amount due is: **$" + amountDue + "**.\n\n" +
                            "Please ensure timely payment to avoid late fees.\n\n" +
                            "Best regards,\nYour Personal Finance Tracker", true
            );

            mailSender.send(message);
            System.out.println("✅ Loan Reminder Sent to: " + toEmail);
        } catch (MessagingException e) {
            throw new RuntimeException("❌ Error sending loan reminder email: " + e.getMessage());
        }
    }
}
