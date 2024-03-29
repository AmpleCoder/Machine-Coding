package DigitalWallet.Dao;

import DigitalWallet.Model.User;

import java.util.List;

public class UserDao {
    List<User> userList;

    //CRUD
    void addUser(String userName){
        User user = new User(userName);
        userList.add(user);
    }
}
