INSERT INTO churches (name, pastor_name, website_url, address, email, phone, description, worship_times,
                      location_lat, location_lng, created_at, updated_at, updated_by, status)
VALUES
-- 현진교회
('현진교회', '박현진', 'http://hyunjin.or.kr', '경기도 안양시 동안구 흥안대로 519', 'hyunjin@church.or.kr', '031-123-4567',
 '사랑과 말씀으로 세워지는 공동체입니다.', '주일 11:00, 수요예배 19:30', 37.401859, 126.976937, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 서울역
('서울역교회', '이성훈', 'http://seoulchurch.or.kr', '서울특별시 중구 통일로 1', 'seoul@church.or.kr', '02-100-1000',
 '중심에 복음을 전하는 교회입니다.', '주일 10:00, 수요 19:30', 37.554722, 126.970833, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 강남역
('강남은혜교회', '박진수', 'http://gracegangnam.or.kr', '서울특별시 강남구 강남대로 396', 'gangnam@grace.or.kr', '02-200-2000',
 '은혜로운 예배와 말씀이 있는 교회입니다.', '주일 11:00, 금요기도회 21:00', 37.497942, 127.027621, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 홍대입구역
('홍대빛의교회', '최은혜', 'http://honglight.or.kr', '서울 마포구 양화로 183', 'hong@light.or.kr', '02-300-3000',
 '청년 중심의 예배와 사역이 활발한 교회입니다.', '주일 14:00, 목요예배 19:30', 37.557192, 126.924903, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 잠실역
('잠실소망교회', '김소망', 'http://jamsilhope.or.kr', '서울 송파구 올림픽로 240', 'jamsil@hope.or.kr', '02-400-4000',
 '소망의 메시지를 전하는 따뜻한 공동체입니다.', '주일 09:00, 수요 19:00', 37.513305, 127.100087, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 건대입구역
('건대열방교회', '윤민수', 'http://kondae.or.kr', '서울 광진구 아차산로 243', 'kondae@nations.or.kr', '02-500-5000',
 '세계 선교를 꿈꾸는 교회입니다.', '주일 11:00, 금요 20:00', 37.540420, 127.070290, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 신촌역
('신촌은총교회', '정예진', 'http://shinchon.or.kr', '서울 서대문구 신촌로 73', 'shinchon@grace.or.kr', '02-600-6000',
 '믿음과 은총으로 함께하는 공동체입니다.', '주일 10:30, 토요기도 07:00', 37.555134, 126.936893, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 사당역
('사당믿음교회', '한기훈', 'http://sadang.or.kr', '서울 동작구 동작대로 89', 'sadang@faith.or.kr', '02-700-7000',
 '믿음의 기초를 세우는 건강한 교회입니다.', '주일 09:00, 수요 19:30', 37.476536, 126.981633, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 왕십리역
('왕십리열정교회', '이예준', 'http://wang.or.kr', '서울 성동구 왕십리로 300', 'wang@passion.or.kr', '02-800-8000',
 '열정적으로 예배하고 섬기는 공동체입니다.', '주일 13:00, 수요 20:00', 37.561468, 127.037102, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 여의도역
('여의도진리교회', '장하은', 'http://yeouido.or.kr', '서울 영등포구 여의대로 108', 'yeouido@truth.or.kr', '02-900-9000',
 '진리를 나누는 공동체입니다.', '주일 10:00, 목요예배 19:00', 37.521857, 126.924431, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE'),

-- 도곡역
('도곡샘물교회', '이샘', 'http://dogok.or.kr', '서울 강남구 도곡로 205', 'dogok@spring.or.kr', '02-111-2222',
 '샘물처럼 흘러넘치는 말씀의 공동체입니다.', '주일 09:30, 금요예배 19:30', 37.491129, 127.055091, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, 'ACTIVE');
