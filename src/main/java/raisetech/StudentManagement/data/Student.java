package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Student {
  private int id;
  private String studentName;
  private String studentNameFurigana;
  private String nickname;
  private String email;
  private String address;
  private int age;
  private String gender;
  private String remark;
  private boolean isDeleted;

}
