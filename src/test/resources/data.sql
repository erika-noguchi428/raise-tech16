INSERT INTO students (id, student_name, student_name_furigana, nickname, email, address, age, gender)
VALUES('1001', '山田　愛', 'Ai Yamada', 'あいちゃん', 'ai@gmail.com', 'Niigata', 20, '女性'),
('2003', '伊藤　哲也', 'Ito Tetuya', 'てつ坊', 'tetu@gmail.com', 'Ehime', 38, '男性'),
('2005', '山川　麗子', 'Yamakawa Reiko', '麗子さん', 'reiko@gmail.com', 'Kanagawa', 28, '女性'),
('4005', '原田　裕也', 'Harada Yuuya', 'ゆっちゃ', 'yuuya@gmail.com', 'Fukushima', 40, '男性'),
('358479', '田中 美咲', 'Tanaka Misaki', 'みさちゃん', 'Misaki@gmail.com', 'Shiga', 24, '女性');

INSERT INTO students_courses (course_id, student_id, course_name, start_date, end_date)
VALUES(1000, 1001, 'Java course', '2024-12-25', '2025-02-03'),
      (4000, 4005, 'Design course', '2025-02-13', '2025-04-01'),
      (4001, 358479, 'Aws course', '2025-05-05', '2026-05-05'),
      (4002, 358479, 'Java course', '2025-05-06', '2026-05-06');
