package com.greeni.api.diaries.domain.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.DiaryErrorStatus;

public enum Emotion {
	HAPPY, SAD, ANGRY, ANXIETY, SURPRISED;


	public static Emotion toEmotion(String emotion) {
		if(emotion.equals("HAPPY")) return Emotion.HAPPY;
		else if(emotion.equals("SAD")) return Emotion.SAD;
		else if(emotion.equals("ANGRY")) return Emotion.ANGRY;
		else if(emotion.equals("ANXIETY")) return Emotion.ANXIETY;
		else if(emotion.equals("SURPRISED")) return Emotion.SURPRISED;
		throw new GeneralException(DiaryErrorStatus.NOT_VALID_EMOTION);
	}

	@JsonCreator
	public static Emotion from(String value) {
		return Emotion.valueOf(value.toUpperCase());
	}
}
