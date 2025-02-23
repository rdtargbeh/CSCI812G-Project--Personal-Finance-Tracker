package csci812_project.backend.service;

import java.math.BigDecimal;

public interface EmailService {
    void sendBudgetAlert(String email, String categoryName, BigDecimal budgetLimit);
}

