-- Badge Initial Data Insertion SQL
INSERT IGNORE INTO badges (id, name, image_url, created_at, updated_at)
VALUES
-- 출석 관련 배지
(1, '5일 출석', 'https://temp.url/attendance_5.png', NOW(), NOW()),
(2, '10일 출석', 'https://temp.url/attendance_10.png', NOW(), NOW()),
(3, '30일 출석', 'https://temp.url/attendance_30.png', NOW(), NOW()),
(4, '50일 출석', 'https://temp.url/attendance_50.png', NOW(), NOW()),
(5, '100일 출석', 'https://temp.url/attendance_100.png', NOW(), NOW()),

-- 일기 작성 관련 배지
(6, '일기 5회 작성', 'https://temp.url/diary_5.png', NOW(), NOW()),
(7, '일기 10회 작성', 'https://temp.url/diary_10.png', NOW(), NOW()),
(8, '일기 30회 작성', 'https://temp.url/diary_30.png', NOW(), NOW()),
(9, '일기 50회 작성', 'https://temp.url/diary_50.png', NOW(), NOW()),
(10, '일기 100회 작성', 'https://temp.url/diary_100.png', NOW(), NOW()),

-- 다섯고개 정답 관련 배지
(11, '다섯고개 5회 정답', 'https://temp.url/quiz_5.png', NOW(), NOW()),
(12, '다섯고개 10회 정답', 'https://temp.url/quiz_10.png', NOW(), NOW()),
(13, '다섯고개 30회 정답', 'https://temp.url/quiz_30.png', NOW(), NOW()),
(14, '다섯고개 50회 정답', 'https://temp.url/quiz_50.png', NOW(), NOW()),
(15, '다섯고개 100회 정답', 'https://temp.url/quiz_100.png', NOW(), NOW()),

-- 역할놀이 수행 관련 배지
(16, '역할놀이 5회 수행', 'https://temp.url/roleplay_5.png', NOW(), NOW()),
(17, '역할놀이 10회 수행', 'https://temp.url/roleplay_10.png', NOW(), NOW()),
(18, '역할놀이 30회 수행', 'https://temp.url/roleplay_30.png', NOW(), NOW()),
(19, '역할놀이 50회 수행', 'https://temp.url/roleplay_50.png', NOW(), NOW()),
(20, '역할놀이 100회 수행', 'https://temp.url/roleplay_100.png', NOW(), NOW());

-- Term Initial Data Insertion SQL
INSERT IGNORE INTO terms (id, name, file_url, required, version, created_at, updated_at)
VALUES (1, '만 14세 미만 약관', 'tmp.html', true, 1, NOW(), NOW()),
       (2, '이용약관', 'tmp.html', true, 1, NOW(), NOW()),
       (3, '개인정보 수집·이용 동의서', 'tmp.html', true, 1, NOW(), NOW());