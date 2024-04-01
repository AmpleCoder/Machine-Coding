package SplitWise.Util;

public class UserIdGenerator {
    private static int userId = 1;

    public static int getUserId(){
        return userId++;
    }
}
