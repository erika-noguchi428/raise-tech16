package raisetech.StudentManagement.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import raisetech.StudentManagement.controller.DTO.SearchStudentConditionDto;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.StudentStatus;
import raisetech.StudentManagement.domain.CourseDetail;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;
import java.util.function.Function;

/**
 * 受講生情報を取り扱うサービスです。
 * 受講生の検索や登録・更新を行います。
 */
@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }


  /**
   * 受講生詳細の一覧検索です。
   * 全件検索を行うので、条件指定は行わないものになります。
   * @return 受講生詳細一覧（全件）
   */
  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentsCoursesList();
    List<StudentStatus> studentStatusList = repository.searchStudentStatusList();

    Map<Integer, StudentStatus> statusMap = studentStatusList.stream()
        .collect(Collectors.toMap(StudentStatus::getCourseId, Function.identity()));

    List<CourseDetail> courseDetails = studentCourseList.stream()
        .map(studentCourse -> {
          CourseDetail courseDetail = new CourseDetail();
          courseDetail.setStudentCourse(studentCourse);
          courseDetail.setStudentStatus(statusMap.get(studentCourse.getCourseId()));
          return courseDetail;
        })
        .collect(Collectors.toList());
    return converter.convertStudentDetails(studentList, courseDetails);
  }


  /**
   * 受講生詳細の検索です。
   * IDに紐づく受講生詳細情報を取得した後、その受講生に紐づく受講生コース、受講生コース申し込み情報
   *
   * @param id 受講生ID
   * @return 受講生詳細
   */
  public StudentDetail searchStudent(String id) {

      // ① Student を取得
      Student student = repository.searchStudent(id);

      // ② StudentCourse を取得
      List<StudentCourse> studentCourses = repository.searchStudentCourse(student.getId());

    List<Integer> courseIds = studentCourses.stream()
        .map(StudentCourse::getCourseId)
        .toList();

    List<StudentStatus> studentStatuses = courseIds.stream()
        .map(repository::searchStudentStatus)
        .collect(Collectors.toList());

    // ⑤ StudentCourse と StudentStatus を紐づけて CourseDetail を構築（コンバーター使用）
      List<CourseDetail> courseDetails = converter.convertCourseDetails(studentCourses, studentStatuses);

      // ⑥ StudentDetail にまとめて返却
      return new StudentDetail(student, courseDetails);
    }


    //受講生を条件指定した検索を行います。
    //①コントローラーから検索条件をうけとる
  //項目の条件に合っているかチェックする機能
  //②リポジトリ→xmlに渡して、項目に一致する受講生の情報をとってくる
  //とってきた受講生のIDに紐づくコースっ情報と申し込み情報をとってくる
// ↓ studentsの中のIDだけを抽出して


  public List<StudentDetail> searchStudentCondition(SearchStudentConditionDto condition) {

    // 条件に合う学生一覧を取得
    List<Student> studentCondition = repository.searchStudentCondition(condition);

    // 学生ID一覧を抽出
    List<String> studentIds = studentCondition.stream()
        .map(Student::getId)
        .toList();

    List<StudentCourse> studentCourses = studentIds.stream()
        .flatMap(id -> repository.searchStudentCourse(id).stream())
        .toList();

    List<Integer> courseIds = studentCourses.stream()
        .map(StudentCourse::getCourseId)
        .toList();

    List<StudentStatus> studentStatuses = courseIds.stream()
        .map(repository::searchStudentStatus)
        .collect(Collectors.toList());

    List<StudentDetail> studentDetails = studentCondition.stream()
        .map(student -> {
          List<StudentCourse> coursesForStudent = studentCourses.stream()
              .filter(studentCourse -> studentCourse.getStudentId().equals(student.getId()))
              .toList();

    // ⑤ StudentCourse と StudentStatus を紐づけて CourseDetail を構築（コンバーター使用）
    List<CourseDetail> courseDetails = converter.convertCourseDetails(coursesForStudent, studentStatuses);

    return new StudentDetail(student, courseDetails);
  })
      .toList();
    // ⑥ StudentDetail にまとめて返却
    return studentDetails;
  }


  /**
   * 受講生詳細の登録を行います。
   * 受講生と受講生コース情報と受講生コース申し込み情報を個別に登録し、受講生コース情報には受講生情報を紐づける値とコース開始日、コース終了日を設定します。
   * 受講生コース申し込み情報には、受講生コースと紐づけるようにする。
   * @param studentDetail 受講生詳細
   * @return 登録情報を付与した受講生詳細
   */
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    //準備
    Student student = studentDetail.getStudent();

    //やりたいことをやる
    repository.registerStudent(student);


        for (CourseDetail courseDetail : studentDetail.getCourseDetail()) {
          StudentCourse studentCourse = courseDetail.getStudentCourse();
          initStudentsCourse(studentCourse, student);
          repository.registerStudentCourse(studentCourse);

          StudentStatus studentStatus = courseDetail.getStudentStatus();
          initStudentStatus(studentStatus, studentCourse);
          repository.registerStudentStatus(studentStatus);
        }
    return studentDetail;
  }


  /**
   *受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourses 受講生コース情報
   * @param              student 受講生
   */
   void initStudentsCourse(StudentCourse studentCourses, Student student) {
    LocalDateTime now = LocalDateTime.now();
    studentCourses.setStudentId(student.getId());
    studentCourses.setStartDate (now);
    studentCourses.setEndDate(now.plusYears(1));
  }


  /**
   *受講生コース情報を登録する際の初期情報を設定する。
   *
   * @param studentCourses 受講生コース情報
   * @param              studentStatus コース状況
   */
  void initStudentStatus(StudentStatus studentStatus, StudentCourse studentCourses) {
    studentStatus.setCourseId(studentCourses.getCourseId());
  }


  /**
   * 受講生詳細の更新を行います。
   * 受講生と受講生コース情報をそれぞれ更新します。
   */
  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    System.out.println("Student: " + studentDetail.getStudent());

    repository.updateStudent(studentDetail.getStudent());

    for (CourseDetail courseDetail : studentDetail.getCourseDetail()) {
      StudentCourse studentCourse = courseDetail.getStudentCourse();
      //initStudentsCourse(studentCourse, student);
      repository.updateStudentCourses(studentCourse);

      StudentStatus studentStatus = courseDetail.getStudentStatus();
      repository.updateStudentStatus(studentStatus);
      System.out.println("StudentStatus: statusId=" + studentStatus.getStatusId()
          + ", courseId=" + studentStatus.getCourseId()
          + ", status=" + studentStatus.getStatus());
  }
  }
}
