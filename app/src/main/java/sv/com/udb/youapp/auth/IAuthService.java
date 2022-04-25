package sv.com.udb.youapp.auth;

import android.content.Intent;

import androidx.annotation.NonNull;

import net.openid.appauth.AuthorizationResponse;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.TokenResponse;

public interface IAuthService {


    void exchangeAccessToken(TokenRequest tokenRequest,
                                      AuthorizationService.TokenResponseCallback tokenResponse) throws Exception;

    Intent createRequestIntent();


}
