package com.greeni.api.diaries.converter;

import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.diaries.domain.Voice;
import com.greeni.api.diaries.domain.enums.VoiceRole;
import com.greeni.api.diaries.dto.DiaryRequestDTO;

import java.util.List;

public class VoiceConverter {

    public static List<Voice> toVoice(List<String> voices, Long sessionId, Diary diary){
        return voices.stream()
                .map(v -> {
                    String[] parts = v.split("\\|");
                    String role = parts[0];
                    String url = parts[1];

                    return Voice.builder()
                            .voiceUrl(url)
                            .voiceRole(role.equals("CHILD") ? VoiceRole.CHILD : VoiceRole.GREENI)
                            .sessionId(sessionId)
                            .diary(diary)
                            .build();
                })
                .toList();
    }

}
