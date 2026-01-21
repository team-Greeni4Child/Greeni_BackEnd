INSERT IGNORE INTO terms (id, name, content, required, created_at, updated_at)
VALUES (1, '약관1', '내용1', true, NOW(), NOW()),
       (2, '약관2', '내용2', true, NOW(), NOW()),
       (3, '약관3', '내용3', false, NOW(), NOW());