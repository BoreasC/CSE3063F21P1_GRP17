import json
import random
from Course import Course
from CourseSection import CourseSection
from Advisor import Advisor
from Student import Student


class RegistrationController(object):
    def __init__(self):
        self.__advisors = []
        self.__students = []
        self.__courses = []
        self.__course_sections = []
        self.__student_number = 0
        self.__advisor_number = 0
        self.__advisor_number = 0
        self.__current_semester = 0
        self.__current_year = 0
        self.__names = []
        self.start_registration()

    def start_registration(self):
        with open("src\input.json", encoding="utf8") as json_file:
            data = json.load(json_file)
            self.__advisor_number = int(data['Advisors'])
            self.__student_number = int(data['Students'])
            self.__current_year = int(data['CurrentYear'])
            self.__current_semester = data['CurrentSemester']
            course_infos = data['Courses']

            for i in course_infos:
                course_code = i['courseCode']
                course_name = i['courseName']
                semester = i['semester']
                course_type = i['courseType']
                credits = i['credits']
                theoretic = int(i['theoretical'])
                lab = int(i['lab'])
                pass_prob = i['PassProbability']
                quota = i['Quota']
                year = i['year']
                required_credits = i['requiredCredits']
                prerequisites = i['preRequisites']

                new_course = Course(course_code, course_name, semester, quota, credits,
                                    theoretic, lab, year, required_credits, self.check_preq(prerequisites), pass_prob)
                self.__courses.append(new_course)
                self.__course_sections.append(CourseSection(new_course))

        self.create_advisor()

    def get_names_list(self):
        nameslist = []
        with open('src//names.json', 'r', encoding="utf8") as json_file:
            data = json.load(json_file)
            temp = data["names"]
        for i in temp:
            nameslist.append(i)

        return nameslist

    def create_advisor(self):
        for i in range(0, self.__advisor_number):
            advisor_name = self.get_names_list()[(random.randint(0, (len(self.get_names_list()) - 1)))]
            self.__advisors.append(Advisor(advisor_name))
        self.create_student()

    def create_student(self):
        for year in range(1, 5):
            for rank in range(1, 71):
                student_name = self.get_names_list()[(random.randint(0, (len(self.get_names_list()) - 1)))]
                self.__students.append((Student(student_name, year, rank, self)))
        self.assign_advisors_to_students()

    def assign_advisors_to_students(self):
        for student in self.__students:
            i = random.randint(0, (len(self.__advisors)) - 1)
            student.advisor = self.__advisors[i]
            student.set_log('Advisor Name: ' + self.__advisors[i].name + " \n\n")
        self.add_previous_courses()

    def add_previous_courses(self):
        previous_courses = []
        for course in self.__courses:
            if ((course.year <= self.__current_year and course.semester == "fall") or
                    (course.year < self.__current_year and course.semester == "spring")):
                previous_courses.append(course)

        for student in self.__students:
            student.set_log("Passed Courses:")
            for course in previous_courses:
                if random.random() < course.pass_prob and course.year <= student.year:
                    student.get_grade(course)
        self.select_courses()

    def select_courses(self):
        for student in self.__students:
            student.select_and_send_to_advisor()
        self.show_output()

    def show_output(self):
        for student in self.__students:
            if student.year == self.__current_year:
                print("Student ID: " + str(student.student_id))
                print("" + student.name + "\n")
                print("Student's year: " + str(student.year))
                print("Student's GPA: " + str(student.transcript.calculate_gpa()))
                print(student.log)
                print("-----------------------------------------------------------------")
        # json filea basılacaklar

    def show_statistics(self):
        buffer = ""
        for cs in self.__course_sections:
            if cs.course.semester == self.__current_semester and cs.course.year == self.__current_year:
                buffer += "\n" + cs.course_section_code + " Statistics: " + "\n"
                buffer += str(cs.num_of_collision_fail) + " students couldn't register due to collision problem."
                buffer += str(cs.num_of_quota_fail) + " students couldn't register due to quota problem."
                buffer += str(cs.num_of_credit_fail) + " students couldn't register due to credit problem."
                buffer += str(cs.num_of_prereq_fail) + " students couldn't register due to prereq problem."
        print(buffer)

    def create_new_course_list(self, student):
        new_course_list = []
        for course_section in self.__course_sections:
            if student.is_passed(course_section.course) is False and course_section.course.year <= student.year:
                new_course_list.append(course_section)
        return new_course_list

    def check_preq(self, course_code):
        for course in self.__courses:
            if course.course_code == course_code:
                return course
        return None
