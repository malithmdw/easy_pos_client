
package appDataModels;

import serverDataModels.UserAccount;


/**
 *
 * @author MalithWanniarachchi
 */
public class UserAccountModel {
    private String userName;
    private int instituteId;
    private String userCode;
    private String email;
    private String phone;
    private String password;
    private String accountType;
    private String passwordHint;
    private int hintType;
    private String note;
    private int status;
    private int forcePasswordChange;

    // ✅ DTO → Model
    public UserAccountModel(UserAccount dto) {
        this.userName = dto.user_name;
        this.instituteId = dto.institute_id;
        this.userCode = dto.user_code;
        this.email = dto.email;
        this.phone = dto.phone;
        this.password = dto.password;
        this.accountType = dto.account_type;
        this.passwordHint = dto.password_hint;
        this.hintType = dto.hint_type;
        this.note = dto.note;
        this.status = dto.status;
        this.forcePasswordChange = dto.force_password_change;
    }

    // ✅ Model → DTO
    public UserAccount newUserAccountDTO() {
        UserAccount dto = new UserAccount();
        dto.user_name = this.getUserName();
        dto.institute_id = this.getInstituteId();
        dto.user_code = this.getUserCode();
        dto.email = this.getEmail();
        dto.phone = this.getPhone();
        dto.password = this.getPassword();
        dto.account_type = this.getAccountType();
        dto.password_hint = this.getPasswordHint();
        dto.hint_type = this.getHintType();
        dto.note = this.getNote();
        dto.status = this.getStatus();
        dto.force_password_change = this.getForcePasswordChange();
        return dto;
    }

    // ✅ Getters & Setters
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public int getInstituteId() { return instituteId; }
    public void setInstituteId(int instituteId) { this.instituteId = instituteId; }

    public String getUserCode() { return userCode; }
    public void setUserCode(String userCode) { this.userCode = userCode; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public String getPasswordHint() { return passwordHint; }
    public void setPasswordHint(String passwordHint) { this.passwordHint = passwordHint; }

    public int getHintType() { return hintType; }
    public void setHintType(int hintType) { this.hintType = hintType; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public int getForcePasswordChange() { return forcePasswordChange; }
    public void setForcePasswordChange(int forcePasswordChange) { this.forcePasswordChange = forcePasswordChange; }
}
