package com.ktb.ktb_cj_community_spring_be.member.repository;

import com.ktb.ktb_cj_community_spring_be.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

      Optional<Member> findByEmail(String email);

      boolean existsByEmail(String email);

      boolean existsByNickName(String nickName);
}
