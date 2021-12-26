public class Advisor {

    private String name;

    public Advisor(String name) {
        this.name = name;

    }
    /** This method checks student's credit sufficiency, prerequisite of the course that the students wants to
     * take and the collision status. It calls the methods defined below to
     * perform the checks. */
    public void approveCourse(Student student, CourseSection courseSection) {
        if (student.getTranscript().calculateCredits() < courseSection.getCourse().getRequiredCredits()) { // Checks credits for only engineering projects
            student.setLogString("\nInsufficient credit");
            courseSection.setNumberOfCreditFail(courseSection.getNumberOfCreditFail()+1);
            courseSection.setFailedCredit(courseSection.getFailedCredit() + " " + student.getStudentID());
        }else if (courseSection.getCourse().getPrerequisite() != null && !student.isPassed(courseSection.getCourse().getPrerequisite())) {
            student.setLogString("\nNot approved " + courseSection.getCourseSectionCode() +
                    " because student failed prerequisite: " + courseSection.getCourse().getPrerequisite().getCourseCode());
            courseSection.setNumberOfPrereqFail(courseSection.getNumberOfPrereqFail()+1);
            courseSection.setFailedPrereq(courseSection.getFailedPrereq() + " " + student.getStudentID());
        }else if (student.getSchedule().isCollision(courseSection)) {
            student.setLogString("\nNot approved. Collision!");
            courseSection.setNumberOfCollisionFail(courseSection.getNumberOfCollisionFail()+1);
            courseSection.setFailedCollision(courseSection.getFailedCollision() + " " + student.getStudentID());
        }
        else {
            courseSection.addStudent(student);
        }
        //showStatistics();
    }

    public String getName () {
        return name;
    }


}