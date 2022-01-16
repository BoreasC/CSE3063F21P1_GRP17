class StudentID(object):
    def __init__(self, student):
        self.__student = student
        self.__department_code = "1501"

    def registration_year(self):
        reg_year = str((2021 - self.__student.year) % 100)
        return reg_year

    def get_student_id(self):
        stu_id = self.__department_code + self.registration_year() + "{:03}".format(self.__student.reg_order)
        return stu_id

    def __str__(self):
        return self.get_student_id()

