package raisetech.StudentManagement.controller;

/* 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。*/

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.service.StudentService;


@RestController
public class StudentController {

  /** 受講生サービス */
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

  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * 受講生検索です。
   * IDに紐づく任意の受講生の情報を取得します。
   *
   * @param id 受講生ID
   * @return 受講生
   */

  @GetMapping("/student/{id}")
  public StudentDetail getStudent(@PathVariable String id ){
    return service.searchStudent(id);
  }

  /* 受講内で削除されたメソッド
  * @GetMapping("/newStudent")
  * public String newStudent(Model model) {
    * StudentDetail studentDetail = new StudentDetail();
    * studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    * model.addAttribute("studentDetail", studentDetail);
    * return "registerStudent";
   *}
   */

  /**
   * 受講生詳細の登録を行います。
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody StudentDetail studentDetail) {
    StudentDetail responseStudentDetail1 = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail1);
  }

  /**
   * 受講生詳細の更新を行います。キャンセルフラグの更新もここで行います。（論理削除）
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */

  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
//①新規受講生情報を登録する処理を実装する→登録した受講生情報がstudentList画面に表示されるようにする

//②コース情報も一緒に登録できるように実装する。コースは単体でいい。
// (最初は1つのコースで始める人が多い。コース情報の追加は更新作業にあたる)