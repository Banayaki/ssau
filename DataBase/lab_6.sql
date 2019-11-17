-- task 1

create or replace view task_1 as
select FNAME
from EMPLOYEES
where GENDER = 'm'
  and SDATE > TO_DATE('01.01.2014', 'dd.mm.yyyy');

select *
from task_1;

-- task 2

create or replace view task_2 as
select *
from EMPLOYEES;

insert into EMPLOYEES
select 1015,
       FNAME,
       BDAY,
       GENDER,
       task_2.JOB,
       WAGE_RATE,
       SDATE,
       ADDRESS
from task_2
where num = 1014;

select *
from task_2;

-- task 3

create or replace view task_3 as
select FNAME
from EMPLOYEES
         join JOB_HISTORY JH on EMPLOYEES.NUM = JH.NUM
where END_DATE is null
  and NUM_DEPARTMENT = 5;

select *
from task_3;

-- task 4

create or replace view task_4 as
select EMPLOYEES.*
from EMPLOYEES
         inner join JOB_HISTORY on EMPLOYEES.NUM = JOB_HISTORY.NUM
where END_DATE is null
  and NUM_DEPARTMENT = 5
  and WAGE_RATE < 0.5
    with check option;

create or replace view task_4 as
select EMPLOYEES.*
from EMPLOYEES
where WAGE_RATE < 0.5
  and num in (select num from JOB_HISTORY where END_DATE is null and NUM_DEPARTMENT = 5);

update task_4
set WAGE_RATE = 1.0
where NUM = 104;

select *
from task_4;

update EMPLOYEES emp
set emp.WAGE_RATE = emp.WAGE_RATE * 1.1
where fname in (select * from task_4);



select min(average) from (select avg(emp.WAGE_RATE) as average
from EMPLOYEES emp
         inner join JOB_HISTORY J on emp.NUM = J.NUM
         inner join DEPARTMENT D on J.NUM_DEPARTMENT = D.NUM_DEPARTMENT
group by j.NUM_DEPARTMENT);