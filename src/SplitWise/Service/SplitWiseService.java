package SplitWise.Service;

import SplitWise.Dao.UserDao;
import SplitWise.Model.User;

import java.util.*;

public class SplitWiseService {
    private UserDao userDao;

    public SplitWiseService(){
        userDao = new UserDao();
    }

    /* add expense
        EXPENSE <user-id-of-person-who-paid> <no-of-users> <space-separated-list-of-users> <EQUAL/EXACT/PERCENT> <space-separated-values-in-case-of-non-equal>
    */
    public void expense(String input){
        String[] tokens = input.split(" ");
        int payerId = Integer.parseInt(tokens[0]);
        double amount = Double.parseDouble(tokens[1]);
        int numberOfUser = Integer.parseInt(tokens[2]);
        List<Integer> owedUserList = new ArrayList<>();

        for(int i = 0; i < numberOfUser; i++){
            owedUserList.add(Integer.parseInt(tokens[3+i]));
        }

        String expenseType = tokens[3+numberOfUser];

        switch (expenseType){
            case "EQUAL":
                equalExpense(payerId, amount, numberOfUser, owedUserList);
                break;
            case "EXACT":
                exactExpense(payerId, amount, numberOfUser, owedUserList, tokens);
                break;
            case "PERCENT":
                percentExpense(payerId, amount, numberOfUser, owedUserList, tokens);
                break;
            default:
                System.out.println("Invalid expense type");
        }
    }

    //this method will handle equal expense
    private void equalExpense(int payerId, double amount, int numberOfUser, List<Integer> owedUserList) {
        //fetch the lent map of payerId
        User user = userDao.getUser(payerId);
        Map<Integer, Double> lent = user.getLent();
        amount /= numberOfUser;

        for(int currUserId: owedUserList){
            if(currUserId == payerId){
                continue;
            }

            if(lent.containsKey(currUserId)){
                lent.put(currUserId, lent.get(currUserId) + amount);
            }

            else{
                lent.put(currUserId, amount);
            }

            User currUser = userDao.getUser(currUserId);
            Map<Integer, Double> owe = currUser.getOwe();

            if(owe.containsKey(currUserId)){
                owe.put(payerId, lent.get(payerId) + amount);
            }

            else{
                owe.put(payerId, amount);
            }
        }
    }

    //this method will handle exact expense
    private void exactExpense(int payerId, double amount, int numberOfUser, List<Integer> owedUserList, String[] tokens) {
        //validate expense amount
        double[] exactShares = new double[owedUserList.size()];

        for(int i = 0; i < owedUserList.size(); i++){
            exactShares[i] = Double.parseDouble(tokens[4 + numberOfUser + i]);
        }

        User user = userDao.getUser(payerId);
        Map<Integer, Double> lent = user.getLent();

        for(int i = 0; i < owedUserList.size(); i++){
            lent.put(owedUserList.get(i), lent.get(owedUserList.get(i)) + exactShares[i]);
            User currUser = userDao.getUser(owedUserList.get(i));
            Map<Integer, Double> owe = currUser.getOwe();
            owe.put(payerId, owe.get(payerId) + exactShares[i]);
        }
    }

    //this method will handle percent expense
    private void percentExpense(int payerId, double amount, int numberOfUser, List<Integer> owedUserList, String[] tokens) {
        double[] percentShares = new double[owedUserList.size()];

        for(int i = 0; i < owedUserList.size(); i++){
            percentShares[i] = Double.parseDouble(tokens[4 + numberOfUser + i]);
        }

        User user = userDao.getUser(payerId);
        Map<Integer, Double> lent = user.getLent();

        for(int i = 0; i < owedUserList.size(); i++){
            if(payerId == owedUserList.get(i)){
                continue;
            }

            if(lent.containsKey(owedUserList.get(i))){
                lent.put(owedUserList.get(i), lent.get(owedUserList.get(i)) + (amount * percentShares[i])/100);
            }

            else{
                lent.put(owedUserList.get(i), (amount * percentShares[i])/100);
            }

            User currUser = userDao.getUser(owedUserList.get(i));
            Map<Integer, Double> owe = currUser.getOwe();

            if(owe.containsKey(payerId)){
                owe.put(payerId, owe.get(payerId) + (amount * percentShares[i])/100);
            }

            else{
                owe.put(payerId, (amount * percentShares[i])/100);
            }
        }
    }

