-- liquibase formatted sql

-- changeset tnugumanov:1
CREATE INDEX student_name_index ON student (name);

-- changeset tnugumanov:2
CREATE INDEX student_faculty  n
   c_idx ON faculty (name, col or);