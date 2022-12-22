package srau.api.config;

import srau.api.filter.JWTAuthenticationFilter;
import srau.api.filter.JWTAuthorizationFilter;
import srau.api.services.AuthenticationUserDetailService;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationUserDetailService authenticationUserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, AuthenticationConfigConstants.SIGN_UP_URL).permitAll()
//                USER ENDPOINT
                .antMatchers(HttpMethod.GET, "/api/v1/user").permitAll()
                .antMatchers("/api/v1/user/addRole").hasAuthority("ADMIN")
                .antMatchers("/api/v1/user/**").hasAuthority("ADMIN")
//                STUDENT ENDPOINT
                .antMatchers(HttpMethod.GET, "/api/v1/student").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/student/**").hasAuthority("ADMIN")
                .antMatchers("/api/v1/student/**").hasAnyAuthority("ADMIN", "STUDENT")
//                TEACHER ENDPOINT
                .antMatchers(HttpMethod.GET, "/api/v1/teacher").permitAll()
                .antMatchers(HttpMethod.DELETE, "/api/v1/teacher/**").hasAuthority("ADMIN")
                .antMatchers("/api/v1/teacher/**").hasAnyAuthority("ADMIN", "TEACHER")
//                SUBJECT ENDPOINT
                .antMatchers(HttpMethod.GET, "/api/v1/subject").permitAll()
                .antMatchers("/api/v1/subject").hasAuthority("ADMIN")
//                COURSE ENDPOINT
                .antMatchers(HttpMethod.GET, "/api/v1/course").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/course/**").hasAnyAuthority("ADMIN", "TEACHER")
                .antMatchers("/api/v1/course/**").hasAuthority("ADMIN")
//                LECTURE ENDPOINT
                .antMatchers(HttpMethod.GET, "/api/v1/lecture").permitAll()
                .antMatchers("/api/v1/lecture/**").hasAuthority("ADMIN")
//                GRADE ENDPOINT
                .antMatchers("/api/v1/grade/student/**").permitAll()
                .antMatchers("/api/v1/grade/**").hasAnyAuthority("ADMIN", "TEACHER")
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager()))
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(authenticationUserDetailService)
                .passwordEncoder(bCryptPasswordEncoder);
    }
}
