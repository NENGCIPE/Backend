package Nengcipe.NengcipeBackend.config;

import Nengcipe.NengcipeBackend.filter.JwtAuthenticationFilter;
import Nengcipe.NengcipeBackend.filter.JwtAuthorizationFilter;
import Nengcipe.NengcipeBackend.oauth2.OAuth2MemberService;
import Nengcipe.NengcipeBackend.oauth2.OAuth2SuccessHandler;
import Nengcipe.NengcipeBackend.repository.MemberRepository;
import Nengcipe.NengcipeBackend.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CorsFilter corsFilter;
    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final MemberRepository memberRepository;
    private final OAuth2MemberService oAuth2MemberService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .httpBasic().disable()
                .formLogin().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().apply(new MyCustom())
                .and().addFilter(corsFilter)
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .requestMatchers(HttpMethod.POST,"/api/users/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/recipes/{recipeId}").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/oauth2/login/**").permitAll()
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login()
                .authorizationEndpoint().baseUri("/oauth2/authorization")
                .and().redirectionEndpoint().baseUri("/login/oauth2/code/*")
                .and()
                .userInfoEndpoint().userService(oAuth2MemberService)
                .and().successHandler(oAuth2SuccessHandler).and().build();
    }

    public class MyCustom extends AbstractHttpConfigurer<MyCustom, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager, jwtUtil, objectMapper);
            jwtAuthenticationFilter.setFilterProcessesUrl("/api/users/login");
            http
                    .addFilter(jwtAuthenticationFilter)
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository, jwtUtil));

        }
    }

}
