package appDataModels;

import control.EasyPosLogger;
import java.util.ArrayList;
import java.util.List;
import serverResponseDataModel.LoginResponse;


/**
 *
 * @author MalithWanniarachchi
 */
public class LoginResponseModel {

    private String authToken;
    private UserAccountModel user;
    private List<Permission> permissions;

    // ✅ DTO → Model
    public LoginResponseModel(LoginResponse dto) {
        this.authToken = dto.auth_token;
        if (dto.user != null) {
            this.user = new UserAccountModel(dto.user);  // Convert nested DTO
        } else {
            this.user = null;
        }
        permissions = new ArrayList<>();
        for (String p : dto.permissions) {
            try {
                permissions.add(Permission.valueOf(p));
            } catch (Exception e) {
                EasyPosLogger.getInstance().error("Error", e);
            }            
        }
    }

    // ✅ Model → DTO
    public LoginResponse newLoginResponseDTO() {
        LoginResponse dto = new LoginResponse();
        dto.auth_token = this.getAuthToken();
        if (this.user != null) {
            dto.user = this.user.newUserAccountDTO();  // Convert nested Model
        } else {
            dto.user = null;
        }
        ArrayList<String> ps = new ArrayList<>();
        for (Permission permission : this.permissions) {
            ps.add(permission.name());
        }
        dto.permissions = ps;
        return dto;
    }

    // ✅ Getters & Setters
    public String getAuthToken() { return authToken; }
    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public UserAccountModel getUser() { return user; }
    public void setUser(UserAccountModel user) { this.user = user; }
    
    public List<Permission> getPermissions() { return permissions;  }
    public void setPermissions(List<Permission> permissions) {  this.permissions = permissions; }
}