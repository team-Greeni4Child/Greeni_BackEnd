package com.greeni.api.ai.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Purpose {

	@JsonProperty("diary")
	DIARY("diary"),
	@JsonProperty("roleplay")
	ROLEPLAY("roleplay"),
	@JsonProperty("game")
	GAME("game");

	private final String name;
}
