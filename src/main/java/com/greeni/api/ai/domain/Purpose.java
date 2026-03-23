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
	@JsonProperty("fiveq")
	FIVEQ("fiveq");

	private final String name;
}
