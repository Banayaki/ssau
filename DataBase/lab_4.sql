set sqlblanklines on

-- Prepare
insert into EMPLOYEES(NUM, FNAME, BDAY, GENDER,
                      JOB, WAGE_RATE, SDATE, ADDRESS)
values (1010, 'Kate', '15-04-1999', 'f', 'worker', 1.2, '02-03-2014', 'somewhere');

insert into EMPLOYEES(NUM, FNAME, BDAY, GENDER,
                      JOB, WAGE_RATE, SDATE, ADDRESS)
values (1011, 'Lisa', '10-03-1999', 'f', 'worker', 0.2, '02-03-2019', 'somewhere');

insert into EMPLOYEES(NUM, FNAME, BDAY, GENDER,
                      JOB, WAGE_RATE, SDATE, ADDRESS)
values (1012, 'Ivan', '15-04-1999', 'm', 'worker', 0.4, '02-05-2019', 'somewhere');

insert into EMPLOYEES(NUM, FNAME, BDAY, GENDER,
                      JOB, WAGE_RATE, SDATE, ADDRESS)
values (1013, 'Diman', '15-04-1999', 'm', 'worker', 1.0, '05-03-2010', 'somewhere');

insert into EMPLOYEES(NUM, FNAME, BDAY, GENDER,
                      JOB, WAGE_RATE, SDATE, ADDRESS)
values (1014, 'Artem', '15-04-1999', 'm', 'worker', 1.5, '10-08-2019', 'somewhere');

insert into JOB_HISTORY(NUM, START_DATE, END_DATE, JOB, NUM_DEPARTMENT)
values (1014, '10-08-2019', SYSDATE, 'worker', 2);

insert into JOB_HISTORY(NUM, START_DATE, END_DATE, JOB, NUM_DEPARTMENT)
values (1014, '05-03-2018', '10-08-2019', 'worker', 1);

insert into JOB_HISTORY(NUM, START_DATE, END_DATE, JOB, NUM_DEPARTMENT)
values (1013, '05-03-2010', SYSDATE, 'worker', 1);

COMMIT;

-- Task 1
select *
from EMPLOYEES
where GENDER = 'f';

-- Task 2
select FNAME, WAGE_RATE
from EMPLOYEES
where MONTHS_BETWEEN(SYSDATE, SDATE) <= 12 * 2;

-- Task 3
select *
from EMPLOYEES
where GENDER = 'm' and WAGE_RATE > 1.0;

-- Task 4
select FNAME
from EMPLOYEES
where MONTHS_BETWEEN(SYSDATE, SDATE) <= 12
order by FNAME DESC;

-- Task 5
select JOB, COUNT(FNAME)
from EMPLOYEES
group by JOB;

-- Task 6
select DISTINCT FNAME
from EMPLOYEES emp, JOB_HISTORY jh
where emp.NUM = jh.NUM and jh.NUM_DEPARTMENT in (1, 2, 4, 5);

-- Task 7
select *
from EMPLOYEES emp, JOB_HISTORY jh
where emp.NUM = jh.NUM and emp.JOB = jh.JOB and emp.SDATE = jh.START_DATE and MONTHS_BETWEEN(jh.START_DATE, '01-01-2014') <= 0;

-- Task 8
select emp.FNAME
from EMPLOYEES emp, JOB_HISTORY jh
where emp.NUM = jh.NUM and
      ((MONTHS_BETWEEN(SYSDATE, emp.SDATE) > 12 * 5 and emp.JOB = &job) or
      (jh.JOB = &job and MONTHS_BETWEEN(jh.END_DATE, jh.START_DATE) > 12 * 5));

select JOB, COUNT(ALL JOB)
from EMPLOYEES
group by JOB;