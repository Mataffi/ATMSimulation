package mataffi;

import java.time.LocalDateTime;

public class ATM {
    private BankDB bankDB;
    private Card card;
    private int fundLimit;
    private boolean exit;
    private final int ATTEMPTS_COUNT;

    public ATM() {
        bankDB = new BankDB();
        fundLimit = 1000000;
        ATTEMPTS_COUNT = 3;
    }

    public boolean isExit() {
        return exit;
    }

    public void setExit(boolean exit) {
        this.exit = exit;
    }

    public void validCardNumber(String cardNumber) throws InvalidCardExeption, CardBlockedExeption {
        if (!cardNumber.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$")) {
            throw new InvalidCardExeption("Invalid card number");
        }

        card = bankDB.getCard(cardNumber);

        if (card == null) {
            throw new InvalidCardExeption("Card not found");
        }

        if (card.getBlockedDate() != null) {
            if (card.getBlockedDate().plusDays(1).isBefore(LocalDateTime.now())) {
                card.resetAttempts();
                card.unblock();
                bankDB.saveCards();
            } else {
                throw new CardBlockedExeption("Card is blocked");
            }
        }

        System.out.println("Card number is correct");
    }

    public void validPin(int pin) throws CardBlockedExeption, InvalidPinExeption {
        if (!card.matchPin(pin)) {
            card.setAttempt();
            if (card.getAttempts() == ATTEMPTS_COUNT) {
                card.setBlocked();
                bankDB.saveCards();
                throw new CardBlockedExeption("Card is blocked");
            }
            bankDB.saveCards();
            throw new InvalidPinExeption("Invalid pin.\nYou have " + (ATTEMPTS_COUNT - card.getAttempts()) + " attempts left");
        }
        System.out.println("Pin is correct");
    }

    public void showBalance() {
        System.out.println("Balance: " + card.getBalance());
    }

    public void deposit(int amount) throws InvalidAmountExeption {
        if (amount > 1000000) {
            throw new InvalidAmountExeption("You can't deposit more than 1,000,000");
        }

        if (amount <= 0) {
            throw new InvalidAmountExeption("Invalid amount");
        }

        card.deposit(amount);
        bankDB.saveCards();
        System.out.println("Successful deposit ");
    }

    public void withdraw(int amount) throws InvalidAmountExeption {
        if (card.getBalance() < amount) {
            throw new InvalidAmountExeption("Insufficient funds");
        }

        if (amount <= 0) {
            throw new InvalidAmountExeption("Invalid amount");
        }

        if (amount > fundLimit) {
            throw new InvalidAmountExeption("Sorry, there are no funds left in the ATM");
        }

        fundLimit -= amount;
        card.withdraw(amount);
        bankDB.saveCards();
        System.out.println("Successful withdraw ");
    }
}
