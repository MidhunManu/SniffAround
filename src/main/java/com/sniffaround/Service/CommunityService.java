package com.sniffaround.Service;

import com.sniffaround.DTO.CommunityResponse;
import com.sniffaround.DTO.CreateCommunityRequest;
import com.sniffaround.DTO.UpdateCommunityRequest;
import com.sniffaround.Exception.CommunityNotFoundException;
import com.sniffaround.Mapper.CommunityMapper;
import com.sniffaround.Model.Community;
import com.sniffaround.Repository.CommunityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityMapper communityMapper;

    public List<CommunityResponse> index() {
        return this.communityRepository.findAll()
                .stream()
                .map(this.communityMapper::toCommunityResponse)
                .toList();
    }

    public CommunityResponse show(Long id) {
        return this.communityMapper.toCommunityResponse(this.communityRepository.findById(id).orElseThrow(() -> new CommunityNotFoundException(id)));
    }

    public CommunityResponse create(CreateCommunityRequest request) {
        return this.communityMapper.toCommunityResponse(this.communityRepository.save(this.communityMapper.toCommunityFromCreateDTO(request)));
    }

    public CommunityResponse update(Long id, UpdateCommunityRequest request) {
        Community community = this.communityRepository.findById(id).orElseThrow(() -> new CommunityNotFoundException(id));
        this.communityMapper.updateCommunityFromDTO(request, community);;
        this.communityRepository.save(community);
        return this.communityMapper.toCommunityResponse(community);
    }

    public void delete(Long id) {
        var community = this.communityRepository.findById(id).orElseThrow(() ->  new CommunityNotFoundException(id));
        this.communityRepository.deleteById(id);
    }
}
