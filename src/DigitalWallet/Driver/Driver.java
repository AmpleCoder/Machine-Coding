package DigitalWallet.Driver;

import DigitalWallet.Exceptions.AccountNumberNotFound;
import DigitalWallet.Exceptions.InsufficientAmountException;
import DigitalWallet.Exceptions.alreadyExistsException;
import DigitalWallet.Exceptions.userNotFound;
import DigitalWallet.Service.DigitalPaymentService;

import java.math.BigDecimal;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) throws alreadyExistsException, userNotFound, InsufficientAmountException, AccountNumberNotFound {
        DigitalPaymentService digitalPaymentService = new DigitalPaymentService();
        Scanner sc = new Scanner(System.in);
        Boolean flag = true;

        while(flag){
            System.out.println("\nOPTIONS:");
            System.out.println("1. Create wallet");
            System.out.println("2. Transfer Amount");
            System.out.println("3. Account Statement");
            System.out.println("4. Overview");
            System.out.println("5. Exit");

            switch(sc.nextInt()){
                case 1:
                    System.out.println("Enter User Name");
                    String userName = sc.next();
                    System.out.println("Enter Amount");
                    BigDecimal amount = sc.nextBigDecimal();
                    digitalPaymentService.createWallet(userName,amount);
                    break;

                case 2:
                    System.out.println("Enter User Name Who Will Send Money");
                    String userNameFrom = sc.next();
                    System.out.println("Enter User Name Who Will Receive Money");
                    String userNameTo = sc.next();
                    System.out.println("Enter The Amount Which You Want To Transfer");
                    BigDecimal amountToTransfer = sc.nextBigDecimal();
                    digitalPaymentService.initiateTransaction(userNameFrom, userNameTo, amountToTransfer);
                    break;

                case 3:
                    System.out.println("Enter Account Number to view the statment");
                    int accountNumber = sc.nextInt();
                    digitalPaymentService.showAccountStatment(accountNumber);
                    break;

                case 4:
                    digitalPaymentService.overView();
                    break;

                case 5:
                    flag = false;
                    break;

                default:
                    System.out.println("please select the valid option");
                    break;
            }
        }
    }
}
