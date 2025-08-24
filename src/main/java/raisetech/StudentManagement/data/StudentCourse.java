package raisetech.StudentManagement.data;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter

public class StudentCourse {
    private int courseId;
    private String studentId;
    private String courseName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
