package mathpar.web.learning.school._configs.security;

import mathpar.web.learning.school.services.AccountService;
import mathpar.web.learning.school.services.UserProfileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final TokenValidationFilter tokenFilter;
    private final UserDetailsPopulationFilter userDetailsPopulationFilter;

    public SecurityConfiguration(AccountService accountService, UserProfileService userProfileService) {
        this.tokenFilter = new TokenValidationFilter(accountService, userProfileService);
        this.userDetailsPopulationFilter = new UserDetailsPopulationFilter(userProfileService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().disable();
        http.anonymous().disable();
        http.sessionManagement().disable();
        http.addFilterAfter(tokenFilter, ConcurrentSessionFilter.class);
        http.addFilterAfter(userDetailsPopulationFilter, TokenValidationFilter.class);
        http.csrf().disable();
        http.cors();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
