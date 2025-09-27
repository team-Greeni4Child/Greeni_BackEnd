package com.greeni.api.profiles.domain;

import com.greeni.api.activities.domain.Activity;
import com.greeni.api.common.BaseEntity;
import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.members.domain.Member;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private Integer attendance;

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
}
