package DigitalWallet.Service;

import DigitalWallet.Dao.AccountDao;
import DigitalWallet.Dao.TransactionDao;
import DigitalWallet.Dao.UserDao;
import DigitalWallet.Exceptions.AccountNumberNotFound;
import DigitalWallet.Exceptions.InsufficientAmountException;
import DigitalWallet.Exceptions.alreadyExistsException;
import DigitalWallet.Exceptions.userNotFound;
import DigitalWallet.Model.Account;
import DigitalWallet.Model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public class DigitalPaymentService {
    private AccountDao accountDao;

    public DigitalPaymentService(){
        accountDao = new AccountDao();
    }

    //create wallet
    public void createWallet(String userName, BigDecimal amount) throws alreadyExistsException {
        //if user alrerady exists then through alreadyExist aexception
        if(accountDao.userExists(userName)){
            throw new alreadyExistsException("User Already Exist Try Creating A New User");
        }

        accountDao.createAccount(userName,amount);
    }

    //transfer amount or initiate transaction
    public void initiateTransaction(String userNameFrom, String userNameTo, BigDecimal amount) throws userNotFound, InsufficientAmountException {

        //if user1 not present throw exception
        if(!accountDao.userExists(userNameFrom)){
            throw new userNotFound("User with UserName: " + userNameFrom + " not found");
        }

        //if user2 not present throw exception
        if(!accountDao.userExists(userNameTo)){
            throw new userNotFound("User with UserName: " + userNameTo + " not found");
        }

        //get account for userName1 and userName2
        Account accountFrom = accountDao.getUser(userNameFrom);
        Account accountTo = accountDao.getUser(userNameTo);

        BigDecimal amountInAccountFrom = accountFrom.getAmount();
        BigDecimal amountInAccountTo = accountTo.getAmount();

        //if accountFrom is having among < amount throw insufficient amount present
        if(amountInAccountFrom.compareTo(amount) < 0){
            throw new InsufficientAmountException("Insufficient amount to transferred");
        }

        //initiate the transaction
            //debit amount money from accountFrom
            //credit amount money to accountTo
        accountFrom.setAmount(amountInAccountFrom.subtract(amount));
        accountTo.setAmount(amountInAccountTo.add(amount));

        //create a transaction and add transaction in transaction list
        Transaction transaction = new Transaction(accountFrom.getUser(), accountTo.getUser(), amount);
        accountDao.addTransaction(accountFrom, transaction);
        accountDao.addTransaction(accountTo, transaction);
    }

    //account statment for a given user
    public void showAccountStatment(int accountNumber) throws AccountNumberNotFound {
        //check if account number exist or not
        Account account = accountDao.getAccountFromAccountNumber(accountNumber);

        if(account == null){
            throw new AccountNumberNotFound("Given Account Number doesn't exist");
        }

        System.out.println("Current balance for accountNumber: " + accountNumber + "is = " + account.getAmount());
        System.out.println("List of Transaction made by accountNumber: "+ accountNumber);
        System.out.println(account.getTransactionList());
    }

    //overview this will display all the current user in the system and their current balance
    public void overView(){
        //fetch all the accounts in the system and show the current balance against the account number
        List<Account> accountList = accountDao.getAccountList();

        for(Account account: accountList){
            int accountNumber = account.getAccountNumber();
            BigDecimal amount = account.getAmount();
            System.out.println("Account Number = " + accountNumber + " Holds a Balance of = " + amount);
        }
    }
}
