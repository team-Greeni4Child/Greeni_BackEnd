# Greeni

> AI 캐릭터와의 음성 대화를 통해 아이의 감정 표현과 언어 발달을 돕는 아동 대상 놀이형 대화 서비스입니다.

## 1. 프로젝트 소개
![홈 화면](./images/greeni_introduce.png)

그리니(Greeni)는 아이가 AI 캐릭터와 대화하며 그림일기, 역할놀이, 다섯고개 등의 활동을 즐길 수 있는 AI 기반 아동 정서·놀이 서비스 플랫폼입니다.

아이에게는 자신의 생각과 감정을 자연스럽게 표현할 수 있는 안전한 디지털 놀이 환경을 제공하고, 보호자에게는 활동 기록과 감정 통계를 통해 아이의 상태와 관심사를 이해할 수 있는 정보를 제공합니다.

## 2. 프로젝트 배경

맞벌이 가정과 1인 가정이 증가하면서 부모와 자녀가 충분히 대화하는 시간이 줄어들고 있습니다. 특히 아동기는 정서와 언어 능력이 빠르게 발달하는 시기이지만, 아이가 자신의 감정을 말로 표현하거나 보호자가 이를 세심하게 파악하기 어려운 경우가 많습니다.

기존 아동 대상 디지털 콘텐츠는 영상 시청이나 학습 중심인 경우가 많아, 아이의 감정 표현과 상호작용을 충분히 지원하기 어렵습니다. 그리니는 이러한 문제의식에서 출발해, 아이가 놀이처럼 대화하고 기록하며 보호자가 아이의 감정 변화와 활동 흐름을 확인할 수 있는 서비스를 목표로 개발되었습니다.

## 3. 주요 기능

- 그림일기: 아이가 AI와 음성으로 대화하며 하루의 경험과 감정을 정리하고, 그림으로 일기를 남길 수 있습니다.
- 다섯고개: AI가 제공하는 힌트를 바탕으로 정답을 맞히는 추리형 놀이 기능입니다.
- 역할놀이: 가게주인과 손님, 선생님과 아이, 친구 사이 등 다양한 상황에서 AI와 대화를 진행할 수 있습니다.
- 마이페이지: 아이 프로필, 활동 기록, 감정 통계 등 서비스 이용 정보를 확인할 수 있습니다.

## 4. UI 화면

## 5. 개발 기간 및 팀원 소개

전체 개발 기간: 25.07 ~ 26.06

- 프로젝트 기획: 25.07 ~ 25.08
- UI/UX 설계: 25.08
- 시스템 구조 설계 및 기초 개발: 25.09 ~ 25.11
- 핵심 기능 API 연동: 25.12 ~ 26.02
- 기능 개선 및 안정화: 26.03 ~ 26.05
- 사용자 테스트: 26.05
- 마무리 및 출시: 26.05 ~ 26.06

| 김은지 | 송민아 | 이시연 |
| --- | --- | --- |
| ERD 설계<br>API 명세서<br>로그인 및 토큰 관련 API<br>다섯고개 관련 API<br>역할놀이 관련 API | ERD 설계<br>API 명세서<br>회원가입<br>파일 업로드 API<br>일기 관련 API<br>서버 배포 | 프로필 관련 API<br>배지 관련 API<br>통계 관련 API |

## 6. 기술 스택

| 구분 | 기술 |
| --- | --- |
| Language | Java 21 |
| Framework | Spring Boot 3.5.6 |
| Database | MySQL, AWS RDS, Redis |
| ORM | Spring Data JPA |
| 배포 | Docker, GitHub Actions, AWS, Nginx |

## 7. 아키텍처
![아키텍처](./images/greeni_architect.png)

## 8. 프로젝트 구조

