package raisetech.StudentManagement.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentCourse;
import raisetech.StudentManagement.domain.StudentDetail;

/**
 *
 * 受講生詳細を受講生や受講生コース情報、もしくはその逆の変換を行うコンバーターです。
 * コース詳細を受講生コース情報や受講生コース申し込み情報、もしくはその逆の変換を行うコンバーターです。
 */

@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報、受講生コース申し込み情報がまとまったコース情報をマッピングする。
   * 受講生コース情報は受講生に対して複数存在するのでループを回して受講生詳細情報を組み立てる。
   *
   * @param students       受講生一覧
   * @param courseDetails 受講生コース情報のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(
      List<Student> students,
      List<CourseDetail> courseDetails) {
    List<StudentDetail> studentDetails = new ArrayList<>();
    studentList.forEach(student -> {
      StudentDetail studentDetail = new StudentDetail();
      studentDetail.setStudent(student);

      List<CourseDetail> convertStudentCourses = courseDetails.stream()
          .filter(courseDetail ->
              Objects.equals(student.getId(), courseDetail.getStudentCourse().getStudentId())
          )
          .collect(Collectors.toList());

      studentDetail.setCourseDetail(convertStudentCourses);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }

  /**
   * 受講生コース情報に紐づく受講生コース申し込み情報をマッピングする。
   * 受講生コース申し込み情報は受講生に対して1：1の関係である。
   *
   * @param studentCourses　受講生コース情報
   * @param studentStatuses　受講生コース申し込み情報
   * @return　乗降性コース情報のリスト
   */

  public List<CourseDetail> convertCourseDetails(
      List<StudentCourse> studentCourses,
      List<StudentStatus> studentStatuses) {

    Map<Integer, StudentStatus> statusMap = studentStatuses.stream()
        .collect(Collectors.toMap(
            StudentStatus::getCourseId,
            status -> status,
            (existing, replacement) -> existing
        ));

    List<CourseDetail> courseDetails = studentCourses.stream()
        .map(studentCourse -> {
          StudentStatus status = statusMap.get(studentCourse.getCourseId());
          return new CourseDetail(studentCourse, status);
        })
        .collect(Collectors.toList());

    return courseDetails;
  }
}
