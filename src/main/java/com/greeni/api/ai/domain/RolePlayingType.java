package com.greeni.api.ai.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RolePlayingType {
	@JsonProperty("shop")
	SHOP("shop"),
	@JsonProperty("teacher")
	TEACHER("teacher"),
	@JsonProperty("friend")
	FRIEND("friend");

	private final String name;

	@JsonCreator
	public static RolePlayingType from(String value) {
		for (RolePlayingType type : RolePlayingType.values()) {
			if (type.name().equalsIgnoreCase(value)) { // 대소문자 무시 비교
				return type;
			}
		}
		return null;
	}
}
