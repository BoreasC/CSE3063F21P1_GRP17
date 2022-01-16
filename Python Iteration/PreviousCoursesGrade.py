class PreviousCoursesGrade(object):
    def __init__(self, course, decimal_grade):
        self.__course = course
        self.__decimal_grade = decimal_grade

    def convert_letter_grade(self):
        letter_grade = ""
        if self.__decimal_grade <= 44:
            letter_grade = "FF"
        elif 44 < self.__decimal_grade <= 49:
            letter_grade = "FD"
        elif 49 < self.__decimal_grade <= 54:
            letter_grade = "DD"
        elif 54 < self.__decimal_grade <= 64:
            letter_grade = "DC"
        elif 64 < self.__decimal_grade <= 74:
            letter_grade = "CC"
        elif 74 < self.__decimal_grade <= 79:
            letter_grade = "CB"
        elif 79 < self.__decimal_grade <= 84:
            letter_grade = "BB"
        elif 84 < self.__decimal_grade <= 89:
            letter_grade = "BA"
        else:
            letter_grade = "AA"
        return letter_grade

    @property
    def course(self):
        return self.__course
