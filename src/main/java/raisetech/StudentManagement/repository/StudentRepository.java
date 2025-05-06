package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;

/**
 * 受講生テーブルと受講生テーブル情報テーブルと紐づくRepositoryです。
 */

@Mapper
public interface StudentRepository {

  /**
   *受講生の全件検索を行います。
   *
   * @return　受講生一覧（全件）
   */
  @Select("SELECT * FROM students")
  List<Student> search();

  /**
   * 受講生の検索を行います。
   * @param id 受講生ID
   * @return 受講生
   */

  @Select("SELECT * FROM students WHERE id = #{student_id}")
  Student searchStudent(String id);

  /**
   * 受講生のコース情報の全件検索を行います。
   *
   * @return 受講生のコース情報（全件）
   */

  @Select("SELECT * FROM students_courses")
  List<StudentCourse> searchStudentsCoursesList();

  /**
   * 受講生IDに紐づく受講生コース情報を検索します。
   * @param studentId 受講生ID
   * @return 受講生IDに紐づく受講生コース情報
   */

  @Select("SELECT * FROM students_courses WHERE student_id = #{studentId}")
  List<StudentCourse> searchStudentCourse(int studentId);

  /**
   * 受講生を新規登録します。IDに関しては自動採番を行う。
   * @param student 受講生
   */

  @Insert("INSERT INTO students( student_name, student_name_furigana, nickname, email, address, age, gender, remark, isDeleted) "
      + "VALUES (#{studentName}, #{studentNameFurigana}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);

  /**
   * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
   * @param studentCourse 受講生コース情報
   */

  @Insert("INSERT INTO students_courses(course_id, student_id, course_name, start_date, end_date) "
      + "VALUES (#{courseId}, #{studentId}, #{courseName}, #{startDate}, #{endDate})")
  @Options(useGeneratedKeys = true, keyProperty = "courseId")
  void registerStudentCourse(StudentCourse studentCourse);

  /**
   * 受講生を更新します。
   * @param student 受講生
   */
  @Update("UPDATE students SET student_name = #{studentName}, student_name_furigana = #{studentNameFurigana},"
    + " nickname = #{nickname}, email = #{email},address = #{address},age = #{age}, gender = #{gender}, "
    + "remark = #{remark}, isDeleted = #{isDeleted} WHERE id = #{id}")
void updateStudent(Student student);

  /**
   * 受講生コース情報のコース名を更新します。
   * @param studentCourse 受講生コース情報
   */

@Update("UPDATE students_courses SET course_name = #{courseName} WHERE course_id = #{courseId}")
void updateStudentCourses(StudentCourse studentCourse);
  }

