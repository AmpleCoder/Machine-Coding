package DigitalWallet.Dao;

import DigitalWallet.Model.Account;
import DigitalWallet.Model.Transaction;
import DigitalWallet.Model.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {
    private List<Account> accountList;

    public AccountDao(){
        accountList = new ArrayList<>();
    }
    //CRUD
    public void createAccount(String userName, BigDecimal amount){
          User user = new User(userName);
          Account account = new Account(user,amount);
          accountList.add(account);
    }

    public boolean userExists(String userName) {
        Boolean isUserPresent = false;

        for(Account account:accountList){
            User user = account.getUser();
            if(user.getUserName().equals(userName)){
                isUserPresent = true;
                break;
            }
        }

        return isUserPresent;
    }

    public Account getAccountFromAccountNumber(int accountNumber) {

        for(Account currAccount:accountList){
            int currAccountNumber = currAccount.getAccountNumber();
            if(currAccountNumber == accountNumber){
                return currAccount;
            }
        }

        return null;
    }

    public Account getUser(String userName) {
        for(Account account:accountList){
            User user = account.getUser();
            if(user.getUserName().equals(userName)){
                return account;
            }
        }

        return null;
    }

    public void addTransaction(Account account, Transaction transaction){
        for(Account acc:accountList){
            if(acc.equals(account)){
                acc.addTransaction(transaction);
            }
        }
    }

    public List<Account> getAccountList(){
        return accountList;
    }
}
