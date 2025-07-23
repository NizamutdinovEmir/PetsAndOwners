package nizamutdinov..config;

import nizamutdinov..service.OwnerDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests(auth -> auth
                        // Swagger/OpenAPI и аутентификация открытыми
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/auth/**").permitAll()
                        // регистрация нового владельца должна быть публичной
                        .requestMatchers(HttpMethod.POST, "/api/owners").permitAll()
                        // Чтение питомцев — любой залогиненный
                        .requestMatchers(HttpMethod.GET, "/api/pets/**").authenticated()
                        // Все остальные запросы (POST/PUT/DELETE) к /api/pets/** — тоже требуют аутентификации,
                        // но фактическую проверку владельца/роли делаем через @PreAuthorize в контроллере
                        .requestMatchers("/api/pets/**").authenticated()
                        // Все остальные запросы — тоже аутентифицированы
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web
                .ignoring().requestMatchers("/health");  // только это разрешено
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http,
                                             PasswordEncoder encoder,
                                             OwnerDetailsService uds) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(uds)
                .passwordEncoder(encoder)
                .and()
                .build();
    }
}