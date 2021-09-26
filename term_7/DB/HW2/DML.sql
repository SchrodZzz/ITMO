insert into Persons
  (person_id, first_name, last_name, birthday) values
  (1, 'Andrey', 'Ivshin', date '2000-05-31'),
  (2, 'Алесксандр', 'Макеоднский', null),
  (3, 'Чинг', 'Иванов', null),
  (4, 'Иван', 'Чонг', date '2005-11-30'),
  (5, 'Alex', 'Brin', null),
  (6, 'Roman', 'Yuneman', null),
  (7, 'Андрей', 'Браткевич', null);

insert into Subjects
  (subject_id, name) values
  (1, 'Теор Инф'),
  (2, 'PE (шахматы в онлайне)'),
  (3, 'ППО'),
  (4, 'Финкек');

insert into UniversityGroups
  (group_id, group_no) values
  (1, 'M34342'),
  (2, 'M34391'),
  (3, 'P1337');

alter table GroupSubjects
  drop constraint GS2TGS_FK;

insert into GroupSubjects
  (group_id, subject_id, teacher_id, hours) values
  (2, 1, 5, 200),
  (1, 2, 4, 9000),
  (1, 3, 7, null);

insert into TeacherGroupSubjects
  (group_id, subject_id, teacher_id) values
  (2, 1, 5),
  (1, 2, 4),
  (1, 3, 7);

insert into TeacherGroupSubjects
  (group_id, subject_id, teacher_id) values
  (1, 1, 5),
  (2, 2, 4);

alter table GroupSubjects
  add constraint GS2TGS_FK
    foreign key (group_id, subject_id, teacher_id)
      references TeacherGroupSubjects (group_id, subject_id, teacher_id);

insert into SubjectResults
  (subject_id, student_id, signer_id, grade) values
  (1, 1, 5, 5),
  (2, 2, 4, 3),
  (4, 1, 1, 5);
