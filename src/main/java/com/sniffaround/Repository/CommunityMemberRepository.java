package com.sniffaround.Repository;

import com.sniffaround.Model.CommunityMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityMemberRepository extends JpaRepository<CommunityMember, Long> {
    Boolean existsByUserIdAndCommunityId(Long userId, Long communityId);
    Optional<CommunityMember> findByUserIdAndCommunityId(Long userId, Long communityId);
    long countByCommunityId(long communityId);
}
