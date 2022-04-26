package sv.com.udb.youapp.auth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.NonNull;

import net.openid.appauth.AppAuthConfiguration;
import net.openid.appauth.AuthorizationRequest;
import net.openid.appauth.AuthorizationService;
import net.openid.appauth.AuthorizationServiceConfiguration;
import net.openid.appauth.ClientAuthentication;
import net.openid.appauth.ClientSecretPost;
import net.openid.appauth.ResponseTypeValues;
import net.openid.appauth.TokenRequest;
import net.openid.appauth.connectivity.ConnectionBuilder;

public final class OAuth2AuthorizationService implements IAuthService {

    private final AuthorizationService authorizationService;
    private final AuthorizationRequest authorizationRequest;
    private final ClientAuthentication clientAuthentication;

    public OAuth2AuthorizationService(AuthorizationService authorizationService,
                                      AuthorizationRequest authorizationRequest,
                                      ClientAuthentication clientAuthentication){
        this.authorizationService = authorizationService;
        this.authorizationRequest = authorizationRequest;
        this.clientAuthentication = clientAuthentication;
    }

    @Override
    public void exchangeAccessToken(@NonNull TokenRequest tokenRequest,
                                             AuthorizationService.TokenResponseCallback tokenResponse) {
        authorizationService.performTokenRequest(tokenRequest,clientAuthentication,tokenResponse);
    }

    @Override
    public Intent createRequestIntent() {
        return authorizationService.getAuthorizationRequestIntent(authorizationRequest);
    }

    public static final class Builder {
        private static final String CLIENT_ID = "youapp";
        private static final String REDIRECT_URI = "youapp://oauth";
        private static final String CLIENT_SECRET = "9d[?hr%[Y>w~nV3_";
        private static final String[] scopes = new String[]{"openid","profile","email"};
        private static final String TOKEN_URI = "http://192.168.101.17:8083/oauth2/token";
        private static final String AUTHORIZE_URI = "http://192.168.101.17:8083/oauth2/authorize";
        private ClientAuthentication clientAuthentication = new ClientSecretPost(CLIENT_SECRET);
        private ConnectionBuilder connectionBuilder = HttpConnectionBuilder.INSTANCE;
        private AuthorizationServiceConfiguration authServiceConfiguration;
        private AppAuthConfiguration appAuthConfiguration;
        private AuthorizationService authorizationService;
        private AuthorizationRequest authorizationRequest;
        private Context context;

        public Builder(Context applicationContext) {
            this.context = applicationContext;
        }

        public Builder setContext(@NonNull Context context){
            this.context = context;
            return this;
        }

        public Builder setConnectionBuilder(ConnectionBuilder connectionBuilder){
            this.connectionBuilder = connectionBuilder;
            return this;
        }


        public Builder setAuthConfiguration(@NonNull AppAuthConfiguration
                                                    appAuthConfiguration){
            this.appAuthConfiguration = appAuthConfiguration;
            return this;
        }

        public Builder setAuthorizationService(@NonNull AuthorizationService
                                                       authorizationService){
            this.authorizationService = authorizationService;
            return this;
        }

        public Builder setClientAuthentication(ClientAuthentication clientAuthentication){
            this.clientAuthentication = clientAuthentication;
            return this;
        }

        public Builder setAuthorizationConfiguration(AuthorizationServiceConfiguration
                                                              serviceConfiguration){
            this.authServiceConfiguration = serviceConfiguration;
            return this;
        }

        public Builder setAuthorizationRequest(AuthorizationRequest authRequest){
            this.authorizationRequest = authRequest;
            return this;
        }


        public OAuth2AuthorizationService build(){
            if(null == appAuthConfiguration){
                appAuthConfiguration = new AppAuthConfiguration.Builder()
                        .setSkipIssuerHttpsCheck(true)
                        .setConnectionBuilder(connectionBuilder).build();
            }
            if(null == authorizationService){
                authorizationService = new AuthorizationService(context,appAuthConfiguration);
            }
            if(null == authServiceConfiguration){
                authServiceConfiguration = new AuthorizationServiceConfiguration(
                        Uri.parse(AUTHORIZE_URI),
                        Uri.parse(TOKEN_URI)
                );
            }
            if(null == authorizationRequest){
                 authorizationRequest =  new AuthorizationRequest.Builder(
                        authServiceConfiguration,
                        CLIENT_ID,
                        ResponseTypeValues.CODE,
                        Uri.parse(REDIRECT_URI))
                        .setScopes(scopes).build();
            }
            return new OAuth2AuthorizationService(
                    authorizationService,
                    authorizationRequest,
                    clientAuthentication);
        }
    }










}
