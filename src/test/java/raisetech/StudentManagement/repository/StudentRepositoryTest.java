package raisetech.StudentManagement.repository;


import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.StudentStatus;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること() {
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(5);
  }

  @Test
  void 受講生コースリストの全件検索が行えること() {
    List<StudentCourse> actualCourse = sut.searchStudentsCoursesList();
    assertThat(actualCourse.size()).isEqualTo(4);
  }

  @Test
  void 受講生申し込み状況の全権検索が行えること() {
    List<StudentStatus> actualStatus = sut.searchStudentStatusList();
    assertThat(actualStatus.size()).isEqualTo(3);
  }

  @Test
  void 受講生の登録が行えること() {
    Student student = new Student();
    student.setId("1");
    student.setStudentName("江波 公史");
    student.setStudentNameFurigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setAge(36);
    student.setGender("男性");
    student.setRemark("");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(6);
  }

  @Test
  void 受講生コース情報の登録が行えること() {
    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId(1005);
   studentCourse.setStudentId("2005");
   studentCourse.setCourseName("Java course");
   studentCourse.setStartDate(LocalDateTime.of(2024, 11,25,0,0));
   studentCourse.setEndDate(LocalDateTime.of(2025, 11,25,0,0));

    sut.registerStudentCourse(studentCourse);

    List<StudentCourse> actualCourse = sut.searchStudentsCoursesList();
    assertThat(actualCourse.size()).isEqualTo(5);
  }

  @Test
  void 受講生コース申し込み状況の登録が行えること() {
    StudentStatus studentStatus = new StudentStatus();
    studentStatus.setStatusId(1);
    studentStatus.setCourseId(1005);
    studentStatus.setStatus("本申込");

    sut.registerStudentStatus(studentStatus);

    List<StudentStatus> actualStatus = sut.searchStudentStatusList();
    assertThat(actualStatus.size()).isEqualTo(4);

  }
}