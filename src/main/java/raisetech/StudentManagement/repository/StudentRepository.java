package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import raisetech.StudentManagement.controller.DTO.SearchStudentConditionDto;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.data.StudentStatus;

/**
 * 受講生テーブルと受講生コース情報テーブルと受講生コース申し込み情報テーブルを紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

  /**
   *受講生の全件検索を行います。
   * &#064;return　受講生一覧（全件）
   */
  List<Student> search();

  /**
   * 受講生のコース情報の全件検索を行います。
   * @return 受講生のコース情報（全件）
   */
  List<StudentCourse> searchStudentsCoursesList();

  /**
   * 受講生の申し込み情報の全件検索を行います。
   * @return 受講生コース申し込み情報（全件）
   */
  List<StudentStatus> searchStudentStatusList();

  /**
   * 受講生の検索を行います。
   * @param id 受講生ID
   * @return 受講生
   */
  Student searchStudent(String id);

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */
  List<StudentCourse> searchStudentCourse(String studentId);

  /**
   * コースIDに紐づくコース申し込情報を検索します。
   *
   * @param courseId@return コースIDに紐づく申し込み情報
   */
  StudentStatus searchStudentStatus(Integer courseId);

  /**
   * 受講生の条件指定による検索を行います。
   *
   * @param condition 　受講生の項目
   * @return 受講生の情報
   */
  List<Student> searchStudentCondition(SearchStudentConditionDto condition);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   * @param student 受講生
   */
  void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   * @param studentCourse 受講生コース情報
   */
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生コース申し込み情報を新規登録します。IDに関しては自動採番を行う。
   * @param studentStatus 受講生コース申し込み情報
   */
  void registerStudentStatus(StudentStatus studentStatus);

  /**
   * 受講生を更新します。
   * @param student 受講生
   */
  void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   * @param studentCourse 受講生コース情報
   */
  void updateStudentCourses(StudentCourse studentCourse);

/**
 * 受講生コース申し込み情報を更新します。
 * @param studentStatus 受講生コース申し込み情報
 */
void updateStudentStatus (StudentStatus studentStatus);
  }