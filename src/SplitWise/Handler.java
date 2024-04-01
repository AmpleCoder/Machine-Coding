package SplitWise;

import SplitWise.Model.User;
import SplitWise.Service.SplitWiseService;

public class Handler {
    public static void main(String[] args) {
        SplitWiseService splitWiseService = new SplitWiseService();
        User user1 = new User("user1", "12345", "user1@gmail.com");
        User user2 = new User("user2", "23456", "user12@gmail.com");
        User user3 = new User("user3", "34567", "user3@gmail.com");
        User user4 = new User("user4", "45678", "user4@gmail.com");

        splitWiseService.addUsers(user1);
        splitWiseService.addUsers(user2);
        splitWiseService.addUsers(user3);
        splitWiseService.addUsers(user4);

        splitWiseService.show();
        splitWiseService.showBalanceForGivenUserId(1);

        String expenseTypeQuery1 = "1 1000 4 1 2 3 4 EQUAL";
        splitWiseService.expense(expenseTypeQuery1);

        splitWiseService.showBalanceForGivenUserId(4);
        splitWiseService.showBalanceForGivenUserId(1);

        String expenseTypeQuery2 = "1 1250 2 2 3 EXACT 370 880";
        splitWiseService.expense(expenseTypeQuery2);
        splitWiseService.show();

        String expenseTypeQuery3 = "4 1200 4 1 2 3 4 PERCENT 40 20 20 20";
        splitWiseService.expense(expenseTypeQuery3);
        splitWiseService.showBalanceForGivenUserId(1);
        splitWiseService.show();
    }
}
