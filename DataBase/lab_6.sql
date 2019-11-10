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
from EMPLOYEES join JOB_HISTORY JH on EMPLOYEES.NUM = JH.NUM
where END_DATE is null and NUM_DEPARTMENT = 5;

select * from task_3;

-- task 4

create or replace view task_4 as
select FNAME
from EMPLOYEES join JOB_HISTORY JH on EMPLOYEES.NUM = JH.NUM
where END_DATE is null and NUM_DEPARTMENT = 5 and WAGE_RATE < 0.5;

update EMPLOYEES emp set emp.WAGE_RATE = emp.WAGE_RATE * 1.1
where fname in (select * from task_4);