package raisetech.StudentManagement.controller.converter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.StudentStatus;
import raisetech.StudentManagement.domain.CourseDetail;
import raisetech.StudentManagement.domain.StudentDetail;
public class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before() {
    sut = new StudentConverter();
  }

  @Test
  void 受講生のリストと受講生コース情報のリストを渡して受講生詳細のリストができること() {
    Student student = CreateStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("4000");
    studentCourse.setCourseId(4005);
    studentCourse.setCourseName("Design course");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));

    StudentStatus studentStatus = new StudentStatus();
    studentStatus.setStatusId(5);
    studentStatus.setCourseId(4005);
    studentStatus.setStatus("受講終了");

    CourseDetail courseDetail = new CourseDetail();
    courseDetail.setStudentCourse(studentCourse);
    courseDetail.setStudentStatus(studentStatus);

    List<Student> studentList = List.of(student);
    List<CourseDetail> courseDetailList = List.of(courseDetail);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, courseDetailList);

    CourseDetail actualDetail = actual.get(0).getCourseDetail().get(0);
    System.out.println("actual.size = " + actual.size());

    assertThat(actualDetail.getStudentCourse().getCourseId()).isEqualTo(studentCourse.getCourseId());
    assertThat(actualDetail.getStudentCourse().getCourseName()).isEqualTo(studentCourse.getCourseName());
    assertThat(actualDetail.getStudentStatus().getStatus()).isEqualTo(studentStatus.getStatus());


    }


  @Test
  void 受講生のリストと受講生コース情報のリストを渡したときに紐づかない受講生コース情報は除外されること() {
    Student student = CreateStudent();

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("2");
    studentCourse.setCourseId(1);
    studentCourse.setCourseName("Java コース");
    studentCourse.setStartDate(LocalDateTime.now());
    studentCourse.setEndDate(LocalDateTime.now().plusYears(1));

    StudentStatus studentStatus = new StudentStatus();
    studentStatus.setStatusId(2);
    studentStatus.setCourseId(1);
    studentStatus.setStatus("本申込");

    List<StudentCourse> studentCourseList = List.of(studentCourse);

    CourseDetail courseDetail = new CourseDetail();
    courseDetail.setStudentCourse(studentCourse);
    courseDetail.setStudentStatus(studentStatus);

    List<Student> studentList = List.of(student);
    List<CourseDetail> courseDetailList = List.of(courseDetail);

    //List<CourseDetail> courseDetailList = studentCourseList.stream()
      //  .map(sc -> new CourseDetail(sc, null)) // StudentStatus を使うならここで紐づけ
      //  .collect(Collectors.toList());

    List<StudentDetail> actual = sut.convertStudentDetails(studentList,courseDetailList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getCourseDetail()).isEmpty();

  }


  private static Student CreateStudent() {
    Student student = new Student();
    student.setId("1");
    student.setStudentName("江波 公史");
    student.setStudentNameFurigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setAge(36);
    student.setGender("男性");
    return student;
  }
}
