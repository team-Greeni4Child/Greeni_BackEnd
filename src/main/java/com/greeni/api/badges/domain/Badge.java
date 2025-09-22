package com.greeni.api.badges.domain;

import com.greeni.api.common.BaseEntity;
import com.greeni.api.profiles.domain.mapping.ProfileBadge;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Badge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "badge", cascade = CascadeType.ALL)
    private List<ProfileBadge> profileBadgeList = new ArrayList<>();

}
