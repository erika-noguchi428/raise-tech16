CREATE TABLE IF NOT EXISTS students
(
  id int PRIMARY KEY AUTO_INCREMENT,
  student_name varchar(255) NOT NULL,
  student_name_furigana varchar(255) NOT NULL,
  nickname varchar(100),
  email varchar(255) NOT NULL UNIQUE,
  address varchar(255),
  age int NOT NULL,
  gender VARCHAR(10) NOT NULL,
  remark VARCHAR(255),
   isDeleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS students_courses
(
  course_id int PRIMARY KEY,
  student_id int,
  course_name varchar(255) NOT NULL,
  start_date date NOT NULL,
  end_date date,
  FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE
);

CREATE TABLE courses_status (
  status_id INT PRIMARY KEY AUTO_INCREMENT,
  course_id INT,
  status VARCHAR(255)
);