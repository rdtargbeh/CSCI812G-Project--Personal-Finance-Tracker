package csci812_project.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com"); // ✅ Set your SMTP host
        mailSender.setPort(587); // ✅ Use the correct SMTP port

        mailSender.setUsername("your-email@gmail.com"); // ✅ Use your email
        mailSender.setPassword("your-email-password"); // ✅ Use an app password for security

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // ✅ Enable debugging logs

        return mailSender;
    }
}
