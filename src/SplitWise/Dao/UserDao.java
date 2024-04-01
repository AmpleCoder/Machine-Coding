package SplitWise.Dao;
import SplitWise.Model.User;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private List<User> userList;

    public UserDao(){
        userList = new ArrayList<>();
    }

    //add user
    public void addUser(User user){
        userList.add(user);
    }

    //get user by id
    public User getUser(int userId){
        for(User user: userList){
            if(user.getUserId() == userId){
                return user;
            }
        }

        return null;
    }

    public List<Integer> getAllUserId(){
        List<Integer> userIdList = new ArrayList<>();

        for(User user: userList){
            userIdList.add(user.getUserId());
        }

        return userIdList;
    }

    public List<User> getAllUsers(){
        return userList;
    }
}
