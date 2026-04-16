package com.greeni.api.activities.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {

	FRIEND("친구"), TEACHER("학생"), SHOP("손님");

	private final String name;
}
