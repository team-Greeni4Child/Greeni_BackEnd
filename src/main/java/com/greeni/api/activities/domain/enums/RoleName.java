package com.greeni.api.activities.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RoleName {

	FRIEND("친구"), STUDENT("학생"), CUSTOMER("손님");

	private final String name;
}
