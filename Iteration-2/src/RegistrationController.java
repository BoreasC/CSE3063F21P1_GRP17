import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RegistrationController {

    private String semester;
    private int year;
    private int currentYear;
    private ArrayList<Course> courses = new ArrayList<>(); //List to hold courses objects
    private ArrayList<CourseSection> courseSections = new ArrayList<>();
    private int numberOfStudents;
    private int numberOfAdvisors;
    private ArrayList<Advisor> advisors = new ArrayList<>(); // List to holds advisor objects
    private ArrayList<Student> students = new ArrayList<>(); // List to holds student objects

    public  RegistrationController( ) throws IOException, ParseException {
        startRegistration();
    }

    private void startRegistration() {

        try {
            JSONParser parser = new JSONParser(); //Creates json parser object to parse input.json file
            JSONObject input = (JSONObject) parser.parse(new FileReader("Iteration-2//src//input.json"));
            int numberOfAdvisors = Integer.parseInt((String)input.get("Advisors")); // Reads total number of advisor from input file
            setNumberOfAdvisors(numberOfAdvisors); // Pass advisor number to its setter method
            int numberOfStudents = Integer.parseInt((String)input.get("Students")); // Reads total number of students from input file
            setNumberOfStudents(numberOfStudents); // Pass student number to its setter method
            currentYear = Integer.parseInt((String)input.get("CurrentYear"));
            semester = (String)input.get("CurrentSemester"); // Reads current semester value from input
            JSONArray inputCourses = (JSONArray) input.get("Courses");  // Reads courses from json file to json array object

            for(Object c: inputCourses) { // For each course, we read every attributes of course to seperate variables
                JSONObject course = (JSONObject) c;
                String courseCode = (String) course.get("courseCode");
                String courseName = (String) course.get("courseName");
                String courseSemester = (String) course.get("semester");
                int credits = Integer.parseInt((String) course.get("credits"));
                int theoretical = Integer.parseInt((String) course.get("theoretical")); // Reads theoric hours of courses
                int lab = Integer.parseInt((String) course.get("lab")); // Reads lab hours of courses
                double passingCourseProb = Double.parseDouble((String)course.get("PassProbability"));
                int quota = Integer.parseInt((String)course.get("Quota"));
                year = Integer.parseInt((String) course.get("year"));
                int requiredCredits = Integer.parseInt((String) course.get("requiredCredits")); // For engineering project
                String preRequisite = (String) course.get("preRequisites");
                Course newCourse = new Course(courseCode, courseName, courseSemester, quota, credits, theoretical,
                        lab, year, requiredCredits, findCourse(preRequisite), passingCourseProb); // Creates courses objects with the data from input.json file
                courses.add(newCourse); // Add every new course object to courses list
                courseSections.add(new CourseSection(newCourse)); // Creates coursection objects within the coursesections list
            }
            createAdvisors(); // After we finished with reading course section then we call the advisor initilazior
        }
        catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private ArrayList<String> getNamesList() {
        ArrayList<String> names = new ArrayList<>();
        try {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(new FileReader("Iteration-2//src//names.json"));
            JSONObject nameJson =  (JSONObject) obj;
            var name = (JSONArray) nameJson.get("names");
            Iterator<?> i = name.iterator();
            while (i.hasNext()) {
                names.add((String) i.next());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ParseException e) {
            e.printStackTrace();
        }
        return names;
    }


    private void createAdvisors()  {
        for (int i = 0; i < numberOfAdvisors; i++) {
            String advisorName =getNamesList().get((int) (Math.random() * getNamesList().size() - 1)); // Randomly assign a name to advisor  
            advisors.add(new Advisor(advisorName)); // Creates advisor objects and adds them to corresponding list
        }
        createStudents(); // Calls students initilazior
    }


    private void createStudents()  {
        for(int year = 1; year <= 4; year++){
            for(int rank = 1; rank <= numberOfStudents / 4; rank++){
                String studentName = getNamesList().get((int) (Math.random() * getNamesList().size() - 1)); // Randomly assign a name to students
                students.add(new Student(studentName, year, rank, this)); // Creates students  ?????????????????
            }
        }
        assignAdvisorsToStudents(); // Calls advisor-students appointer
    }

    private void assignAdvisorsToStudents() { // This method appoints an advisor for every student
        for (Student s: students) {
            int i = (int) (Math.random() * advisors.size()); // Appoints advisors to students randomly
            s.setAdvisor(advisors.get(i));
            s.setLogString("Advisor: " + advisors.get(i).getName() + " \n\n"); // Prints students advisor to console
        }
        addPreviousCourses();
    }

    private void addPreviousCourses() { // This methods adds previous courses of students who failed or passed to the previo
        ArrayList<Course> previousCourses = new ArrayList<>();

        for(Course c : courses){
            if ((c.getYear() <= currentYear && c.getSemester().equals("fall")) || (c.getYear() < currentYear && c.getSemester().equals("spring"))) { // We currently runs program for spring semester of first year, therefore fall semester's courses are previous courses to students
                previousCourses.add(c); // Adds all previous courses to the list
            }
        }

        for (Student s : students) {                // In this section, for every previous course, we check whether students passed to course or not
            s.setLogString("Passed Courses: ");
            for (Course c: previousCourses) {
                if (Math.random() < c.getPassingCourseProb() && c.getYear() <= s.getYear()) { // Every course has its own pass probability and checks whether this course's year is suitable for the student
                    s.getGrade(c); // If course was passed by student then we add the course to the list
                }
            }
        }
        selectCourses();
    }

    private void selectCourses() {
        for (Student stu: students) {
            stu.selectAndSendToAdvisor();
        }
        showOutput();
    }

    private void showOutput() {
        // Prints students informations to the console
        for (Student std : students) {
            if(std.getYear()==currentYear){
                System.out.println("" + std.getName());
                System.out.println("\nStudent ID: " + std.getStudentID().getStudentID());
                System.out.println("Student's year: " + std.getYear());
                System.out.println("Student's GPA: " + std.getTranscript().calculateGPA());
                System.out.println(std.getLogString()); // Prints buffer from student class
                System.out.println("\n\n\n\n\n");
            }
        }
        // After that, creates a json file for each students and prints student's logStrings into this files
        for (Student std: students) {
            JSONObject studentJson = new JSONObject();
            studentJson.put("Registration: ", std.getLogString());
            JSONArray jsonList = new JSONArray();
            jsonList.add(studentJson);

            try (FileWriter file = new FileWriter(new File( "Iteration-2//Outputs//" + std.getStudentID().getStudentID() +  ".json"))) {
                file.write(jsonList.toJSONString());
                file.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        showStatistics();
    }

    private void showStatistics(){
        String buffer="";
        for(CourseSection cs: courseSections){
            if(cs.getCourse().getSemester().equals(semester) && cs.getCourse().getYear()==currentYear){
                buffer += "\n" + cs.getCourseSectionCode() + " Statistics: " + "\n";;
                buffer += cs.getNumberOfCollisionFail() + " students couldn't register due to collision problem." + cs.getFailedCollision() + " \n";
                buffer += cs.getNumberOfQuotaFail() + " students couldn't register due to quota problem." + cs.getFailedQuota() + " \n";
                buffer += cs.getNumberOfCreditFail() + " students couldn't register due to total taken credit problem." + cs.getFailedCredit() + " \n";
                buffer += cs.getNumberOfPrereqFail() + " students couldn't register due to prerequisite problem." + cs.getFailedPrereq() + " \n";
            }
        }
        System.out.println(buffer);

        JSONObject statJson = new JSONObject();
        statJson.put("Courses Statistics", buffer);
        JSONArray statList = new JSONArray();
        statList.add(statJson);

        try (FileWriter file = new FileWriter(new File("Iteration-2//Outputs//Statistics.json"))) {
            file.write(statList.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CourseSection> createNewCourseList(Student student) { // This method returns offered course lists if the course is passed by the student and course's semester is current semester
        ArrayList<CourseSection> newCourseList = new ArrayList<>();
        for (CourseSection c: courseSections) {
            if (!student.isPassed(c.getCourse()) && c.getCourse().getYear() <= student.getYear()) {
                newCourseList.add(c);
            }
        }
        return newCourseList;
    }

    private  Course findCourse(String courseCode) { // This method searches for find prerequisite course of a course, if any
        for (Course c: courses) {
            if (c.getCourseCode().equals(courseCode)) {
                return c;
            }
        }
        return null;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
    }

    public void setNumberOfAdvisors(int numberOfAdvisors) {
        this.numberOfAdvisors = numberOfAdvisors;
    }
}