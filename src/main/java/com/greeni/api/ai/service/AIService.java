package com.greeni.api.ai.service;

import com.greeni.api.ai.domain.Purpose;
import com.greeni.api.ai.domain.RolePlayingType;
import com.greeni.api.ai.dto.AIRequestDTO;
import com.greeni.api.ai.dto.AIResponseDTO;
import com.greeni.api.apiPayload.handler.GeneralException;
import com.greeni.api.apiPayload.status.CommonErrorStatus;
import com.greeni.api.apiPayload.status.DiaryErrorStatus;
import com.greeni.api.diaries.domain.Diary;
import com.greeni.api.diaries.domain.Voice;
import com.greeni.api.diaries.domain.enums.Emotion;
import com.greeni.api.diaries.domain.enums.VoiceRole;
import com.greeni.api.diaries.repository.DiaryRepository;
import com.greeni.api.profiles.domain.Profile;
import com.greeni.api.profiles.service.ProfileQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AIService {

    private final WebClient aiWebClient;
    private final DiaryRepository diaryRepository;
    private final RedisTemplate<String, String> redistemplate;
    private final ProfileQueryService profileQueryService;

    public Mono<AIResponseDTO.FiveQuestionsHintResponse> fiveQuestionsHint(AIRequestDTO.FiveQuestionsHint request) {

        Purpose purpose = Purpose.FIVEQ;

        return aiWebClient.post()
                .uri("/game/fiveq/hint")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AIResponseDTO.FiveQuestionsHintText.class)

                .flatMap(context -> {
                    List<String> hintList = context.hints();
                    if (hintList == null) {
                        hintList = Collections.emptyList();
                    }

                    if (hintList.isEmpty()) {
                        return Mono.just(AIResponseDTO.FiveQuestionsHintResponse.builder()
                                .hints(Collections.emptyList())
                                .build());
                    }
                    return Flux.fromIterable(hintList)
                            .concatMap(hintText -> {
                                AIRequestDTO.TTSRequest ttsRequest = new AIRequestDTO.TTSRequest(
                                        purpose, hintText, null, null, null
                                );
                                return aiWebClient.post()
                                        .uri("/tts/speak")
                                        .bodyValue(ttsRequest)
                                        .retrieve()
                                        .bodyToMono(AIResponseDTO.TTSResponse.class)
                                        .doOnError(WebClientResponseException.class, e -> {
                                            log.error("TTS 에러: {}", e.getResponseBodyAsString());
                                        })
                                        .map(base64Audio -> new AIResponseDTO.TextVoiceData(hintText, base64Audio.audioContent()));
                            })
                            .collectList()
                            .map(hintDataList -> AIResponseDTO.FiveQuestionsHintResponse.builder()
                                    .hints(hintDataList)
                                    .build()
                            );
                });
    }

    public Mono<AIResponseDTO.FiveQuestionsCheckResponse> fiveQuestionsCheck(AIRequestDTO.FiveQuestionsCheck request) {

        Purpose purpose = Purpose.FIVEQ;

        MultipartBodyBuilder builder = buildVoiceMultipart(request.voice(), purpose, null);

        return aiWebClient.post()
                .uri("/stt/transcribe")
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(AIResponseDTO.STTResponse.class)
                .doOnError(WebClientResponseException.class, e -> {
                    log.error("STT 에러: {}", e.getResponseBodyAsString());
                })
                .flatMap(sttRes -> {
                    String recognizedText = sttRes.text();
                    log.debug("아이가 한 말: {}", recognizedText);

                    AIRequestDTO.FiveQuestionsCheckInner checkInnerReq = new AIRequestDTO.FiveQuestionsCheckInner(
                            recognizedText,
                            request.answer()
                    );

                    return aiWebClient.post()
                            .uri("/game/fiveq/check")
                            .bodyValue(checkInnerReq)
                            .retrieve()
                            .bodyToMono(AIResponseDTO.FiveQuestionsCheckAnswer.class)
                            .doOnError(e -> log.error("FiveQ Check 에러: {}", e.getMessage()))
                            .map(checkAns -> {
                                Boolean correct = checkAns.correct();
                                return AIResponseDTO.FiveQuestionsCheckResponse.of(
                                        correct, null
                                );
                            });
                });
    }

    public Mono<AIResponseDTO.RolePlayingResponse> rolePlaying(AIRequestDTO.RolePlaying request) {

        Purpose purpose = Purpose.ROLEPLAY;
        RolePlayingType type = RolePlayingType.valueOf(request.role().toUpperCase());

        MultipartBodyBuilder builder = buildVoiceMultipart(request.voice(), purpose, null);

        return aiWebClient.post()
                .uri("/stt/transcribe")
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(AIResponseDTO.STTResponse.class)
                .doOnError(WebClientResponseException.class, e -> {
                    log.error("STT 에러: {}", e.getResponseBodyAsString());
                })
                .flatMap(sttRes -> {
                    String recognizedText = sttRes.text();
                    log.debug("아이가 한 말: {}", recognizedText);

                    AIRequestDTO.RolePlayingInner rolePlayingReq = new AIRequestDTO.RolePlayingInner(
                            request.sessionId(), type, recognizedText, null, null, null
                    );

                    return aiWebClient.post()
                            .uri("/chat/roleplay")
                            .bodyValue(rolePlayingReq)
                            .retrieve()
                            .bodyToMono(AIResponseDTO.RolePlayingInnerResponse.class)
                            .doOnError(e -> log.error("RolePlaying 에러: {}", e.getMessage()))
                            .flatMap(roleRes -> {
                                AIRequestDTO.TTSRequest ttsRequest = new AIRequestDTO.TTSRequest(
                                        purpose, roleRes.reply(), null, null, null
                                );
                                return aiWebClient.post()
                                        .uri("/tts/speak")
                                        .bodyValue(ttsRequest)
                                        .retrieve()
                                        .bodyToMono(AIResponseDTO.TTSResponse.class)
                                        .doOnError(WebClientResponseException.class, e -> {
                                            log.error("TTS 에러: {}", e.getResponseBodyAsString());
                                        })
                                        .map(base64Audio -> AIResponseDTO.RolePlayingResponse.of(
                                                request.sessionId(), base64Audio.audioContent(), roleRes.reply(), roleRes.turn()
                                        ));
                            });
                });
    }

    public Mono<AIResponseDTO.RolePlayingEndResponse> rolePlayingClose(AIRequestDTO.RolePlayingEnd request) {
        return aiWebClient.post()
                .uri("/chat/roleplay/close")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AIResponseDTO.RolePlayingEndResponse.class)
                .map(result -> AIResponseDTO.RolePlayingEndResponse.of(request.sessionId()));
    }

    private MultipartBodyBuilder buildVoiceMultipart(MultipartFile voice, Purpose purpose, String sessionId) {

        MultipartBodyBuilder builder = new MultipartBodyBuilder();

        try {
            byte[] voiceBytes = voice.getBytes();
            String originalFileName = voice.getOriginalFilename();

            if (originalFileName == null || originalFileName.isEmpty()) {
                originalFileName = "audio.mp4";
            }

            builder.part("voice", new ByteArrayResource(voiceBytes))
                    .filename(originalFileName)
                    .contentType(MediaType.parseMediaType("audio/mp4"));
        } catch (IOException e) {
            throw new GeneralException(CommonErrorStatus.VOICE_PARSING_ERROR);
        }
        builder.part("purpose", purpose.getName());
        if (sessionId != null) {
            builder.part("session_id", sessionId);
        }

        return builder;
    }

    // 일기 대화 요청 로직
    public Mono<AIResponseDTO.DiaryResponse> diary(AIRequestDTO.DiaryRequest request, Long memberId) {
        // 프론트에서 받은 s3 redis에 저장하는 로직
        Profile profile = profileQueryService.findProfileAndValidate(request.profileId(), memberId);

        if(diaryRepository.findByProfileIdAndDiaryDate(profile.getId(), LocalDate.now()).isPresent()){
            throw new GeneralException(DiaryErrorStatus.EXIST_TODAY_DIARY);
        }

        ListOperations<String, String> ops = redistemplate.opsForList();
        String key = "diary:voice:" + memberId + ":" + request.profileId();
        String value = "CHILD|" + request.voiceUrl();
        ops.rightPush(key, value);
        if(redistemplate.getExpire(key) == -1) {
            redistemplate.expire(key, 1, TimeUnit.HOURS);
        }

        // 일기 요청 대화 로직
        Purpose purpose = Purpose.DIARY;

        MultipartBodyBuilder builder = buildVoiceMultipart(request.voice(), purpose, null);

        return aiWebClient.post()
                .uri("/stt/transcribe")
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(AIResponseDTO.STTResponse.class)
                .doOnError(WebClientResponseException.class, e -> {
                    log.error("STT 에러: {}", e.getResponseBodyAsString());
                })
                .flatMap(sttRes -> {
                    String recognizedText = sttRes.text();
                    log.debug("아이가 한 말: {}", recognizedText);

                    AIRequestDTO.DiaryInner diaryReq = new AIRequestDTO.DiaryInner(
                            request.session_id(), recognizedText
                    );

                    return aiWebClient.post()
                            .uri("/diary/chat")
                            .bodyValue(diaryReq)
                            .retrieve()
                            .bodyToMono(AIResponseDTO.DiaryInnerResponse.class)
                            .doOnError(e -> log.error("Diary 에러: {}", e.getMessage()))
                            .flatMap(diaryInnerResponse -> {
                                AIRequestDTO.TTSRequest ttsRequest = new AIRequestDTO.TTSRequest(
                                        purpose, diaryInnerResponse.reply(), null, null, null
                                );
                                return aiWebClient.post()
                                        .uri("/tts/speak")
                                        .bodyValue(ttsRequest)
                                        .retrieve()
                                        .bodyToMono(AIResponseDTO.TTSResponse.class)
                                        .doOnError(WebClientResponseException.class, e ->
                                                log.error("TTS 에러: {}", e.getResponseBodyAsString())
                                        )
                                        // ai서버로부터 온 s3 url 저장 로직
                                        .map(ttsRes -> {
                                            if (ttsRes.audioUrl() != null) {
                                                String aiValue = "GREENI|" + ttsRes.audioUrl();
                                                List<String> existingList = ops.range(key, 0, -1);

                                                if (existingList == null || !existingList.contains(aiValue)) {
                                                    ops.rightPush(key, aiValue);

                                                    if (redistemplate.getExpire(key) == -1) {
                                                        redistemplate.expire(key, 1, TimeUnit.HOURS);
                                                    }
                                                } else {
                                                    log.warn("중복 GREENI 음성 감지 - Redis 저장 생략: {}", aiValue);
                                                }
                                            }
                                            return AIResponseDTO.DiaryResponse.of(
                                                    request.session_id(),
                                                    ttsRes.audioContent(),
                                                    diaryInnerResponse.reply(),
                                                    diaryInnerResponse.turn_count()
                                            );
                                        });
                            });
                });
    }

    public Mono<?> diaryClose(AIRequestDTO.DiaryCloseRequest request, Long memberId) {
        // ai에게 대화 종료 요청 (ended)
        Profile profile = profileQueryService.findProfileAndValidate(request.profileId(), memberId);
        String key = "diary:voice:" + memberId + ":" + request.profileId();
        return aiWebClient.post()
                .uri("/diary/end")
                .bodyValue(request)   // session_id 포함된 DTO
                .retrieve()
                .bodyToMono(AIResponseDTO.DiaryAbnormalEndInnerResponse.class)
                .doOnError(WebClientResponseException.class, e ->
                        log.error("Diary End 에러: {}", e.getResponseBodyAsString())
                )
                .doOnSuccess(res -> {
                    redistemplate.delete(key);
                });
    }

    @Transactional
    public Mono<AIResponseDTO.DiaryCloseResponse> diarySummarize(AIRequestDTO.DiarySummarizeRequest request, Long memberId) {
        Profile profile = profileQueryService.findProfileAndValidate(request.profileId(), memberId);
        String key = "diary:voice:" + memberId + ":" + request.profileId();

        AIRequestDTO.DiarySummarizeInnerRequest innerReq =
                new AIRequestDTO.DiarySummarizeInnerRequest(
                        request.sessionId()
                );
        // db에 저장 후, 프론트에게 값 돌려주기
        // ai에게 대화 요약 요청
        return aiWebClient.post()
                .uri("/diary/summarize")
                .bodyValue(innerReq)
                .retrieve()
                .bodyToMono(AIResponseDTO.DiarySummarizeInnerResponse.class)
                .doOnError(WebClientResponseException.class, e ->
                        log.error("Diary Summarize 에러: {}", e.getResponseBodyAsString())
                )
                .flatMap(aiRes -> {
                    //  1. Redis에서 voice 리스트 조회
                    List<String> voiceRawList = redistemplate.opsForList().range(key, 0, -1);

                    //  2. Diary 생성
                    Diary diary = Diary.builder()
                            .diaryImage(request.imageUrl())
                            .summary(aiRes.summary())
                            .emotion(Emotion.from(aiRes.emotion().primary()))
                            .keyword(aiRes.keyword())
                            .diaryDate(LocalDate.now())
                            .profile(profile)
                            .build();

                    //  3. Voice 변환
                    if (voiceRawList != null) {
                        for (String raw : voiceRawList) {

                            String[] parts = raw.split("\\|");
                            String roleStr = parts[0];
                            String url = parts[1];

                            Voice voice = Voice.builder()
                                    .sessionId(request.sessionId())
                                    .voiceUrl(url)
                                    .voiceRole(
                                            roleStr.equals("CHILD")
                                                    ? VoiceRole.CHILD
                                                    : VoiceRole.GREENI
                                    )
                                    .diary(diary)
                                    .build();

                            diary.getVoiceList().add(voice);
                        }
                    }

                    //  4. DB 저장 (JPA는 blocking)
                    return Mono.fromCallable(() -> diaryRepository.save(diary))
                            .subscribeOn(Schedulers.boundedElastic())
                            .map(saved -> {

                                // 5. Redis 삭제
                                redistemplate.delete(key);

                                return AIResponseDTO.DiaryCloseResponse.of(
                                        saved.getId()
                                );
                            });
                });
    }

}
