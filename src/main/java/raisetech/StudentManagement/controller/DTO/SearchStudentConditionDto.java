package raisetech.StudentManagement.controller.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class SearchStudentConditionDto {

  @Size(max = 50)
  private String studentName;

  @Size(max = 50)
  private String studentNameFurigana;

  @Size(max = 50)
  private String nickname;

  @Email
  private String email;

  @Size(max = 50)
  private String address;

  @Min(0)
  @Max(120)
  private Integer age;

  private String gender;
  private Boolean isDeleted;

}
