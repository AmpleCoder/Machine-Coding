package SplitWise.Model;

import SplitWise.Util.UserIdGenerator;

import java.util.HashMap;
import java.util.Map;

public class User {
    private int userId;
    private String userName;
    private String phoneNumber;
    private String email;
    private Map<Integer, Double> lent;
    private Map<Integer, Double> owe;

    public User(String userName, String phoneNumber, String email){
        this.userId = UserIdGenerator.getUserId();
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.lent = new HashMap<Integer, Double>();
        this.owe = new HashMap<Integer, Double>();
    }

    public int getUserId(){
        return userId;
    }

    public Map<Integer, Double> getLent(){
        return lent;
    }

    public Map<Integer, Double> getOwe(){
        return owe;
    }
}
