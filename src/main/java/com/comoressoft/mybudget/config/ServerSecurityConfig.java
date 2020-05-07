package com.comoressoft.mybudget.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@Order(SecurityProperties.BASIC_AUTH_ORDER)
@Import(Encoders.class)
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {

    // @Value("${user.auth.name}")
    // private String userName;
    // @Value("${user.auth.password}")
    // private String userPassword;
    // @Value("${user.auth.rol}")
    // private String userRol;
    //
    // @Autowired
    // private ClientDetailsService clientDetailsService;
    //
    // @Override
    // @Bean
    // public AuthenticationManager authenticationManagerBean() throws Exception
    // {
    // return super.authenticationManagerBean();
    // }
    //
    // @Autowired
    // public void configure(AuthenticationManagerBuilder auth) throws Exception
    // {
    //
    // auth.inMemoryAuthentication().withUser(userName).password("{noop}" +
    // userPassword).roles(userRol);
    // }
    //
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    // http.csrf().disable().anonymous().disable().authorizeRequests().antMatchers("/oauth/token").permitAll();
    // }
    //
    // @Bean
    // public TokenStore tokenStore() {
    // return new InMemoryTokenStore();
    // }
    //
    // @Bean
    // @Autowired
    // public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore
    // tokenStore) {
    // TokenStoreUserApprovalHandler handler = new
    // TokenStoreUserApprovalHandler();
    // handler.setTokenStore(tokenStore);
    // handler.setRequestFactory(new
    // DefaultOAuth2RequestFactory(clientDetailsService));
    // handler.setClientDetailsService(clientDetailsService);
    // return handler;
    // }
    //
    // @Bean
    // @Autowired
    // public ApprovalStore approvalStore(TokenStore tokenStore) throws
    // Exception {
    // TokenApprovalStore store = new TokenApprovalStore();
    // store.setTokenStore(tokenStore);
    // return store;
    // }

    // =================================================

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(userPasswordEncoder);

    }

    @Bean
    public DaoAuthenticationProvider getAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(userPasswordEncoder);
        return authenticationProvider;
    }

}
