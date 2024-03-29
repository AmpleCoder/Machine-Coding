package DigitalWallet.Model;

import java.util.UUID;

public class User {
    private String userId;
    private String userName;

    public User(String userName){
        this.userId = UUID.randomUUID().toString();
        this.userName = userName;
    }

    //getters and setters
    public String getUserName(){
        return this.userName;
    }
}
