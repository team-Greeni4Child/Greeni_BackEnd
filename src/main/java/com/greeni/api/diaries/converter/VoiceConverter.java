package com.greeni.api.diaries.converter;

import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.diaries.domain.Voice;
import com.greeni.api.diaries.domain.enums.VoiceRole;
import com.greeni.api.diaries.dto.DiaryRequestDTO;

import java.util.List;

public class VoiceConverter {

    public static List<Voice> toVoice(List<DiaryRequestDTO.VoiceDTO> voices, Long sessionId, Diary diary){
        return voices.stream()
                .map(voiceDto -> Voice.builder()
                        .voiceUrl(voiceDto.getUrl())
                        .voiceRole(voiceDto.getRole().equals("CHILD") ? VoiceRole.CHILD : VoiceRole.GREENI)
                        .sessionId(sessionId)
                        .diary(diary)
                        .build())
                .toList();
    }

}
