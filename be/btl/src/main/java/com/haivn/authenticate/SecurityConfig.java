package com.haivn.authenticate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/swagger-ui/**", "/v3/api-docs/**", "/css/**", "/js/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        System.out.println("");

        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())
                ).cors().and().csrf().disable().authorizeRequests()
                .antMatchers(PUBLIC_LIST)
                .permitAll()
                .antMatchers(ADMIN_LIST_PRIVATE)
                .hasAuthority("ADMIN1")
                .antMatchers(USER_LIST_PRIVATE)
                .hasAuthority("SV1")
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().clearAuthentication(true).invalidateHttpSession(true)
                .and()
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("POST", "GET", "PUT", "DELETE", "HEAD", "OPTIONS"));
        configuration.setAllowCredentials(false);
        configuration.setAllowedHeaders(Arrays.asList("Authorization","Cache-Control","Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    String[] PUBLIC_LIST = {
            "/api/login",
            "/api/student/login",
            "/api/**",
            "/api-student/**",
            "/api/upload/**"
//            "/api/**",
//            "/api/login","/api/register/process_register","/api/register/verify",
//            "/api/function/*","/api/function/put/*", "/api/function/get/*","/api/function/del/*",
//            "/api/document/post","/api/document/get/*","/api/document/put/*","/api/document/del","/api/document/del/*","/api/document/get/add-down/*",
//            "/api/document-review/post","/api/document-review/get/*","/api/document-review/put/*","/api/document-review/del/*",
//            "/api/topic-review/post","/api/topic-review/get/*","/api/topic-review/put/*","/api/topic-review/del/*",
//            "/api/topic/post","/api/topic/get/*","/api/topic/put/*","/api/topic/del/*",
//            "/api/department/post","/api/department/get/*","/api/department/put/*","/api/department/del/*","/api/department/get/detail/*",
//            "/api/role/post","/api/role/get/*","/api/role/put/*","/api/role/del/*",
//            "/api/role-func/post","/api/role-func/get/*","/api/role-func/put/*","/api/role-func/del/*",
//            "/api/role-func-per/*","/api/role-func-per/put/*", "/api/role-func-per/get/*","/api/role-func-per/del/*",
//            "/api/hospital/post", "/api/hospital/get/*", "/api/hospital/del", "/api/hospital/del/*", "/api/hospital/put/*",
//            "/api/tbl-user/post", "/api/tbl-user/get/*", "/api/tbl-user/del/*", "/api/tbl-user/put/*",
//            "/api/loai-nkbv/post", "/api/loai-nkbv/get/*", "/api/loai-nkbv/del/*", "/api/loai-nkbv/put/*",
//            "/api/loai-form/post", "/api/loai-form/get/*", "/api/loai-form/del/*", "/api/loai-form/put/*",
//            "/data-form-json/post", "/data-form-json/get/*", "/data-form-json/del/*", "/data-form-json/put/*",
//            "/api/category/post", "/api/category/get/*", "/api/category/del/*", "/api/category/put/*","/api/upload",
//            "/api/application/post", "/api/application/get/*", "/api/application/del/*", "/api/application/put/*",
//            "/api/app-func/post", "/api/app-func/get/*", "/api/app-func/del/*", "/api/app-func/put/*",
//            "/api/user-app/post", "/api/user-app/get/*", "/api/user-app/del/*", "/api/user-app/put/*",
//            "/api/files/*","/css/*","/js/*","/api/form/*","/api/form/get/danhmuc","/api/form/update-form/*","/api/form/view-form/*","/api/form/view-data/*",
//            "/api/form/get/*","/api/form/put/display/*","/api/form-json/*","/api/form-json/get/*","/api/form-json/put/*","/api/form-json/post",
//            "/api/document/quantity-down/*"
//            "/form/view-form-by-cateId-active",
//            "/form/**",
//            "/api/department/get/depform/**",
//            "/api/loai-form/**",
//            "/form-json/**"
    };

    String[] ADMIN_LIST_PRIVATE = {
//            "/api/**",
    };

    String[] USER_LIST_PRIVATE = {
//            "/api/**",
//            "/api-student/**",

    };

}