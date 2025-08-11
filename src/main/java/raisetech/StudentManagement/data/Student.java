package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * 受講生を扱うオブジェクト。
 */
@Schema(description = "受講生")
@Getter
@Setter

public class Student {

  /** 受講生ID */
  @Pattern(regexp = "^\\d+$", message = "数値のみ入力するようにしてください。")
  private String id;


  @NotBlank
  private String studentName;

  @NotBlank
  private String studentNameFurigana;

  @NotBlank
  private String nickname;

  @NotBlank
  @Email
  private String email;

  @NotBlank
  private String address;

  //@NotBlank
  //@Pattern(regexp = "^\\d+$")
  @Positive
  private int age;

  @NotBlank
  private String gender;

  private String remark;
  private boolean isDeleted;



}

/* @AssertTrue
*   boolean isXXX(age gender){
*  age != null && gender ! = null
*  true
*   }のように年齢と性別どちらもnullならエラーになるなどの規制を設定することもできる
 */