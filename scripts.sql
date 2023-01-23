select * from student;
select * from student where age < 20 AND age > 10;
select name from student;
select * from student where lower(name) like '%h%' ;
select * from student where age < student.id;
select * from student order by age;