    /* Show balances for a single user: SHOW {user-id} */
    public void showBalanceForGivenUserId(int userId){
        //check if user exist or not

        /*
            get the current user
            get all the users in the system

        */
        boolean noBalance = true;
        User user = userDao.getUser(userId);
        List<User> userList = userDao.getAllUsers();
        Double netAmountOwed;

        for(User currUser: userList){
            if(currUser.equals(user)){
                continue;
            }

            Double amountLented = user.getLent().get(currUser.getUserId());
            Double amountOwed = user.getOwe().get(currUser.getUserId());

            if(amountOwed == null && amountLented == null){
                continue;
            }

            else if(amountOwed == null){
                System.out.println("User " + currUser.getUserId()  + " owes User " + user.getUserId() + ": " + amountLented);
            }

            else if(amountLented == null){
                System.out.println("User " + user.getUserId()  + " owes User " + currUser.getUserId() + ": " + amountOwed);
            }

            else{
                if(Math.round((amountOwed * 100.0)/100.0) == Math.round((amountLented * 100.0)/100.0)){
                    continue;
                }

                if(amountOwed > amountLented){
                    netAmountOwed = amountOwed - amountLented;
                    System.out.println("User " + user.getUserId()  + " owes User " + currUser.getUserId() + ": " + netAmountOwed);
                }

                else if(amountLented > amountOwed){
                    netAmountOwed = amountLented - amountOwed;
                    System.out.println("User " + currUser.getUserId()  + " owes User " + user.getUserId() + ": " + netAmountOwed);
                }
            }

            noBalance = false;
        }

        if(noBalance){
            System.out.println("No balances");
        }
    }

    /* Show balances for all: SHOW */
    public void show(){
        List<Integer> userId = userDao.getAllUserId();

        boolean noBalances = true;

        for(int i = 0; i < userId.size(); i++){
            int currentUserId = userId.get(i);

            for(int j = i + 1; j < userId.size(); j++){
                int owedUserId = userId.get(j);
                Double amountLentByCurrentUserToOwedUser = userDao.getUser(currentUserId).getLent().get(owedUserId);
                Double amountLentByOwedUserToCurrentUser = userDao.getUser(owedUserId).getLent().get(currentUserId);
                double netAmountOwed;

                if(amountLentByCurrentUserToOwedUser == null && amountLentByOwedUserToCurrentUser == null){
                    continue;
                }

                if(amountLentByCurrentUserToOwedUser == null) {
                    amountLentByCurrentUserToOwedUser = 0D;
                }

                if(amountLentByOwedUserToCurrentUser == null) {
                    amountLentByOwedUserToCurrentUser = 0D;
                }

                if(amountLentByCurrentUserToOwedUser > amountLentByOwedUserToCurrentUser){
                    netAmountOwed = amountLentByCurrentUserToOwedUser - amountLentByOwedUserToCurrentUser;
                    System.out.println("User " + owedUserId + " owes User " + currentUserId + ": " + netAmountOwed);
                    noBalances = false;
                }

                else{
                    netAmountOwed = amountLentByOwedUserToCurrentUser - amountLentByCurrentUserToOwedUser;
                    System.out.println("User " + currentUserId + " owes User " + owedUserId + ": " + netAmountOwed);
                    noBalances = false;
                }
            }
        }

        if(noBalances){
            System.out.println("No balances");
        }
    }

    public void addUsers(User user){
        userDao.addUser(user);
    }
}
