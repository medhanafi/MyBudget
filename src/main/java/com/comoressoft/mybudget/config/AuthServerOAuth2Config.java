package com.comoressoft.mybudget.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
@EnableAuthorizationServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import(ServerSecurityConfig.class)
public class AuthServerOAuth2Config extends AuthorizationServerConfigurerAdapter {

    @Value("${security.app.client.id}")
    private String clientId;

    @Value("${security.app.client.password}")
    private String clientPassword;

    @Value("${security.access.token.validity}")
    private int accessTokenValidity;

    @Value("${security.refresh.token.validity}")
    private int refreshTokenValidity;

    // @Autowired
    // private TokenStore tokenStore;

    // @Autowired
    // private UserApprovalHandler userApprovalHandler;

    // @Autowired
    // @Qualifier("authenticationManagerBean")
    // private AuthenticationManager authenticationManager;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory().withClient(clientId).authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_TRUSTED_CLIENT").scopes("read", "write").secret(clientPassword)
                .accessTokenValiditySeconds(accessTokenValidity).refreshTokenValiditySeconds(refreshTokenValidity);

    }

    // @Override
    // public void configure(AuthorizationServerEndpointsConfigurer endpoints)
    // throws Exception {
    // endpoints.tokenStore(tokenStore).userApprovalHandler(userApprovalHandler).authenticationManager(authenticationManager);
    // }

    // @Override
    // public void configure(AuthorizationServerSecurityConfigurer oauthServer)
    // {
    // oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    // }

    // ================================================================================

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder oauthClientPasswordEncoder;

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    @Bean
    public OAuth2AccessDeniedHandler oauthAccessDeniedHandler() {
        return new OAuth2AccessDeniedHandler();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("permitAll()").passwordEncoder(oauthClientPasswordEncoder);
        
    }
    

    // @Override
    // public void configure(ClientDetailsServiceConfigurer clients) throws
    // Exception {
    // clients.jdbc(dataSource);
    // }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore()).authenticationManager(authenticationManager).userDetailsService(userDetailsService);
    }
}
