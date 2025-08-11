package raisetech.StudentManagement.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.StudentStatus;
import raisetech.StudentManagement.domain.CourseDetail;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before() {
    sut = new StudentService(repository, converter);
  }


  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {

    // モックデータの作成
    Student student = new Student();
    student.setId("1");

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setStudentId("1");
    studentCourse.setCourseId(1000);

    StudentStatus studentStatus = new StudentStatus();
    studentStatus.setCourseId(1000);
    studentStatus.setStatus("本申込");

    //Mockito
    //事前準備
    StudentService sut = new StudentService(repository, converter);

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);
    List<StudentStatus> studentStatusList = List.of(studentStatus);

    // CourseDetail を構築（studentCourse と studentStatus を紐づけ）
    CourseDetail courseDetail = new CourseDetail(studentCourse, studentStatus);
    List<CourseDetail> courseDetails = List.of(courseDetail);


    // モック設定
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentsCoursesList()).thenReturn(studentCourseList);
    when(repository.searchStudentStatusList()).thenReturn(studentStatusList);

    // 実行
    sut.searchStudentList();

    // 検証
    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentsCoursesList();
    verify(repository, times(1)).searchStudentStatusList();
    verify(converter, times(1)).convertStudentDetails(studentList, courseDetails);
  }


  @Test
  void 受講生詳細の検索_リポジトリの処理が適切に呼び出せていること(){
    String id = "999";
    Student student = new Student();
    student.setId("id");
    when(repository.searchStudent("id")).thenReturn(student);
    when(repository.searchStudentCourse("id")).thenReturn(new ArrayList<>());

    StudentDetail expected = new StudentDetail(student, new ArrayList<>());

    StudentDetail actual = sut.searchStudent("id");

    verify(repository, times(1)).searchStudent("id");
    verify(repository, times(1)).searchStudentCourse("id");
    assertEquals(expected.getStudent().getId(), actual.getStudent().getId());
  }

  @Test
  void 受講生詳細の検索_受講コースとステータスが正しくCourseDetailに変換されていること() {
    // モックデータの準備
    Student student = new Student();
    student.setId("id");

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setCourseId(1000);

    StudentStatus studentStatus = new StudentStatus();
    studentStatus.setCourseId(1000);
    studentStatus.setStatus("仮申込");


    CourseDetail courseDetail = new CourseDetail(studentCourse, studentStatus);

    // モックの設定
    when(repository.searchStudent("id")).thenReturn(student);
    when(repository.searchStudentCourse("id")).thenReturn(Arrays.asList(studentCourse));
    when(repository.searchStudentStatus(1000)).thenReturn(studentStatus);

    when(converter.convertCourseDetails(List.of(studentCourse), List.of(studentStatus))).thenReturn(List.of(courseDetail));

    // 実行
    StudentDetail actual = sut.searchStudent("id");

    StudentCourse actualCourse = actual.getCourseDetail().get(0).getStudentCourse();
    assertEquals(1000, actualCourse.getCourseId());
    assertEquals("仮申込", actual.getCourseDetail().get(0).getStudentStatus().getStatus());
  }


  @Test
  void 受講生詳細の登録_リポジトリの処理が適切に呼び出せていること(){
      Student student = new Student();
      StudentCourse studentCourse = new StudentCourse();
      StudentStatus studentStatus = new StudentStatus();

    CourseDetail courseDetail = new CourseDetail(studentCourse, studentStatus);
    List<CourseDetail> courseDetailList = List.of(courseDetail);
    StudentDetail studentDetail = new StudentDetail(student, courseDetailList);

      sut.registerStudent(studentDetail);

      verify(repository, times(1)).registerStudent(student);
      verify(repository, times(1)).registerStudentCourse(studentCourse);
      verify(repository, times(1)).registerStudentStatus(studentStatus);
  }

  @Test
  void 受講生詳細の登録_初期化処理が行われること(){
    String id = "999";
    int course_id = 1000;
    Student student = new Student();
    student.setId(id);
    StudentCourse studentCourse =new StudentCourse();
    studentCourse.setCourseId(course_id);

    sut.initStudentsCourse(studentCourse, student);

    assertEquals(id, studentCourse.getStudentId());
    assertEquals(LocalDateTime.now().getHour(),
        studentCourse.getStartDate().getHour());
    assertEquals(LocalDateTime.now().plusYears(1).getYear(),
        studentCourse.getEndDate().getYear());


    StudentStatus studentStatus =new StudentStatus();

    sut.initStudentStatus(studentStatus, studentCourse);

    assertEquals(course_id, studentStatus.getCourseId());
  }

  @Test
  void 受講生詳細の更新_リポジトリの処理が適切に呼び出せていること(){
    Student student = new Student();
    StudentCourse studentCourse = new StudentCourse();
    StudentStatus studentStatus = new StudentStatus();

    CourseDetail courseDetail = new CourseDetail(studentCourse, studentStatus);
    List<CourseDetail> courseDetailList = List.of(courseDetail);
    StudentDetail studentDetail = new StudentDetail(student, courseDetailList);

    sut.updateStudent(studentDetail);

    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourses(studentCourse);
    verify(repository, times(1)).updateStudentStatus(studentStatus);
  }
}