```text
src
├── main
│   ├── java
│   │   └── com
│   │       └── greeni
│   │           └── api
│   │               ├── ApiApplication.java
│   │               ├── activities
│   │               │   ├── controller
│   │               │   │   ├── ActivityController.java
│   │               │   │   └── docs
│   │               │   │       └── ActivityControllerDocs.java
│   │               │   ├── converter
│   │               │   │   └── ActivityConverter.java
│   │               │   ├── domain
│   │               │   │   ├── Activity.java
│   │               │   │   └── enums
│   │               │   │       ├── ActivityType.java
│   │               │   │       └── RoleName.java
│   │               │   ├── dto
│   │               │   │   ├── ActivityRequestDTO.java
│   │               │   │   └── ActivityResponseDTO.java
│   │               │   ├── repository
│   │               │   │   └── ActivityRepository.java
│   │               │   └── service
│   │               │       ├── ActivityService.java
│   │               │       └── ActivityServiceImpl.java
│   │               ├── ai
│   │               │   ├── controller
│   │               │   │   ├── AIController.java
│   │               │   │   └── docs
│   │               │   │       └── AIControllerDocs.java
│   │               │   ├── domain
│   │               │   │   ├── Purpose.java
│   │               │   │   └── RolePlayingType.java
│   │               │   ├── dto
│   │               │   │   ├── AIRequestDTO.java
│   │               │   │   └── AIResponseDTO.java
│   │               │   └── service
│   │               │       └── AIService.java
│   │               ├── apiPayload
│   │               │   ├── CommonResponse.java
│   │               │   ├── exception
│   │               │   │   └── ExceptionAdvice.java
│   │               │   ├── handler
│   │               │   │   └── GeneralException.java
│   │               │   └── status
│   │               │       ├── BadgeErrorStatus.java
│   │               │       ├── CommonErrorStatus.java
│   │               │       ├── DiaryErrorStatus.java
│   │               │       ├── ErrorReason.java
│   │               │       ├── JwtErrorStatus.java
│   │               │       ├── MemberErrorStatus.java
│   │               │       ├── ProfileErrorStatus.java
│   │               │       ├── S3ErrorStauts.java
│   │               │       ├── SuccessStatus.java
│   │               │       └── TermErrorStatus.java
│   │               ├── badges
│   │               │   ├── controller
│   │               │   │   ├── BadgeController.java
│   │               │   │   └── docs
│   │               │   │       └── BadgeControllerDocs.java
│   │               │   ├── converter
│   │               │   │   └── BadgeConverter.java
│   │               │   ├── domain
│   │               │   │   └── Badge.java
│   │               │   ├── dto
│   │               │   │   ├── BadgeRequestDTO.java
│   │               │   │   └── BadgeResponseDTO.java
│   │               │   ├── repository
│   │               │   │   └── BadgeRepository.java
│   │               │   └── service
│   │               │       ├── BadgeService.java
│   │               │       └── BadgeServiceImpl.java
│   │               ├── common
│   │               │   ├── base
│   │               │   │   └── BaseEntity.java
│   │               │   └── config
│   │               │       ├── EmailConfig.java
│   │               │       ├── RedisConfig.java
│   │               │       ├── S3Config.java
│   │               │       ├── SwaggerConfig.java
│   │               │       └── WebClientConfig.java
│   │               ├── diaries
│   │               │   ├── controller
│   │               │   │   ├── DiaryController.java
│   │               │   │   └── docs
│   │               │   │       └── DiaryControllerDocs.java
│   │               │   ├── converter
│   │               │   │   ├── DiaryConverter.java
│   │               │   │   └── VoiceConverter.java
│   │               │   ├── domain
│   │               │   │   ├── Diary.java
│   │               │   │   ├── Voice.java
│   │               │   │   └── enums
│   │               │   │       ├── Emotion.java
│   │               │   │       └── VoiceRole.java
│   │               │   ├── dto
│   │               │   │   ├── DiaryRequestDTO.java
│   │               │   │   └── DiaryResponseDTO.java
│   │               │   ├── repository
│   │               │   │   ├── DiaryRepository.java
│   │               │   │   └── VoiceRepository.java
│   │               │   └── service
│   │               │       ├── DiaryService.java
│   │               │       └── DiaryServiceImpl.java
│   │               ├── members
│   │               │   ├── controller
│   │               │   │   ├── MemberController.java
│   │               │   │   └── docs
│   │               │   │       └── MemberControllerDocs.java
│   │               │   ├── converter
│   │               │   │   ├── MemberConverter.java
│   │               │   │   └── MemberTermConverter.java
│   │               │   ├── domain
│   │               │   │   ├── Member.java
│   │               │   │   ├── Term.java
│   │               │   │   └── mapping
│   │               │   │       └── MemberTerm.java
│   │               │   ├── dto
│   │               │   │   ├── MemberRequestDTO.java
│   │               │   │   └── MemberResponseDTO.java
│   │               │   ├── repository
│   │               │   │   ├── MemberRepository.java
│   │               │   │   ├── MemberTermRepository.java
│   │               │   │   └── TermRepository.java
│   │               │   └── service
│   │               │       ├── MemberService.java
│   │               │       └── MemberServiceImpl.java
│   │               ├── profiles
│   │               │   ├── controller
│   │               │   │   ├── ProfileController.java
│   │               │   │   ├── ProfileStatisticsController.java
│   │               │   │   └── docs
│   │               │   │       ├── ProfileControllerDocs.java
│   │               │   │       └── ProfileStatisticsControllerDocs.java
│   │               │   ├── converter
│   │               │   │   └── ProfileConverter.java
│   │               │   ├── domain
│   │               │   │   ├── Profile.java
│   │               │   │   └── mapping
│   │               │   │       └── ProfileBadge.java
│   │               │   ├── dto
│   │               │   │   ├── ProfileRequestDTO.java
│   │               │   │   └── ProfileResponseDTO.java
│   │               │   ├── repository
│   │               │   │   ├── ProfileBadgeRepository.java
│   │               │   │   └── ProfileRepository.java
│   │               │   └── service
│   │               │       ├── ProfileQueryService.java
│   │               │       ├── ProfileService.java
│   │               │       └── ProfileServiceImpl.java
│   │               ├── s3
│   │               │   ├── controller
│   │               │   │   ├── S3Controller.java
│   │               │   │   └── docs
│   │               │   │       └── S3ControllerDocs.java
│   │               │   ├── converter
│   │               │   │   └── S3Converter.java
│   │               │   ├── dto
│   │               │   │   └── S3ResponseDTO.java
│   │               │   └── service
│   │               │       ├── S3Service.java
│   │               │       └── S3ServiceImpl.java
│   │               └── security
│   │                   ├── auth
│   │                   │   ├── controller
│   │                   │   │   ├── AuthController.java
│   │                   │   │   └── docs
│   │                   │   │       └── AuthControllerDocs.java
│   │                   │   ├── converter
│   │                   │   │   └── AuthConverter.java
│   │                   │   ├── dto
│   │                   │   │   ├── AuthRequestDTO.java
│   │                   │   │   └── AuthResponseDTO.java
│   │                   │   └── service
│   │                   │       ├── AuthResponseWriter.java
│   │                   │       ├── AuthService.java
│   │                   │       └── AuthServiceImpl.java
│   │                   ├── config
│   │                   │   ├── AuthenticationManagerConfig.java
│   │                   │   └── SecurityConfig.java
│   │                   └── jwt
│   │                       ├── dto
│   │                       │   └── JwtProperties.java
│   │                       ├── enums
│   │                       │   └── RedisTokenType.java
│   │                       ├── filter
│   │                       │   ├── JwtAuthenticationFilter.java
│   │                       │   └── JwtExceptionHandlerFilter.java
│   │                       ├── handler
│   │                       │   ├── JwtAccessDeniedHandler.java
│   │                       │   └── JwtAuthenticationEntryPoint.java
│   │                       ├── provider
│   │                       │   ├── CustomAuthenticationProvider.java
│   │                       │   └── JwtProvider.java
│   │                       ├── redis
│   │                       │   └── TokenManager.java
│   │                       ├── token
│   │                       │   └── CustomAuthenticationToken.java
│   │                       └── userDetails
│   │                           ├── CustomUserDetails.java
│   │                           └── CustomUserDetailsService.java
│   └── resources
│       ├── application-dev.yml
│       ├── application.yml
│       └── data.sql
└── test
    └── java
        └── com
            └── greeni
                └── api
                    └── ApiApplicationTests.java
```

## 9. 플레이 스토어 출시
