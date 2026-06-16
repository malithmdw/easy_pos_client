
package control;

import appDataModels.UserAccountModel;

/**
 *
 * @author malit
 */
public interface LoginEvent {
    public abstract void onLoginSuccess(UserAccountModel user);
    public abstract void onForgotPassword(String UserName);
    
}
