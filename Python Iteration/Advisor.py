import string


class Advisor(object):
    def __init__(self, name):
        self.__name = name

    def approve_course(self, student, course_section):
        if student.transcript.calculate_credits() < course_section.course.required_credits:
            student.set_log("\nInsufficient credit")
            course_section.num_of_credit_fail += 1
            #course_section.failed_credit(course_section.failed_credit + " " + student.student_id)
        elif course_section.course.prerequisite and student.is_passed(
                course_section.course.prerequisite) is False:
            student.set_log("\nNot approved " + course_section.course_section_code + " because student failed " \
                                                                                 "prerequisite: " + \
                        course_section.course.prerequisite.course_code)
            course_section.num_of_prereq_fail += 1
            #course_section.failed_prereq += course_section.failed_prereq + " " + str(student.student_id)
        elif student.schedule.is_collision(course_section):
            #student.set_log("\nNot approved. Collision!")
            course_section.num_of_collision_fail += 1
            #course_section.failed_collision += course_section.failed_collision + " " + str(student.student_id)
        elif course_section.num_of_quota_fail > 0:
            student.set_log("\nThe student couldn't register for " + course_section.course_section_code + " because of a "
                                                                                                      "quota problem.")
            course_section.num_of_quota_fail += 1
            #course_section.failed_quota += course_section.failed_quota + " " + str(student.student_id)
        else:
            course_section.add_student(student)

    @property
    def name(self):
        return self.__name
