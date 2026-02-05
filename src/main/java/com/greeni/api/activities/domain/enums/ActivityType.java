package com.greeni.api.activities.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActivityType {
	DIARY("일기"), FIVE_QUESTION("다섯고개"), ROLE_PLAYING("역할놀이"), BADGE("배지");

	private final String name;
}
