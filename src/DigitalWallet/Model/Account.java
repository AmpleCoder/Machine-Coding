package DigitalWallet.Model;

import DigitalWallet.Util.AccountNumberGenerator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Account {
    private int accountNumber;
    private User user;
    private List<Transaction> transactionList;
    private BigDecimal amount;

    public Account(User user, BigDecimal amount){
        this.accountNumber = AccountNumberGenerator.getAccountNumber();
        this.user = user;
        this.amount = amount;
        this.transactionList = new ArrayList<>();
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void addTransaction(Transaction transaction){
        transactionList.add(transaction);
    }

    public int getAccountNumber(){
        return accountNumber;
    }

    public List<Transaction> getTransactionList(){
        return transactionList;
    }
}
