package org.spring.managinglibrary.managinglibrary;

import org.spring.managinglibrary.managinglibrary.model.Member;
import org.spring.managinglibrary.managinglibrary.model.Role;
import org.spring.managinglibrary.managinglibrary.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class ManagingLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagingLibraryApplication.class, args);
    }

    @Bean
    public CommandLineRunner seedMembers(MemberRepository repo, PasswordEncoder encoder) {
        return args -> {
            repo.findByEmail("admin@library.com").ifPresent(repo::delete);
            repo.findByEmail("member@library.com").ifPresent(repo::delete);

            Member admin = new Member();
            admin.setName("Admin User");
            admin.setEmail("admin@library.com");
            admin.setPassword(encoder.encode("admin123")); // BCrypt hash
            admin.setPhoneNumber("0780000000");
            admin.setRole(Role.ADMIN);
            repo.save(admin);
            System.out.println("Admin user seeded");

            Member member = new Member();
            member.setName("Regular Member");
            member.setEmail("member@library.com");
            member.setPassword(encoder.encode("member123"));
            member.setPhoneNumber("0781111111");
            member.setRole(Role.MEMBER);
            repo.save(member);
            System.out.println(" Regular member seeded");
        };
    }
}