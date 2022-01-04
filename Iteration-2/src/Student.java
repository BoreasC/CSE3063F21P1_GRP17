import java.util.ArrayList;

/**
 * Student class includes some fields and methods related to the
 * student in our domain.
 *
 * @author canba
 *
 */
public class Student {

    private String name;
    private StudentID studentID;
    private int registrationOrder;
    private ArrayList<PreviousCourseGrade> grades;
    private ArrayList<Course> takenCourses;
    private int year;
    private Advisor advisor;
    private WeeklySchedule schedule;
    private Transcript transcript;
    private RegistrationController registrationController;
    private String logString = "";

    public Student(String name, int year, int registrationOrder, RegistrationController registrationController) {
        this.name = name;
        this.year = year;
        this.registrationOrder = registrationOrder;
        this.grades = new ArrayList<>();
        this.takenCourses = new ArrayList<>();
        this.studentID = new StudentID(this);
        this.registrationController = registrationController;
        this.transcript = new Transcript(this);
        this.schedule = new WeeklySchedule(this);
    }

    /**
     * This method loads previous course list
     *
     * @return This returns an arraylist of previous courses of the student.
     *
     */
    private ArrayList<Course> getPreviousCourses() { //
        ArrayList<Course> previousCourses = new ArrayList<>();
        for (PreviousCourseGrade g : grades) {
            previousCourses.add(g.getCourse());
        }
        return previousCourses;
    }

    public boolean isPassed(Course course) { // This method checks whether student passed to course or not
        ArrayList<Course> passedCourses = getPreviousCourses();

        for (Course c : passedCourses) {
            if (course.getCourseCode().equals(c.getCourseCode())) { // If the previousCourses list contains the course's code that means student passed the course and method returns true
                return true;
            }
        }
        return false;
    }

    public void newCourses(CourseSection courseSection) {
        schedule.addToSchedule(courseSection);
        takenCourses.add(courseSection.getCourse());
    }

    public void getGrade(Course course) { // This method randomly appoints a grade to a passed courses (Grade should be more than 50 according to the departments rules)
        int grade = (int) (Math.random() * 51) + 50;
        grades.add(new PreviousCourseGrade(course, grade));
        logString += "\n" + course.getCourseCode() + ": " + grades.get(grades.size() - 1).convertLetterGrade();
    }

    public void selectAndSendToAdvisor() {
        ArrayList<CourseSection> newCourseList = registrationController.createNewCourseList(this);
        setLogString("\n\nCourse List: \n");
        for (CourseSection c : newCourseList) {
            setLogString(c.getCourseSectionCode() + " ");
        }
        setLogString("\n");
        for (CourseSection c: newCourseList) {
            advisor.approveCourse(this, c);;
        }

        setLogString("\n\nTaken Courses: \n");
        for (Course c: takenCourses) {
            setLogString(c.getCourseCode() + " ");
        }
    }

    public String getLogString() {
        return logString;
    }

    public void setLogString(String logString) {
        this.logString += logString;
    }

    public String getName() {
        return name;
    }

    public StudentID getStudentID() {
        return studentID;
    }

    public ArrayList<PreviousCourseGrade> getGrades() {
        return grades;
    }

    public ArrayList<Course> getTakenCourses() {
        return takenCourses;
    }

    public int getYear() {
        return year;
    }

    public Advisor getAdvisor() {
        return advisor;
    }

    public void setAdvisor(Advisor advisor) {
        this.advisor = advisor;
    }

    public WeeklySchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(WeeklySchedule schedule) {
        this.schedule = schedule;
    }

    public Transcript getTranscript() {
        return transcript;
    }
    public int getRegistrationOrder() {
        return registrationOrder;
    }

    public void setRegistrationOrder(int registrationOrder) {
        this.registrationOrder = registrationOrder;
    }

    public void setYear(int year) {
        this.year = year;
    }

}