package com.greeni.api.profiles.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import com.greeni.api.activities.domain.Activity;
import com.greeni.api.common.base.BaseEntity;
import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.members.domain.Member;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "profiles")
@DynamicUpdate
@DynamicInsert
public class Profile extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String profileImage;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private LocalDate birth;

	@Column(nullable = false)
	private int attendance;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Diary> diaryList = new ArrayList<>();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	@Builder.Default
	private List<Activity> activityList = new ArrayList<>();

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
	@Builder.Default
	private List<ProfileBadge> profileBadgeList = new ArrayList<>();

	// 연관관계 편의 메서드
	public void setMember(Member member) {
		// 1. 기존 연관관계 제거
		if (this.member != null) {
			this.member.getProfileList().remove(this);
		}
		// 2. 새로운 member로 설정
		this.member = member;
		// 3. 새 member의 profileList에 this 추가
		if (member != null && !member.getProfileList().contains(this)) {
			member.getProfileList().add(this);
		}
	}

	// 프로필 수정 편의 메서드
	public void update(String profileImage, String name, LocalDate birth) {
		this.profileImage = profileImage;
		this.name = name;
		this.birth = birth;
	}

	// 프로필 출석 일수 증가 편의 메서드
	public void attend() {
		this.attendance++;
	}
}
