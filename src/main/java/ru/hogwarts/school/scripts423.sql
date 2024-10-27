1. Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах
(достаточно получить только имя и возраст студента) школы Хогвартс вместе с названиями факультетов.

SELECT student.name, student.age
FROM student
INNER JOIN faculty ON student.faculty = faculty.name;

2. Составить второй JOIN-запрос, чтобы получить только тех студентов, у которых есть аватарки.
SELECT student.name, student.age
FROM student
RIGHT JOIN avatar ON student.id = avatar.id;
