/**
 * This class converts decimal grades to letter grades
 */
public class PreviousCourseGrade {

    private Course course;
    private int decimalGrade;

    public PreviousCourseGrade(Course course, int decimalGrade) {
        this.decimalGrade = decimalGrade;
        this.course = course;
    }

    public String convertLetterGrade() {  // This methods converts decimal grades to letter grades
        String letterGrade ;

        if (decimalGrade <= 44) {
            letterGrade =  "FF";
        }
        else if (decimalGrade > 44 && decimalGrade <=49) {
            letterGrade =  "FD";
        }
        else if (decimalGrade > 49 && decimalGrade <= 54) {
            letterGrade = "DD";
        }
        else if (decimalGrade > 54 && decimalGrade <= 64) {
            letterGrade = "DC";
        }
        else if (decimalGrade > 64 && decimalGrade <= 74) {
            letterGrade = "CC";
        }
        else if (decimalGrade > 74 && decimalGrade <= 79) {
            letterGrade = "CB";
        }
        else if (decimalGrade > 79 && decimalGrade <= 84) {
            letterGrade = "BB";
        }
        else if (decimalGrade > 84 && decimalGrade <= 89) {
            letterGrade = "BA";
        }
        else {
            letterGrade = "AA";
        }

        return letterGrade;
    }

    public Course getCourse() {
        return course;
    }

}