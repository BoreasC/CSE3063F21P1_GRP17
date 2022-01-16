from random import randrange

import Advisor
import Transcript
import WeeklySchedule
from PreviousCoursesGrade import PreviousCoursesGrade
from StudentID import StudentID


class Student(object):
    def __init__(self, name, year, registration_order, reg_controller):
        self.__name = name
        self.__year = year
        self.__reg_order = registration_order
        self.__reg_controller = reg_controller
        self.__grades = []
        self.__studentID = StudentID(self)
        self.__schedule = WeeklySchedule.WeeklySchedule(self)
        self.__transcript = Transcript.Transcript(self)
        self.__log = ""
        self.__taken_courses = []
        self.__advisor = Advisor.Advisor

    def get_previous_courses(self):
        previous_courses = []
        for prev_course_grade in self.__grades:
            previous_courses.append(prev_course_grade.course)
        return previous_courses

    def is_passed(self, course):
        for c in self.get_previous_courses():
            if course.course_code == c.course_code:
                return True
        return False

    def new_courses(self, course_section):
        self.__schedule.add_to_schedule(course_section)
        self.__taken_courses.append(course_section.course)

    def get_grade(self, course):
        prev_course_grade = randrange(51) + 50
        self.__grades.append(PreviousCoursesGrade(course, prev_course_grade))
        self.set_log("\n" + course.course_code + ": " + self.__grades[len(self.__grades) - 1].convert_letter_grade())

    def select_and_send_to_advisor(self):
        new_course_list = self.__reg_controller.create_new_course_list(self)
        self.set_log("\n\nCourse List: \n")
        for course_section in new_course_list:
            self.set_log(course_section.course_section_code + " ")
            self.log = "\n"
        for course_section in new_course_list:
            self.__advisor.approve_course(self, course_section)
        self.set_log("\n\nTaken Courses: \n")
        for course in self.__taken_courses:
            self.set_log(course.course_code + " ")

    def set_log(self, log_message):
        self.__log += log_message

    @property
    def advisor(self):
        return self.__advisor

    @advisor.setter
    def advisor(self, advisor):
        self.__advisor = advisor

    @property
    def year(self):
        return self.__year

    @property
    def transcript(self):
        return self.__transcript

    @property
    def student_id(self):
        return self.__studentID

    @property
    def name(self):
        return self.__name

    @property
    def grades(self):
        return self.__grades

    @grades.setter
    def grades(self, grade):
        self.__grades.append(grade)

    @property
    def log(self):
        return self.__log

    @log.setter
    def log(self, log):
        self.__log += log

    @property
    def schedule(self):
        return self.__schedule

    @property
    def reg_order(self):
        return self.__reg_order
