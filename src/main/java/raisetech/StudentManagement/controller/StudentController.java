package raisetech.StudentManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.exception.TestException;
import raisetech.StudentManagement.service.StudentService;

@Validated
@RestController
public class StudentController {

  private StudentService service;

  /**
   * コンストラクタ
   *
   * @param service 受講生サービス
   */
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生詳細の一覧検索です。
   * 全権検索を行うので、条件指定は行わないものになります。
   * @return 受講生詳細一覧（全件）
   */
@Operation(summary = "一覧検索",description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  @GetMapping("/studentException")
  public StudentDetail StudentList() throws TestException {
   throw new TestException("現在このAPIは利用できません。URLは「studentList」ではなく「students」を利用してください。");
  }

  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   */
  @Operation(summary = "受講生検索",description = "IDに紐づく受講生を検索します。")
  @GetMapping("/student/{id}")
  public StudentDetail getStudent(
      @PathVariable @NotBlank @Pattern(regexp = "^\\d+$") String id) {
    return service.searchStudent(id);
  }

  /**
   * 条件を指定した受講生検索です。 受講生の任意の項目について条件を指定して検索できます。
   *
   * @param condition 検索条件（名前、年齢、コースなど）
   * @return 受講生
   */
  @Operation(summary = "条件指定の受講生検索", description = "指定した条件の受講生を検索します。")
  @GetMapping("/students/search")
  public List<StudentDetail> searchStudents(
      @Validated @ModelAttribute SearchStudentConditionDto condition) {
    if (condition.getStudentName() == null && condition.getStudentNameFurigana() == null
        && condition.getNickname() == null
        && condition.getEmail() == null && condition.getAddress() == null
        && condition.getAge() == null
        && condition.getGender() == null && condition.getIsDeleted() == null) {
      throw new SearchConditionMissingException("検索条件を1つ以上指定してください");
    }
    return service.searchStudentCondition(condition);
  }

   // @GetMapping("/studentConditionException")
  //  public StudentDetail searchConditionMissingException () {
   //   throw new SearchConditionMissingException("検索条件を1つ以上指定してください");
   // }



}
