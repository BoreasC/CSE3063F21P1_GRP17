/**
 * This class is for creating unique IDs for students from departmnet code, registiration year and registiration order.
 */
public class StudentID {

    private Student student;
    private final String department = "1501"; // Only for computer engineering

    public StudentID(Student stu) {
        this.student = stu;
    }

    private String registrationYear() { // Finds reg year of students
        return String.valueOf((2021 - student.getYear()) % 100);
    }

    public String getStudentID() {
        return department + registrationYear() + String.format("%03d", student.getRegistrationOrder());
    }

    public String toString() {
        return getStudentID();
    }
}