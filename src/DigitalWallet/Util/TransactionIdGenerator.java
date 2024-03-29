package DigitalWallet.Util;

public class TransactionIdGenerator {
    private static int transactionId = 1;

    public static int getTransactionId(){
        return transactionId++;
    }
}
