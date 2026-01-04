package com.greeni.api.profiles.service;

import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.MemberErrorStatus;
import com.greeni.api.apiPayload.status.ProfileErrorStatus;
import com.greeni.api.diaries.repository.DiaryRepository;
import com.greeni.api.members.domain.Member;
import com.greeni.api.members.repository.MemberRepository;
import com.greeni.api.profiles.converter.ProfileConverter;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.dto.ProfileRequestDTO;
import com.greeni.api.profiles.dto.ProfileResponseDTO;
import com.greeni.api.profiles.repository.ProfileRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileServiceImpl implements ProfileService {

	private final ProfileRepository profileRepository;
	private final MemberRepository memberRepository;
	private final DiaryRepository diaryRepository;

	// 프로필 생성
	@Override
	@Transactional
	public ProfileResponseDTO.CreateProfileResponse createProfile(Long memberId,
		ProfileRequestDTO.CreateProfileRequest dto) {
		// 프로필 생성 가능 개수는 최대 6개
		int count = profileRepository.countByMemberId(memberId);
		if (count >= 6) {
			throw new GeneralException(ProfileErrorStatus.PROFILE_LIMIT_EXCEEDED);
		}

		// Member, Profile 엔티티 생성
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new GeneralException(MemberErrorStatus.NOT_EXIST_MEMBER));
		Profile profile = ProfileConverter.toProfile(member, dto);

		// 저장
		Profile saved = profileRepository.save(profile);

		// DTO 변환 후 반환
		return ProfileConverter.toCreateProfileResponseDTO(saved);
	}

	// 프로필 수정
	@Override
	@Transactional
	public ProfileResponseDTO.UpdateProfileResponse updateProfile(Long memberId, Long profileId,
		ProfileRequestDTO.UpdateProfileRequest dto) {
		// Profile 엔티티 조회
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

		// 이 Profile이 현재 로그인한 회원의 것인지 검증
		if (!profile.getMember().getId().equals(memberId)) {
			throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
		}

		// 업데이트
		profile.update(dto.profileImage(), dto.name(), dto.birth());

		// DTO 변환 후 반환
		return ProfileConverter.toUpdateProfileResponseDTO(profile);
	}

	// 프로필 삭제
	@Override
	@Transactional
	public void deleteProfile(Long memberId, Long profileId) {
		// Profile 엔티티 조회
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

		// 본인 Profile인지 검증
		if (!profile.getMember().getId().equals(memberId)) {
			throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
		}

		// 삭제
		profileRepository.delete(profile);
	}

	// 프로필 목록 조회
	@Override
	public ProfileResponseDTO.GetProfileListResponse getProfileList(Long memberId) {
		// Profile 리스트 조회
		List<Profile> profiles = profileRepository.findAllByMemberId(memberId);

		// DTO 변환 후 반환
		return ProfileConverter.toGetProfileListResponseDTO(profiles);
	}

	// 프로필 단일 조회
	@Override
	public ProfileResponseDTO.GetProfileResponse getProfile(Long memberId, Long profileId) {
		// Profile 엔티티 조회
		Profile profile = profileRepository.findById(profileId)
			.orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

		// 본인 Profile인지 검증
		if (!profile.getMember().getId().equals(memberId)) {
			throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
		}

		// DTO 변환 후 반환
		return ProfileConverter.toGetProfileResponseDTO(profile);
	}

	// 출석 및 일기 횟수 조회
	@Override
	public ProfileResponseDTO.GetAttendanceDiaryCountResponse getAttendanceDiaryCount(Long memberId, Long profileId) {
		// Profile 엔티티 조회
		Profile profile = profileRepository.findById(profileId)
				.orElseThrow(() -> new GeneralException(ProfileErrorStatus.PROFILE_NOT_FOUND));

		// 본인 Profile인지 검증
		if (!profile.getMember().getId().equals(memberId)) {
			throw new GeneralException(ProfileErrorStatus.UNAUTHORIZED_PROFILE_ACCESS);
		}

		// DTO 변환 후 반환
		int diaryCount = diaryRepository.countByProfileId(profileId);
		return ProfileConverter.toAttendanceDiaryCountResponseDTO(profile, diaryCount);
	}
}
