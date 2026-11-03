package serverResponseDataModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Maps the EasyReload login data payload:
 * {"accessToken":"...", "accessTokenExpiresIn":900, "refreshToken":"...",
 *  "refreshTokenExpiresIn":604800, "tokenType":"Bearer", "username":"..."}
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillPaymentLoginResponse {
    public String accessToken;
    public int accessTokenExpiresIn;
    public String refreshToken;
    public int refreshTokenExpiresIn;
    public String tokenType;
    public String username;
}
