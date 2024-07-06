package mataffi;


import java.util.InputMismatchException;
import java.util.Scanner;

public class App
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        while (true) {
            boolean successfulCardNumber;
            successfulCardNumber = processCardNumber(scanner, atm);

            while (!atm.isExit() && successfulCardNumber) {
                boolean successfulPin;
                successfulPin = processPin(scanner, atm);

                while (!atm.isExit() && successfulPin) {
                    processTransaction(scanner, atm);
                }
            }
        }
    }

    private static boolean processCardNumber(Scanner scanner, ATM atm) {
        boolean successfulCardNumber = false;
        try {
            System.out.println("---------------------");
            System.out.println("Enter card number:");
            atm.validCardNumber(scanner.next());
            atm.setExit(false);
            successfulCardNumber = true;
        } catch (CardBlockedExeption | InvalidCardExeption ex) {
            System.out.println(ex.getMessage());
        }
        return successfulCardNumber;
    }

    private static boolean processPin(Scanner scanner, ATM atm) {
        boolean successfulPin = false;
        try {
            int transaction;
            System.out.println("---------------------");
            System.out.println("0. Exit");
            System.out.println("1. Pin");
            System.out.println("Enter the transaction number:");
            transaction = scanner.nextInt();
            switch (transaction) {
                case 0:
                    atm.setExit(true);
                    break;
                case 1:
                    System.out.println("Enter pin:");
                    atm.validPin(scanner.nextInt());
                    successfulPin = true;
                    break;
                default:
                    System.out.println("You have selected the wrong transaction number");
                    break;
            }

        } catch (CardBlockedExeption ex) {
            atm.setExit(true);
            System.out.println(ex.getMessage());
        } catch (InvalidPinExeption ex) {
            System.out.println(ex.getMessage());
        } catch (InputMismatchException ex) {
            scanner.next();
            System.out.println("Invalid data format");
        }
        return successfulPin;
    }

    private static void processTransaction(Scanner scanner, ATM atm) {
        try {
            int transaction;
            System.out.println("---------------------");
            System.out.println("0. Exit");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Balance");
            System.out.println("Enter the transaction number:");
            transaction = scanner.nextInt();
            switch (transaction) {
                case 0:
                    atm.setExit(true);
                    break;
                case 1:
                    int depositAmount;
                    System.out.println("Enter the deposit amount:");
                    depositAmount = scanner.nextInt();
                    atm.deposit(depositAmount);
                    break;
                case 2:
                    int withdrawAmount;
                    System.out.println("Enter the withdraw amount:");
                    withdrawAmount = scanner.nextInt();
                    atm.withdraw(withdrawAmount);
                    break;
                case 3:
                    atm.showBalance();
                    break;
                default:
                    System.out.println("You have selected the wrong transaction number");
                    break;
            }
        } catch (InvalidAmountExeption ex) {
            System.out.println(ex.getMessage());
        } catch (InputMismatchException ex) {
            scanner.next();
            System.out.println("Invalid data format");
        }
    }
}

