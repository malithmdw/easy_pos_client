package serverResponseDataModel;

import java.util.ArrayList;
import serverDataModels.UserAccount;

/**
 *
 * @author MalithWanniarachchi
 */
public class LoginResponse {
    public String auth_token;
    public UserAccount user;
    public ArrayList<String> permissions;
}
