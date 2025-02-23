package csci812_project.backend.service;

import java.math.BigDecimal;

public interface EmailService {

    void sendBudgetAlert(String email, String categoryName, BigDecimal budgetLimit);

    void sendLoanReminder(String toEmail, String loanName, String dueDate, BigDecimal amountDue);

    void sendSavingsGoalReminder(Long userId, String goalName, BigDecimal progress);

    /** ✅ Send savings contribution confirmation */
    void sendSavingsContributionEmail(Long userId, String goalName, BigDecimal contributionAmount);

}

