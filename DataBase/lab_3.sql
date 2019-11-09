set sqlblanklines on

drop sequence sec_department_pk;
drop table DEPARTMENT cascade constraints;

@lab_2.sql;

insert into EMPLOYEES
values (100, 'Artem', '12-12-1990', 'm', 'backend_man', 1, '12-10-2012', 'moscow');

insert into EMPLOYEES
values (101, 'Leo', '12-11-1990', 'm', 'frontend_man', 1, '12-10-2012', 'moscow');

insert into EMPLOYEES
values (102, 'Dibos', '12-10-1990', 'm', 'rest_man', 1, '12-10-2012', 'moscow');

-- Second task

insert into EMPLOYEES(NUM, FNAME, BDAY, GENDER, JOB, SDATE, ADDRESS)
values (&num, &fname, &bday, &gender, &job, &sdate, &address);

insert into EMPLOYEES(NUM, FNAME, BDAY, GENDER, JOB, SDATE, ADDRESS)
values (&num, &fname, &bday, &gender, &job, &sdate, &address);

-- Third task

delete
from EMPLOYEES
where NUM = 100;

delete
from EMPLOYEES
where NUM = &num;

-- Fourth task

update EMPLOYEES
set FNAME = 'killreal'
where NUM = 101;

update EMPLOYEES
set FNAME = 'John'
where NUM = &num;

-- Fifth task

insert into JOB_HISTORY(NUM, START_DATE, END_DATE, JOB, NUM_DEPARTMENT)
values (102, '10-10-2001', '10-12-2001', 'first_job', 1);

insert into JOB_HISTORY(NUM, START_DATE, END_DATE, JOB, NUM_DEPARTMENT)
values (102, '10-10-2002', '10-12-2002', 'second_job', 1);

insert into JOB_HISTORY(NUM, START_DATE, END_DATE, JOB, NUM_DEPARTMENT)
values (102, '10-10-2003', '10-12-2003', 'third_job', 2);

insert into JOB_HISTORY(NUM, START_DATE, END_DATE, JOB, NUM_DEPARTMENT)
values (103, '15-01-2000', '01-09-2005', 'first_job', 2);

insert into JOB_HISTORY(NUM, START_DATE, END_DATE, JOB, NUM_DEPARTMENT)
values (103, '23-09-2001', '13-08-2010', 'first_job', 1);

@lab_3_script.sql;
