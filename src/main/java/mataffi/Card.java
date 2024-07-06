package mataffi;

import java.time.LocalDateTime;

public class Card {
    private String cardNumber;
    private int pin;
    private double balance;
    private int attempts;
    private LocalDateTime blockedDate;

    public Card(String number, int pin, double balance) {
        this.cardNumber = number;
        this.pin = pin;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public int getAttempts() {
        return attempts;
    }

    public boolean matchPin(int pin) {
        return this.pin == pin;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void resetAttempts() {
        attempts = 0;
    }

    public void setAttempt() {
        attempts++;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public void setBlocked() {
        blockedDate = LocalDateTime.now();
    }

    public void setBlocked(LocalDateTime blockedDate) {
        this.blockedDate = blockedDate;
    }

    public LocalDateTime getBlockedDate() {
        return blockedDate;
    }

    public void unblock() {
        blockedDate = null;
    }

    @Override
    public String toString() {
        return cardNumber + " " + pin + " " + balance + " " + attempts + " " + blockedDate;
    }
}
