package com.greeni.api.activities.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ActivityType {
	ATTENDANCE("출석"),
	DIARY("일기"),
	FIVE_QUESTIONS("다섯고개"),
	ROLE_PLAYING("역할놀이"),
	BADGE("배지");

	private final String name;
}
