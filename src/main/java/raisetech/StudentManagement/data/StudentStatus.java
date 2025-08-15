package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講コース申し込み情報")
@Getter
@Setter

public class StudentStatus {
  private int statusId;
  private int courseId;
  private String status;
}
