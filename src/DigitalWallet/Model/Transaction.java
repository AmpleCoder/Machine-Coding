package DigitalWallet.Model;

import DigitalWallet.Util.TransactionIdGenerator;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private int transactionId;
    private User fromUser;
    private User toUser;
    private BigDecimal amount;
    private Date date;

    public Transaction(User fromUser, User toUser, BigDecimal amount){
        this.transactionId = TransactionIdGenerator.getTransactionId();
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.amount = amount;
        this.date = new Date();
    }

    public String toString(){
        String message = "Transaction {from " + fromUser.getUserName() + " , to " + toUser.getUserName() + " of amount = " + amount + " on date = " + date;
        return message;
    }

    public BigDecimal getAmount(){
        return amount;
    }
}
