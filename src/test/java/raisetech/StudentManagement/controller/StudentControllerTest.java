package raisetech.StudentManagement.controller;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import raisetech.StudentManagement.data.Student;
import raisetech.StudentManagement.service.StudentService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索ができて空のリストが返ってくること() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk())
        .andExpect(content().json("[]"));

    verify(service, times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索ができて空のリストが返ってくること() throws Exception {
    String id = "999";
    mockMvc.perform(get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service, times(1)).searchStudent(id);
  }

  @Test
  void 条件指定した受講生詳細の検索ができて空のリストが返ってくること() throws Exception {
    when(service.searchStudentCondition(any())).thenReturn(Collections.emptyList());

    mockMvc.perform(get("/students/search")
        .param("studentName", "鱗滝"))

        .andExpect(status().isOk())
    .andExpect(content().json("[]"));

    verify(service, times(1)).searchStudentCondition(any());
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること()
      throws Exception{
    //リクエストデータは適切に構築して入力チェックの検証も兼ねている。
    //本来であれば返りは登録されたデータが入るが、モック化すると意味がないため、レスポンスは作らない。
    mockMvc.perform(post("/registerStudent").contentType(MediaType.APPLICATION_JSON).content(
        """
            {
            "student": {
            "studentName" : "江並康介",
            "studentNameFurigana"  : "エナミ",
            "nickname" : "コウジ",
            "email" : "test@example.com",
            "address" : "奈良県",
            "age" : 36,
            "gender" : "男性",
            "remark" : ""
            },
            "studentCourseList" : [
            {
                "courseName" : "Javaコース"
                }
                ]
                }
            """
    ))
        .andExpect(status().isOk());

    verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新が実行できて空で返ってくること() throws Exception {
    String json = """
    {
      "student": {
        "id": "358481",
        "studentName": "田中 雅子",
        "studentNameFurigana": "Tanaka Masako",
        "nickname": "まさちゃん",
        "email": "Masako@gmail.com",
        "address": "Saga",
        "age": 26,
        "gender": "女性",
        "remark": "",
        "deleted": false
      },
      "courseDetail": [
        {
          "studentCourse": {
            "courseId": 4007,
            "studentId": "358481",
            "courseName": "Java course",
            "startDate": "2025-08-11T00:00:00",
            "endDate": "2026-08-11T00:00:00"
          },
          "studentStatus": {
            "statusId": 8,
            "courseId": 4007,
            "status": "本申込"
          }
        }
      ]
    }
    """;

    mockMvc.perform(put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }


  @Test
  void 受講生詳細の例外APIが実行できてステータスが400で返ってくること() throws Exception  {
    mockMvc.perform(get("/studentException"))
        .andExpect(status().isBadRequest());
   // mockMvc.perform(get("/exception"))
     //   .andExpect(status().is4xxClientError())
       // .andExpect(content().string("このAPIは現在利用できません。古いURLとなっています。"));
  }

  @Test
  void 受講生を条件指定した際に条件がすべてnullなら例外が発生する() throws Exception {
    mockMvc.perform(get("/students/search"))
        .andExpect(status().isBadRequest());
  }


  @Test
  void 受講生詳細の受講生で適切な値を入力したときに入力チェックに異常が発生しないこと(){
    Student student = new Student();

    student.setId("1");
    student.setStudentName("江波 公史");
    student.setStudentNameFurigana("エナミコウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setAge(36);
    student.setGender("男性");


    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(0);
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いたときに入力チェックに掛かること(){
    Student student = new Student();

    student.setId("テストです。");
    student.setStudentName("江波 公史");
    student.setStudentNameFurigana("エナミ コウジ");
    student.setNickname("エナミ");
    student.setEmail("test@example.com");
    student.setAddress("奈良県");
    student.setAge(36);
    student.setGender("男性");


    Set<ConstraintViolation<Student>> violations = validator.validate(student);

    assertThat(violations.size()).isEqualTo(1);
    assertThat(violations).extracting("message")
        .containsOnly("数値のみ入力するようにしてください。");
  }
}