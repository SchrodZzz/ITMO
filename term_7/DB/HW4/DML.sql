insert into Groups (groupId, groupName) values
	(1, 'M34342'),
	(2, 'M34351'),
	(3, 'M34381'),
	(4, 'M34391');

insert into Students (studentId, studentName, groupId) values
	(1, 'Анон Анонов', 2),
	(2, 'Василий Лебедев', 4),
	(3, 'Ivshin Andrey', 1),
	(4, 'Ли Кван Ю', 2);

insert into Lecturers (lecturerId, lecturerName) values
	(1, 'Korneev Georgiy'),
	(2, 'Elizarov Lapochka'),
	(3, 'Станкевич Андрей');

insert into Courses (courseId, courseName) values
	(1, 'Discrete Match'),
	(2, 'Discrete Match'),
	(3, 'Java Basics'),
	(4, 'DBMS'),
	(5, 'Java Advanced'),
	(6, 'Type Theory'),
	(7, 'Теория типов');

insert into Teaching (courseId, groupId, lecturerId) values
	(5, 1, 1),
	(5, 2, 1),
	(5, 3, 1),
	(5, 4, 1);

insert into Marks (studentId, courseId, mark) values
	(1, 6, 100.00),
	(2, 6, 100.00),
	(3, 6, 99.99),
	(4, 6, 59.00);
