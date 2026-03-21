package org.spring.managinglibrary.managinglibrary.repository;
import org.spring.managinglibrary.managinglibrary.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
}
