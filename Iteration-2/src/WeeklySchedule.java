import java.util.ArrayList;

public class WeeklySchedule {

    private CourseSection[][] stuSchedule = new CourseSection[5][8];
    private Student student;

    public WeeklySchedule(Student student) {
        this.student = student;
    }

    /**Takes a courseSection as argument and adds it to
     * the schedule by taking its courseSchedule into consideration*/
    public void addToSchedule(CourseSection courseSection) {
        boolean[][] courseSchedule = courseSection.getCourseSchedule();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {

                if (courseSchedule[i][j]) {
                    stuSchedule[i][j] = courseSection;
                }
            }
        }
    }

    /**Takes a course section as argument and compares it with
     * current schedule, if there is more than 1 hour collision between
     * courseSchedule and current schedule returns true, otherwise false */
    public boolean isCollision(CourseSection courseSection) {
        boolean[][] courseSchedule = courseSection.getCourseSchedule();
        int collidedHours = 0; // total num of collided hours
        ArrayList<CourseSection> collidedSections = new ArrayList<>();


        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 8; j++) {

                // If courseSchedule and schedule has lectures in the same hour
                if (courseSchedule[i][j] && stuSchedule[i][j] != null) {
                    collidedSections.add(stuSchedule[i][j]);
                    collidedHours++;
                }
            }
        }

        if (collidedHours > 1) {
            student.setLogString("\nAdvisor didn't approve " + courseSection.getCourseSectionCode() +
                    " because of more than one hour collision with -> ");
            for (CourseSection c: collidedSections) {
                student.setLogString(c.getCourseSectionCode() + " ");
            }
            student.setLogString(" in schedule");
        }

        return collidedHours > 1; // Return true if collided hours is greater than one, false otherwise.

    }

}