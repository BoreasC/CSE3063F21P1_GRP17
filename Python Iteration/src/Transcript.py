class Transcript(object):
    def __init__(self, student):
        self.__student = student

    def calculate_credits(self):
        sum_of_credits = 0
        for prev_course_grade in self.__student.grades:
            sum_of_credits += prev_course_grade.course.credits
        return sum_of_credits

    def calculate_gpa(self):
        credit_multiply_grade = 0
        for prev_course_grade in self.__student.grades:
            if prev_course_grade.convert_letter_grade == "AA":
                credit_multiply_grade += prev_course_grade.course.credits * 4.0
            elif prev_course_grade.convert_letter_grade == "BA":
                credit_multiply_grade += prev_course_grade.course.credits * 3.5
            elif prev_course_grade.convert_letter_grade == "BB":
                credit_multiply_grade += prev_course_grade.course.credits * 3.0
            elif prev_course_grade.convert_letter_grade == "CB":
                credit_multiply_grade += prev_course_grade.course.credits * 2.5
            elif prev_course_grade.convert_letter_grade == "CC":
                credit_multiply_grade += prev_course_grade.course.credits * 2.0
            elif prev_course_grade.convert_letter_grade == "DC":
                credit_multiply_grade += prev_course_grade.course.credits * 1.5
            elif prev_course_grade.convert_letter_grade == "DD":
                credit_multiply_grade += prev_course_grade.course.credits * 1.0
            elif prev_course_grade.convert_letter_grade == "FF":
                credit_multiply_grade += prev_course_grade.course.credits * 0

        return credit_multiply_grade / self.calculate_credits()
