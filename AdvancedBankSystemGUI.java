import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Random;
import javax.swing.*;

class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private String fatherName;
    private String aadharNumber;
    private String panCardNumber;
    protected double balance;

    public BankAccount(String accountNumber, String accountHolder, String fatherName, String aadharNumber, String panCardNumber, double balance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.fatherName = fatherName;
        this.aadharNumber = aadharNumber;
        this.panCardNumber = panCardNumber;
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposit: Account Number: " + accountNumber + ", Amount: " + amount + ", New Balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Withdraw: Account Number: " + accountNumber + ", Amount: " + amount + ", New Balance: " + balance);
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds for withdrawal.");
            System.out.println("Withdraw: Account Number: " + accountNumber + ", Amount: " + amount + " - Insufficient funds.");
        }
    }

    @Override
    public String toString() {
        return String.format("Account Number: %s\nAccount Holder: %s\nFather's Name: %s\nAadhar Number: %s\nPAN Card Number: %s\nBalance: %.2f",
                accountNumber, accountHolder, fatherName, aadharNumber, panCardNumber, balance);
    }
}

class SavingsAccount extends BankAccount {
    public SavingsAccount(String accountNumber, String accountHolder, String fatherName, String aadharNumber, String panCardNumber) {
        super(accountNumber, accountHolder, fatherName, aadharNumber, panCardNumber, 0.0);
    }
}

class Bank {
    private HashMap<String, BankAccount> accounts = new HashMap<>();

    public void addAccount(BankAccount account) {
        accounts.put(account.getAccountNumber(), account);
        System.out.println("Account Created: " + account);
    }

    public BankAccount findAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}

public class AdvancedBankSystemGUI {
    private static Bank bank = new Bank();
    private static Random random = new Random();
    private static final String ACCOUNT_PREFIX = "94086522";

    public static void main(String[] args) {
        JFrame frame = createFrame("Advanced Bank System", 500, 400);
        createButton(frame, "Create Savings Account", e -> createSavingsAccount());
        createButton(frame, "Perform Transaction", e -> performTransaction());
        createButton(frame, "Exit", e -> System.exit(0));
        frame.setVisible(true);
    }

    private static JFrame createFrame(String title, int width, int height) {
        JFrame frame = new JFrame("Advance Banking Sysytem");
        frame.setSize(1080, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        return frame;
    }

    private static JButton createButton(JFrame frame, String title, ActionListener action) {
        JButton button = new JButton(title);
        button.addActionListener(action);
        frame.add(button);
        return button;
    }

    private static void createSavingsAccount() {
        String accountNumber = generateAccountNumber();
        String accountHolderName = JOptionPane.showInputDialog("Enter account holder name:");
        String fatherName = JOptionPane.showInputDialog("Enter father's name:");
        String aadharNumber = JOptionPane.showInputDialog("Enter Aadhar card number:");
        String panCardNumber = JOptionPane.showInputDialog("Enter PAN card number:");
        int age = Integer.parseInt(JOptionPane.showInputDialog("Enter age:"));

        if (age < 18) {
            JOptionPane.showMessageDialog(null, "Account cannot be created. The customer is a minor.");
            System.out.println("Account creation failed: Customer is a minor.");
            return;
        }

        bank.addAccount(new SavingsAccount(accountNumber, accountHolderName, fatherName, aadharNumber, panCardNumber));
        JOptionPane.showMessageDialog(null, "Savings Account created with initial balance of $0.00.");
    }

    private static String generateAccountNumber() {
        long suffix = (long) (1000000L + random.nextDouble() * 9000000L);
        return ACCOUNT_PREFIX + suffix;
    }

    private static void performTransaction() {
        String accountNumber = JOptionPane.showInputDialog("Enter account number:");
        BankAccount transactionAccount = bank.findAccount(accountNumber);
        if (transactionAccount == null) {
            JOptionPane.showMessageDialog(null, "Account not found.");
            System.out.println("Transaction: Account not found.");
            return;
        }

        String[] options = {"Deposit", "Withdraw", "Check Balance"};
        int transactionChoice = JOptionPane.showOptionDialog(null, "Select transaction type:", "Transaction",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (transactionChoice == 0) {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to deposit:"));
            transactionAccount.deposit(amount);
            JOptionPane.showMessageDialog(null, "Deposit successful.");
        } else if (transactionChoice == 1) {
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter amount to withdraw:"));
            transactionAccount.withdraw(amount);
            JOptionPane.showMessageDialog(null, "Withdrawal successful.");
        } else if (transactionChoice == 2) {
            JOptionPane.showMessageDialog(null, "Balance: " + transactionAccount.getBalance());
            System.out.println("Check Balance: Account Number: " + transactionAccount.getAccountNumber() + ", Balance: " + transactionAccount.getBalance());
        } else {
            JOptionPane.showMessageDialog(null, "Invalid transaction choice.");
            System.out.println("Invalid transaction choice.");
        }
    }
}