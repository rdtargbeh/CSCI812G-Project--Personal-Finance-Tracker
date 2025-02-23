
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(50) UNIQUE NOT NULL
    first_name VARCHAR(30) NOT NULL,
    last_name VARCHAR(30) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL CHECK (email LIKE '%@%.%'),
    password VARCHAR(80) NOT NULL,
    phone_number int(15)
    address VARCHAR(100)
    currency VARCHAR(3) DEFAULT 'USD' CHECK (LENGTH(currency) = 3),
    timezone VARCHAR(50) DEFAULT 'UTC',
    profile_picture TEXT,
    notification_preferences JSON,
    preferred_language VARCHAR(20) DEFAULT 'en',
    is_deleted BOOLEAN DEFAULT FALSE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Index for faster email lookup
CREATE INDEX idx_user_email ON users(email);


CREATE TABLE accounts (
    account_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    type ENUM('BANK', 'CASH', 'CREDIT_CARD', 'WALLET') NOT NULL,
    balance DECIMAL(15,2) DEFAULT 0.00 CHECK (balance >= 0),
    currency VARCHAR(3) DEFAULT 'USD' CHECK (LENGTH(currency) = 3),
    institution_name VARCHAR(100),
    account_number VARCHAR(50) UNIQUE,
    interest_rate DECIMAL(5,2) DEFAULT NULL CHECK (interest_rate >= 0),
    is_default BOOLEAN DEFAULT FALSE,
    is_deleted BOOLEAN DEFAULT FALSE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Index for faster account queries
CREATE INDEX idx_account_user ON accounts(user_id);


CREATE TABLE categories (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type ENUM('INCOME', 'EXPENSE') NOT NULL,
    icon VARCHAR(50),
    color_code VARCHAR(10),
    user_id BIGINT DEFAULT NULL,
    is_deleted BOOLEAN DEFAULT FALSE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_category_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE SET NULL
);

-- Index for user-defined categories
CREATE INDEX idx_category_user ON categories(user_id);


CREATE TABLE transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    account_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    amount DECIMAL(15,2) NOT NULL CHECK (amount >= 0),
    transaction_type ENUM('INCOME', 'EXPENSE', 'TRANSFER') NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    payment_method ENUM('CASH', 'CREDIT_CARD', 'BANK_TRANSFER', 'PAYPAL'),
    receipt_url TEXT,
    recurring BOOLEAN DEFAULT FALSE,
    parent_transaction_id BIGINT DEFAULT NULL,
    status ENUM('PENDING', 'COMPLETED', 'FAILED') DEFAULT 'COMPLETED',
    is_deleted BOOLEAN DEFAULT FALSE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (account_id) REFERENCES accounts(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL,
    FOREIGN KEY (parent_transaction_id) REFERENCES transactions(id) ON DELETE SET NULL
);

-- Index for optimizing transaction queries
CREATE INDEX idx_transaction_date ON transactions(date);
CREATE INDEX idx_transaction_status ON transactions(status);
CREATE INDEX idx_transaction_category ON transactions(category_id);


CREATE TABLE budgets (
    budget_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    amount_limit DECIMAL(15,2) NOT NULL CHECK (amount_limit >= 0),
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    budget_type ENUM('STRICT', 'FLEXIBLE') DEFAULT 'FLEXIBLE',
    rollover_amount DECIMAL(15,2) DEFAULT NULL CHECK (rollover_amount >= 0),
    is_deleted BOOLEAN DEFAULT FALSE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE SET NULL
);


CREATE TABLE savings_goals (
    saving_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    goal_name VARCHAR(100) NOT NULL,
    target_amount DECIMAL(15,2) NOT NULL CHECK (target_amount >= 0),
    current_amount DECIMAL(15,2) DEFAULT 0.00 CHECK (current_amount >= 0),
    deadline DATE NOT NULL,
    status ENUM('ACTIVE', 'COMPLETED', 'CANCELLED') DEFAULT 'ACTIVE',
    auto_save BOOLEAN DEFAULT FALSE,
    priority_level ENUM('LOW', 'MEDIUM', 'HIGH') DEFAULT 'MEDIUM',
    contribution_frequency ENUM('DAILY', 'WEEKLY', 'MONTHLY', 'CUSTOM') DEFAULT 'MONTHLY',
    is_deleted BOOLEAN DEFAULT FALSE,
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE audit_logs (
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    action VARCHAR(255) NOT NULL, -- e.g., 'CREATE_TRANSACTION', 'DELETE_ACCOUNT'
    entity VARCHAR(255) NOT NULL, -- Table affected
    entity_id BIGINT NOT NULL, -- ID of affected record
    old_value TEXT, -- JSON before update/delete
    new_value TEXT, -- JSON after update
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE reports (
    report_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    total_income DECIMAL(15,2) NOT NULL CHECK (total_income >= 0),
    total_expense DECIMAL(15,2) NOT NULL CHECK (total_expense >= 0),
    net_balance DECIMAL(15,2) NOT NULL,
    generated_by ENUM('SYSTEM', 'CUSTOM') DEFAULT 'SYSTEM',
    file_format ENUM('PDF', 'CSV', 'EXCEL') DEFAULT 'PDF',
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);


CREATE TABLE subscriptions (
    subscription_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,  -- Subscription name (Netflix, Gym, etc.)
    amount DECIMAL(15,2) NOT NULL CHECK (amount >= 0),  -- Monthly charge amount
    next_billing_date TIMESTAMP NOT NULL,  -- When the next payment is due
    payment_method_id BIGINT NOT NULL,  -- Links to an account (Bank, Card, etc.)
    auto_renew BOOLEAN DEFAULT TRUE,  -- Whether the subscription auto-renews
    status ENUM('ACTIVE', 'CANCELLED', 'PAUSED') DEFAULT 'ACTIVE',  -- Subscription status
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (payment_method_id) REFERENCES accounts(id) ON DELETE CASCADE
);

-- Index for faster user-based queries
CREATE INDEX idx_subscription_user ON subscriptions(user_id);
CREATE INDEX idx_subscription_next_billing ON subscriptions(next_billing_date);


CREATE TABLE investments (
    investment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    investment_type ENUM('STOCKS', 'CRYPTO', 'MUTUAL_FUNDS', 'REAL_ESTATE') NOT NULL,  -- Type of investment
    asset_name VARCHAR(100) NOT NULL,  -- Name of stock, crypto, or real estate asset
    amount_invested DECIMAL(15,2) NOT NULL CHECK (amount_invested >= 0),  -- Initial investment amount
    current_value DECIMAL(15,2) DEFAULT 0.00 CHECK (current_value >= 0),  -- Market value of investment
    purchase_date TIMESTAMP NOT NULL,  -- When the investment was made
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- Last valuation update
    is_deleted BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Index for fast lookups
CREATE INDEX idx_investment_user ON investments(user_id);
CREATE INDEX idx_investment_type ON investments(investment_type);



CREATE TABLE loans (
    loan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    lender_name VARCHAR(100) NOT NULL,  -- Bank, Credit Card Company, etc.
    amount_borrowed DECIMAL(15,2) NOT NULL CHECK (amount_borrowed >= 0),  -- Principal loan amount
    outstanding_balance DECIMAL(15,2) NOT NULL CHECK (outstanding_balance >= 0),  -- Remaining loan amount
    interest_rate DECIMAL(5,2) DEFAULT 0.00 CHECK (interest_rate >= 0),  -- Annual Interest Rate %
    interest_paid DECIMAL(15,2) DEFAULT 0.00 CHECK (interest_paid >= 0),  -- Total interest paid over loan lifetime
    monthly_payment DECIMAL(15,2) NOT NULL CHECK (monthly_payment >= 0),  -- Installment amount
    total_amount_paid DECIMAL(15,2) DEFAULT 0.00 CHECK (total_amount_paid >= 0),  -- Total amount repaid (principal + interest)
    number_years INT NOT NULL CHECK (number_years > 0),  -- Loan term in years
    due_date DATE NOT NULL,  -- Next repayment date
    status ENUM('ACTIVE', 'PAID_OFF', 'DEFAULTED') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Indexes for faster searches
CREATE INDEX idx_loans_user ON loans(user_id);
CREATE INDEX idx_loans_status ON loans(status);


CREATE TABLE loan_payments (
    payment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loan_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    payment_amount DECIMAL(15,2) NOT NULL CHECK (payment_amount >= 0),  -- Amount paid
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- Date of payment
    remaining_balance DECIMAL(15,2) NOT NULL CHECK (remaining_balance >= 0),  -- Balance after payment
    FOREIGN KEY (loan_id) REFERENCES loans(id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- Index for faster searches
CREATE INDEX idx_loan_payments_loan ON loan_payments(loan_id);
CREATE INDEX idx_loan_payments_user ON loan_payments(user_id);
