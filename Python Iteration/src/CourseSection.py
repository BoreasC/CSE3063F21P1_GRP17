from random import random


class CourseSection(object):
    def __init__(self, course):
        self.__section_hour = 0
        self.__course = course
        self.__students = []
        self.__num_of_credit_fail = 0
        self.__num_of_collision_fail = 0
        self.__num_of_prereq_fail = 0
        self.__num_of_quota_fail = 0
        self.__failed_credit = ""
        self.__failed_collision = ""
        self.__failed_prereq = ""
        self.__failed_quota = ""
        self.__course_schedule = [[False for x in range(8)] for y in range(5)]
        self.__full = False
        self.set_section_hour()
        self.set_course_schedule()

    def set_section_hour(self):
        self.__section_hour = self.__course.get_section_hours()

    def set_course_schedule(self):
        for i in range(0, self.__section_hour):
            day = int(random() * 5)
            hour = int(random() * 8)

            if self.__course_schedule[day][hour] is False:
                self.__course_schedule[day][hour] = True
            else:
                i -= 1

    def add_student(self, student):
        if self.is_full() is False:
            self.__students.append(student)
            student.new_courses(self)
            if self.get_quota() == len(self.__students):
                self.set_full(True)
        else:
            student.set_log("\nThe student couldn't  register for " + self.course_section_code + "because of a quota " \
                                                                                               "problem. ")
            self.__num_of_quota_fail += 1
            self.__failed_quota += " " + str(student.student_id)

    def is_full(self):
        return len(self.__students) == self.get_quota()

    def set_full(self, full):
        self.__full = full

    def get_quota(self):
        return self.__course.quota

    @property
    def course_section_code(self):
        return self.__course.course_code

    @property
    def course(self):
        return self.__course

    @property
    def num_of_credit_fail(self):
        return self.__num_of_credit_fail

    @num_of_credit_fail.setter
    def num_of_credit_fail(self, num_of_credit_fail):
        self.__num_of_credit_fail = num_of_credit_fail

    @property
    def failed_credit(self):
        return self.__failed_credit

    @failed_credit.setter
    def failed_credit(self, failed_credit):
        self.__failed_credit = failed_credit

    @property
    def num_of_collision_fail(self):
        return self.__num_of_collision_fail

    @num_of_collision_fail.setter
    def num_of_collision_fail(self, num_of_collision_fail):
        self.__num_of_collision_fail = num_of_collision_fail

    @property
    def failed_collision(self):
        return self.__failed_collision

    @failed_collision.setter
    def failed_collision(self, failed_collision):
        self.__failed_collision = failed_collision

    @property
    def num_of_prereq_fail(self):
        return self.__num_of_prereq_fail

    @num_of_prereq_fail.setter
    def num_of_prereq_fail(self, num_of_prereq_fail):
        self.__num_of_prereq_fail = num_of_prereq_fail

    @property
    def failed_prereq(self):
        return self.__failed_prereq

    @failed_prereq.setter
    def failed_prereq(self, failed_prereq):
        self.__failed_prereq = failed_prereq

    @property
    def num_of_quota_fail(self):
        return self.__num_of_quota_fail

    @num_of_quota_fail.setter
    def num_of_quota_fail(self, num_of_quota_fail):
        self.__num_of_quota_fail = num_of_quota_fail

    @property
    def failed_quota(self):
        return self.__failed_quota

    @failed_quota.setter
    def failed_quota(self, failed_quota):
        self.__failed_quota = failed_quota

    @property
    def course_schedule(self):
        return self.__course_schedule
