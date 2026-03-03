package com.greeni.api.ai.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum RolePlayingType {
	@JsonProperty("shop")
	SHOP,
	@JsonProperty("teacher")
	TEACHER,
	@JsonProperty("friend")
	FRIEND
}
