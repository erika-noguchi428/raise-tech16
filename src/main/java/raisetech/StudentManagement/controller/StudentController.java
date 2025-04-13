package raisetech.StudentManagement.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import raisetech.StudentManagement.controller.converter.StudentConverter;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.data.StudentsCourses;
import raisetech.StudentManagement.domain.StudentDetail;
import raisetech.StudentManagement.repository.StudentRepository;
import raisetech.StudentManagement.service.StudentService;

@Controller
public class StudentController {

  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter) {
    this.service = service;
    this.converter = converter;
  }

  @GetMapping("/studentList")
  public String getStudentList (Model model) {   //リクエストの加工処理、入力チェックとか
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses = service.searchStudentCourseList();

    model.addAttribute("studentList",converter.convertStudentDetails(students,studentsCourses));
    return "studentList"; //このstudentListは35行目と合わせるのではなく、htmlのstudentListを入れている。
  }

  @GetMapping("/studentCoursesList")
  public List<StudentsCourses> getStudentCourseList(){
    return service.searchStudentCourseList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model){
    model.addAttribute("studentDetail" , new StudentDetail());
    return "registerStudent";
  }

@PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result) {
    if(result.hasErrors()){
      return "registerStudent";
    }
    service.registerStudent(studentDetail);
    System.out.println(studentDetail.getStudent().getStudentName() + "さんが新規受講生として登録されました。");
    return "redirect:/studentList";
}

}
//①新規受講生情報を登録する処理を実装する→登録した受講生情報がstudentList画面に表示されるようにする

//②コース情報も一緒に登録できるように実装する。コースは単体でいい。
// (最初は1つのコースで始める人が多い。コース情報の追加は更新作業にあたる)