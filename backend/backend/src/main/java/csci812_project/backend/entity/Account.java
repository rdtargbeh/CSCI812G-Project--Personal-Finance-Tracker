package csci812_project.backend.entity;

import csci812_project.backend.enums.AccountType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "accounts")
@Builder
public class Account {

    /**
     * Unique account ID (Primary Key).
     * Auto-generated by the database.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    /**
     * Foreign Key linking this account to a user.
     * Ensures that each account belongs to a specific user.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Name of the account (e.g., "Savings Account", "Personal Wallet").
     * Required field.
     */
    @Column(name = "name", nullable = false, length = 100)
    @NotBlank(message = "Account name is required")
    private String name;

    /**
     * Type of the account.
     * Must be one of the predefined ENUM values.
     */
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountType type;

    /**
     * Current balance in the account.
     * Cannot be negative.
     * Default value is 0.00.
     */
    @Column(name = "balance", nullable = false, precision = 15, scale = 2)
    @DecimalMin(value = "0.00", message = "Balance cannot be negative")
    private BigDecimal balance = BigDecimal.ZERO;

    /**
     * Currency code (ISO 4217 format, e.g., "USD", "EUR").
     * Defaults to "USD".
     */
    @Column(name = "currency", nullable = false, length = 3)
    @Pattern(regexp = "^[A-Z]{3}$", message = "Currency must be a valid 3-letter ISO code")
    private String currency = "USD";

    /**
     * Name of the financial institution (e.g., "Bank of America").
     * Optional field.
     */
    @Column(name = "institution_name", length = 100)
    private String institutionName;

    /**
     * Account number for bank accounts.
     * Must be unique.
     */
    @Column(name = "account_number", unique = true, length = 50)
    private String accountNumber;

    /**
     * Interest rate applicable to the account (if any).
     * Cannot be negative.
     */
    @Column(name = "interest_rate", precision = 5, scale = 2)
    @DecimalMin(value = "0.00", message = "Interest rate cannot be negative")
    private BigDecimal interestRate;

    /**
     * Marks whether this is the default account for the user.
     * Defaults to false.
     */
    @Column(name = "is_default", nullable = false)
    private boolean isDefault = false;

    /**
     * Soft delete flag to prevent accidental deletion.
     * Instead of removing the account, set this flag to TRUE.
     */
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;

    /**
     * Timestamp for when the account was created.
     * Automatically set when a new record is inserted.
     */
    @Column(name = "date_created", updatable = false)
    private LocalDateTime dateCreated = LocalDateTime.now();

    /**
     * Timestamp for when the account was last updated.
     * Automatically updates on modification.
     */
    @Column(name = "date_updated")
    private LocalDateTime dateUpdated = LocalDateTime.now();

    /**
     * Lifecycle hook to update the timestamp before updating.
     */
    @PreUpdate
    protected void onUpdate() {
        this.dateUpdated = LocalDateTime.now();
    }
}

