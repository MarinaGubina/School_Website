-- liquibase formatted sql

-- changeset mgubina:1
CREATE INDEX student_name_index ON student (name);

-- changeset mgubina:2
CREATE INDEX faculty_nc_index ON faculty (name, color);