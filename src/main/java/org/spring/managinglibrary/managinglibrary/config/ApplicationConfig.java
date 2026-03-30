package org.spring.managinglibrary.managinglibrary.config;

import org.spring.managinglibrary.managinglibrary.repository.MemberRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfig {

    private final MemberRepository memberRepository;

    public ApplicationConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found: " + username));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        // Pass UserDetailsService to constructor
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        // Create ProviderManager with our provider that uses BCrypt
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder()); // 👈 set BCrypt here
        return new ProviderManager(provider);
    }
}