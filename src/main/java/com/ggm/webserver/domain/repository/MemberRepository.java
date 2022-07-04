package com.ggm.webserver.domain.repository;

import com.ggm.webserver.domain.Member;
import com.ggm.webserver.domain.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findBySocialTypeAndSocialId(SocialType socialType , String socialId);
}