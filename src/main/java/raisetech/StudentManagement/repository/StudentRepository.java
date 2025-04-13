package raisetech.StudentManagement.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;

/**
 * 受講生情報を扱うリポジトリ。
 * 全件検索や単一条件での検索、コース情報の検索が行えるクラスです。
 */

@Mapper
public interface StudentRepository {

  /**
   *全件検索します。
   *
   * @return　全件検索した受講生情報の一覧
   */


  @Select("SELECT * FROM students")
  List<Student> search();

  @Select("SELECT * FROM students_courses")
  List<StudentsCourses> searchCourse();

  @Insert("INSERT INTO students( student_name, student_name_furigana, nickname, email, address, age, gender, remark, isDeleted) "
      + "VALUES (#{studentName}, #{studentNameFurigana}, #{nickname}, #{email}, #{address}, #{age}, #{gender}, #{remark}, false)")
  @Options(useGeneratedKeys = true,keyProperty = "id")
    void registerStudent(Student student);
  }

