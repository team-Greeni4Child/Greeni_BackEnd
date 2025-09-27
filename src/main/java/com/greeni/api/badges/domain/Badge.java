package com.greeni.api.badges.domain;

import com.greeni.api.common.BaseEntity;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "badges")
@DynamicUpdate
@DynamicInsert
public class Badge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ProfileBadge> profileBadgeList = new ArrayList<>();

}
