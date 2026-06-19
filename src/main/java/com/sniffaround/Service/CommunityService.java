package com.sniffaround.Service;

import com.sniffaround.DTO.CommunityMemberResponse;
import com.sniffaround.DTO.CommunityResponse;
import com.sniffaround.DTO.CreateCommunityRequest;
import com.sniffaround.DTO.UpdateCommunityRequest;
import com.sniffaround.Enum.UserRoleEnum;
import com.sniffaround.Exception.*;
import com.sniffaround.Mapper.CommunityMapper;
import com.sniffaround.Mapper.CommunityMemberMapper;
import com.sniffaround.Model.Community;
import com.sniffaround.Model.CommunityMember;
import com.sniffaround.Model.User;
import com.sniffaround.Repository.CommunityMemberRepository;
import com.sniffaround.Repository.CommunityRepository;
import com.sniffaround.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;
    private final UserRepository userRepository;
    private final CommunityMemberRepository communityMemberRepository;
    private final CommunityMemberMapper communityMemberMapper;

    public List<CommunityResponse> index(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return this.communityRepository
                .findAll(pageable)
                .stream()
                .map(this.communityMapper::toCommunityResponse)
                .toList();
    }

    public CommunityResponse show(Long id) {
        return this.communityMapper.toCommunityResponse(this.communityRepository.findById(id).orElseThrow(() -> new CommunityNotFoundException(id)));
    }

    @Transactional
    public CommunityResponse create(CreateCommunityRequest request) {
        Community community = this.communityMapper.toCommunityFromCreateDTO(request);
        String username = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                        .getName();

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        community.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        community.setCreatedBy(user);

        CommunityMember communityMember = new CommunityMember();
        communityMember.setUser(user);
        communityMember.setCommunity(community);
        communityMember.setRole(UserRoleEnum.ADMIN.name());
        communityMember.setJoinedAt(new Timestamp(System.currentTimeMillis()));

        this.communityMemberRepository.save(communityMember);

        return this.communityMapper.toCommunityResponse(this.communityRepository.save(community));
    }

    public CommunityResponse update(Long id, UpdateCommunityRequest request) {
        Community community = this.communityRepository.findById(id).orElseThrow(() -> new CommunityNotFoundException(id));
        this.communityMapper.updateCommunityFromDTO(request, community);
        this.communityRepository.save(community);
        return this.communityMapper.toCommunityResponse(community);
    }

    public void delete(Long id) {
        this.communityRepository.findById(id).orElseThrow(() ->  new CommunityNotFoundException(id));
        this.communityRepository.deleteById(id);
    }

    @Transactional
    public CommunityMemberResponse joinCommunity(Long id) {
        Community community = this.communityRepository.findById(id).orElseThrow(() -> new CommunityNotFoundException(id));
        String username = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                        .getName();

        User user = this.userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        boolean alreadyJoined = this.communityMemberRepository.existsByUserIdAndCommunityId(user.getId(), id);

        if (alreadyJoined) {
            throw new UserAlreadyJoinedCommunityException(id);
        }

        CommunityMember member = new CommunityMember();
        member.setUser(user);
        member.setCommunity(community);
        member.setRole(UserRoleEnum.MEMBER.name());
        member.setJoinedAt(new Timestamp(System.currentTimeMillis()));

        return communityMemberMapper.toCommunityMemberResponse(this.communityMemberRepository.save(member));
    }

    @Transactional
    public void leaveCommunity(Long id) {
        this.communityRepository.findById(id)
                .orElseThrow(() -> new CommunityNotFoundException(id));

        String username = Objects.requireNonNull(SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                        .getName();

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        CommunityMember member = this.communityMemberRepository
                .findByUserIdAndCommunityId(user.getId(), id)
                .orElseThrow(UserNotCommunityMemberException::new);

        if (member.getRole().equals(UserRoleEnum.ADMIN.name())) {
            long memberCount = this.communityMemberRepository.countByCommunityId(id);
            if (memberCount > 1) {
                throw new OwnershipTransferRequiredException();
            }
        }

        this.communityMemberRepository.delete(member);
    }
}
