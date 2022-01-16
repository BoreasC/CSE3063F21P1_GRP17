import CourseSection


class WeeklySchedule(object):
    def __init__(self, student):
        self.__stu_schedule = [[0 for x in range(8)] for y in range(5)]
        self.__student = student

    def add_to_schedule(self, course_section):
        course_schedule = course_section.course_schedule
        for i in range(0, 5):
            for j in range(0, 8):
                if course_schedule[i][j]:
                    self.__stu_schedule[i][j] = course_section

    def is_collision(self, course_section):
        course_schedule = course_section.course_schedule
        collided_hours = 0
        collided_sections = []
        for i in range(0, 5):
            for j in range(0, 8):
                if course_schedule[i][j] and self.__stu_schedule[i][j]:
                    collided_sections.append(self.__stu_schedule[i][j])
                    collided_hours += 1

        if collided_hours > 1:
            self.__student.log = "\nAdvisor didn't approve " + course_section.course_section_code + " because of more " \
                                                                                                    "than one hour " \
                                                                                                    "collision with -> "
            for c_section in collided_sections:
                self.__student.set_log(c_section.course_section_code + " ")
            self.__student.set_log(" in schedule")

            return True
        else:
            return False






