package org.razu.config;

import org.razu.auth.CorsFilter;
import org.razu.auth.LogoutSuccess;
import org.razu.auth.RestAuthenticationEntryPoint;
import org.razu.auth.TokenAuthenticationFilter;
import org.razu.auth.TokenHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${app.security.jwt.cookie}")
    private String AUTH_TOKEN_COOKIE;

    @Value(value = "${app.security.ignore:}")
    private String ignoreUris;

    @Value(value = "${app.security.csrf.ignore:}")
    private String ignoreCsrfUris;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    private LogoutSuccess logoutSuccess;

    @Autowired
    CorsFilter corsFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
                .authorizeRequests()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/webjars/**",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()
                // Allow pre-flight checks
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                //                .antMatchers("/api/auth/login").permitAll()
                //                .antMatchers(HttpMethod.POST, "/api/users").permitAll()
                //                .antMatchers(HttpMethod.PUT, "/api/users/{email}/forgot-password").permitAll()
                //                .antMatchers(HttpMethod.PUT, "/api/users/reset-forgot-password").permitAll()
                //                .antMatchers(HttpMethod.PUT, "/api/users/activate").permitAll()
                //                .antMatchers(HttpMethod.POST, "/user/rg/web/registration").permitAll()
                //                .antMatchers(HttpMethod.POST, "/user/log/web/login").permitAll()
                //                .antMatchers(HttpMethod.POST, "/institute/type/create").permitAll()
                .antMatchers(HttpMethod.POST, "/registration/web/user").permitAll()
                .antMatchers(HttpMethod.POST, "/login/web/user").permitAll()
                .antMatchers(HttpMethod.GET, "/user/all/user").permitAll()
                .antMatchers(HttpMethod.GET, "/user/find/by/userName").permitAll()
                .antMatchers(HttpMethod.GET, "/user/find/by/uid").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/update").permitAll()
                .antMatchers(HttpMethod.DELETE, "/user/delete").permitAll()
                //                .antMatchers(HttpMethod.GET, "/user/find/web/by/phone/{phone}").permitAll()
                //                .antMatchers(HttpMethod.GET, "/institute/get/all/institution").permitAll()
                //                .antMatchers(HttpMethod.PUT, "/api/users/{email}/profile-activation-email-resend").permitAll()
                //                .antMatchers(HttpMethod.GET, "/api/roles/list").permitAll()
                .anyRequest().authenticated().and()
                .addFilterBefore(new TokenAuthenticationFilter(tokenHelper, userDetailsService), BasicAuthenticationFilter.class)
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/api/auth/logout"))
                .logoutSuccessHandler(logoutSuccess)
                .deleteCookies(AUTH_TOKEN_COOKIE)
                .and()
                .addFilterBefore(corsFilter, ChannelProcessingFilter.class);

        if (ignoreCsrfUris != null && !ignoreCsrfUris.trim().isEmpty()) {
            http.csrf().ignoringAntMatchers(ignoreCsrfUris.split(",\\s*"));
        }

        // disable csrf for the login request
        http.csrf()
                /*--------------start----------------*/
                .ignoringAntMatchers("/api/auth/login")
                .ignoringAntMatchers("/api/users")
                .ignoringAntMatchers("/api/users/{email}/forgot-password")
                .ignoringAntMatchers("/api/users/reset-forgot-password")
                .ignoringAntMatchers("/api/users/activate")
                .ignoringAntMatchers("/api/users/{email}/profile-activation-email-resend")
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // TokenAuthenticationFilter will ignore the below paths
        if (ignoreUris != null && !ignoreUris.trim().isEmpty()) {
            web.ignoring().antMatchers(ignoreUris.split(",\\s*"));
        }
        web.ignoring().antMatchers(HttpMethod.POST, "/auth/login");
        web.ignoring().antMatchers(
                HttpMethod.GET,
                "/",
                "/webjars/**",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js"
        );
    }
}
