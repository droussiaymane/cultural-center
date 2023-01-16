package com.ju.islamicculturalcenter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ju.islamicculturalcenter.dto.response.CODE;
import com.ju.islamicculturalcenter.dto.response.Response;
import com.ju.islamicculturalcenter.filter.CustomAuthorizationFilter;
import com.ju.islamicculturalcenter.service.auth.CustomUserDetailsService;
import com.ju.islamicculturalcenter.service.auth.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class Config extends WebSecurityConfigurerAdapter {

    public static final String USER_LOGIN_PATH = "/api/v1/user/auth/login";
    public static final String SUPER_ADMIN_PATH = "/api/v1/admin/admins";
    public static final String ADMIN_INSTRUCTOR_PATH = "/api/v1/admin/instructors/**";
    public static final String ADMIN_STUDENT_PATH = "/api/v1/admin/students/**";
    public static final String ADMIN_COURSE_PATH = "/api/v1/admin/courses/**";
    public static final String INSTRUCTOR_PATH = "/api/v1/instructor/**";
    private static final String SWAGGER_UI_HTML_PAGE = "/swagger-ui/**";
    private static final String SWAGGER_UI_PATH = "/swagger-ui/*";
    private static final String DOCS_PATH = "/v3/api-docs/**";
    private static final List<String> ALLOWED_METHODS = Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "PATCH");
    private static final List<String> ALLOWED_HEADERS = Arrays.asList("x-requested-with", "authorization", "Content-Type",
            "Authorization", "credential", "X-XSRF-TOKEN", "X-Refresh-Token", "X-Client-Id", "x-client-id");
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint((request, response, e) -> accessDenied(response))
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .cors().configurationSource(request -> getCorsConfiguration())
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(SWAGGER_UI_HTML_PAGE).permitAll()
                .antMatchers(SWAGGER_UI_PATH).permitAll()
                .antMatchers(DOCS_PATH).permitAll()
                .antMatchers(HttpMethod.POST, SUPER_ADMIN_PATH).hasAuthority("super-admin")
                .antMatchers(HttpMethod.PUT, SUPER_ADMIN_PATH).hasAuthority("super-admin")
                .antMatchers(HttpMethod.DELETE, SUPER_ADMIN_PATH).hasAuthority("super-admin")
                .antMatchers(HttpMethod.PATCH, SUPER_ADMIN_PATH.concat("/reset-password")).hasAuthority("super-admin")
                .antMatchers(SUPER_ADMIN_PATH.concat("/**")).hasAnyAuthority("super-admin", "admin")
                .antMatchers(ADMIN_INSTRUCTOR_PATH).hasAnyAuthority("super-admin", "admin")
                .antMatchers(ADMIN_STUDENT_PATH).hasAnyAuthority("super-admin", "admin")
                .antMatchers(ADMIN_COURSE_PATH).hasAnyAuthority("super-admin", "admin")
                .antMatchers(INSTRUCTOR_PATH).hasAnyAuthority("instructor")
                .antMatchers(USER_LOGIN_PATH).permitAll()

                .anyRequest().authenticated();

        http.addFilterBefore(new CustomAuthorizationFilter(userDetailsService, jwtUtil), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private void accessDenied(HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(objectMapper.writeValueAsString(Response.builder()
                .code(CODE.FORBIDDEN.getId())
                .message("Access denied")
                .success(false)
                .build()));
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private CorsConfiguration getCorsConfiguration() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(ALLOWED_HEADERS);
        corsConfiguration.setAllowedMethods(ALLOWED_METHODS);
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }
}
