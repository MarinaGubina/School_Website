-- liquibase formatted sql

-- changeset mgubina:1
--precondition-sql-check expectedResult:1 select case when count(i.tablename) = 1 then 0 else 1 end from pg_tables t inner join pg_indexes i on t.tablename = i.tablename where t.tablename = 'student' and i.indexname ='student_name_index';
--onFail=MARK_RAN
CREATE INDEX student_name_index ON student (name);

-- changeset mgubina:2
--precondition-sql-check expectedResult:1 select case when count(i.tablename) = 1 then 0 else 1 end from pg_tables t inner join pg_indexes i on t.tablename = i.tablename where t.tablename = 'faculty' and i.indexname ='faculty_nc_index';
--onFail=MARK_RAN
CREATE UNIQUE INDEX faculty_nc_index ON faculty (name, color);