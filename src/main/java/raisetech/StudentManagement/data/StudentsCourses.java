package raisetech.StudentManagement.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class StudentsCourses {
    private int courseId;
    private int studentId;
    private String courseName;
    private String startDate;
    private String endDate;

}
