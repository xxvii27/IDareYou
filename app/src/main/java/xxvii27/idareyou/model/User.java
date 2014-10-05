package xxvii27.idareyou.model;

/**
 * Created by xxvii27 on 10/4/14.
 */


import com.parse.ParseUser;


public class User {

    public static String getUsername() {
        return ParseUser.getCurrentUser().getUsername();
    }

    public static void setUsername(String username) {
        ParseUser.getCurrentUser().setUsername(username);
    }

    public static String getEmail() {
        return ParseUser.getCurrentUser().getEmail();
    }

    public static void setEmail(String email) {
        ParseUser.getCurrentUser().setEmail(email);
    }

    public static String getName() {
        return ParseUser.getCurrentUser().getString("Name");
    }

    public static ParseUser getID() {
        return ParseUser.getCurrentUser();
    }
}
