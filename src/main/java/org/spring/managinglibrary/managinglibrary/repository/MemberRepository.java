package org.spring.managinglibrary.managinglibrary.repository;
import org.spring.managinglibrary.managinglibrary.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);
    Optional<Member> findByNameContainingIgnoreCase(String name);
    Optional<Member> findByEmail(String email);
}
