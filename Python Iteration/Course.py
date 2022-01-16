class Course(object):
    def __init__(self, course_code, course_name, semester, quota, credits,
                 theoretic, lab, year, required_credits, prerequisite: 'Course', pass_prob):
        self.__section_hours = []
        self.__course_code = course_code
        self.__course_name = course_name
        self.__semester = semester
        self.__quota = int(quota)
        self.__credits = int(credits)
        self.__theoretic = int(theoretic)
        self.__lab = int(lab)
        self.__year = int(year)
        self.__required_credits = int(required_credits)
        self.__prerequisite = prerequisite
        self.__pass_prob = float(pass_prob)
        self.set_section_hours(theoretic, lab)

    def set_section_hours(self, theoretic, lab):
        self.__section_hours = [theoretic, lab]

    def get_section_hours(self):
        return self.__section_hours[0] + self.__section_hours[1]

    @property
    def course_code(self):
        return self.__course_code

    @course_code.setter
    def course_code(self, course_code):
        self.__course_code = course_code

    @property
    def course_name(self):
        return self.__course_name

    @course_name.setter
    def course_name(self, course_name):
        self.__course_name = course_name

    @property
    def semester(self):
        return self.__semester

    @semester.setter
    def semester(self, semester):
        self.__semester = semester

    @property
    def quota(self):
        return self.__quota

    @quota.setter
    def quota(self, quota):
        self.__quota = quota

    @property
    def credits(self):
        return self.__credits

    @credits.setter
    def credits(self, credits):
        self.__credits = credits

    @property
    def year(self):
        return self.__year

    @property
    def required_credits(self):
        return self.__required_credits

    @property
    def prerequisite(self):
        return self.__prerequisite

    @prerequisite.setter
    def prerequisite(self, prerequisite):
        self.__prerequisite = prerequisite

    @property
    def pass_prob(self):
        return self.__pass_prob
