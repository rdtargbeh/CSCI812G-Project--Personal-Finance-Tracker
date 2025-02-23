package csci812_project.backend.service;

import java.math.BigDecimal;

public interface EmailService {

    void sendBudgetAlert(String email, String categoryName, BigDecimal budgetLimit);

    void sendLoanReminder(String toEmail, String loanName, String dueDate, BigDecimal amountDue);
}

