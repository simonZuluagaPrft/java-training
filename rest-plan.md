# Functionalities
CRUD student
enroll student
get student courses
get student subjects
get student grade
get student schedule
get student report card

CRUD teacher
grade a student (through a course)
change a student grade (through a course)

CRUD subject

create course
delete course
drop student from a course
get students that passed the course
get students that failed the course
get students enrolled in the course

CRUD lecture

CRUD grade

---
# Routing
student/
POST    create student

student/{email}
GET     read student

student/{id}
PUT     update student
DELETE  delete student

student/{id}/courses
GET     get courses enrolled by the student

student/{id}/subjects
GET     get subjects the student is studying

student/{studentId}/grades/{courseId}
GET     get student's grade for a course

student/{id}/schedule
GET     get student's schedule

student/{id}/reportCard
GET     get student's reportCard

teacher/
POST    create teacher

teacher/{email}
GET     read teacher

teacher/{id}
PUT     update teacher
DELETE  delete teacher

teacher/{teacherId}/grade
POST    grade a student
PUT     update a students grade

subject/
POST    create subject

subject/{name}
GET     read subject

subject/{id}
PUT     update subject
DELETE  delete subject

course/
POST    create course

course/{id}
GET     read course
PUT     update course
DELETE  delete course

course/enrollment
POST    enroll student in a course

course/drop/{studentId}/{courseId}
DELETE  drop student from a course

lecture/
POST    create lecture

lecture/{id}
GET     read lecture
PUT     update lecture
DELETE  delete lecture