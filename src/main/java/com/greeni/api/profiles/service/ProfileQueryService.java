package com.greeni.api.profiles.service;

import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileQueryService {

    private final ProfileRepository profileRepository;

    public Profile findProfileAndValidate(Long profileId, Long memberId) {
        // Profile 엔티티 조회
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

        // member의 profile인지 검증
        if (!profile.getMember().getId().equals(memberId)) {
            throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
        }
        return profile;
    }
}
