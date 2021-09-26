create table Persons (
  person_id       int         not null,
  first_name      varchar(15) not null,
  last_name       varchar(30) not null,
  birthday        date        null,
  primary key (person_id)
);

create table Subjects (
  subject_id   int          not null,
  name         varchar(50)  not null,
  primary key (subject_id),
  unique (name)
);

create table UniversityGroups (
  group_id int         not null,
  group_no varchar(6)  not null,
  primary key (group_id),
  unique (group_no)
);

create table GroupSubjects (
  group_id     int not null,
  subject_id   int not null,
  teacher_id   int not null,
  hours        int null,
  primary key (group_id, subject_id),
  foreign key (group_id) references UniversityGroups (group_id),
  foreign key (subject_id) references Subjects (subject_id)
);

create table TeacherGroupSubjects (
  group_id     int not null,
  subject_id   int not null,
  teacher_id   int not null,
  primary key (group_id, subject_id, teacher_id),
  foreign key (teacher_id) references Persons (person_id),
  foreign key (group_id, subject_id) references GroupSubjects (group_id, subject_id)
);

alter table GroupSubjects
  add constraint GS2TGS_FK
    foreign key (group_id, subject_id, teacher_id)
      references TeacherGroupSubjects (group_id, subject_id, teacher_id);

create table Groupz (
  student_id int not null,
  group_id   int not null,
  primary key (student_id, group_id),
  foreign key (student_id) references Persons (person_id),
  foreign key (group_id) references UniversityGroups (group_id)
);

create table SubjectResults (
  subject_id int        not null,
  student_id int        not null,
  signer_id  int        not null,
  grade      numeric(1) not null,
  primary key (subject_id, student_id),
  foreign key (student_id) references Persons (person_id),
  foreign key (signer_id) references Persons (person_id),
  foreign key (subject_id) references Subjects (subject_id)
);
