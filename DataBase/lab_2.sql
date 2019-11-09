-- Variant 2
drop table EMPLOYEES cascade constraints;
drop table JOB_HISTORY;
drop sequence employee_auto_inc_sec;

create table EMPLOYEES
(
    NUM       number(4, 0),
    FNAME     varchar(100) not null,
    BDAY      date,
    GENDER    char(1)      default 'm',
    JOB       varchar(30)  not null,
    WAGE_RATE number(2, 1) default 1,
    SDATE     date         not null,
    ADDRESS   varchar(200) not null,

    CONSTRAINT pk_employees PRIMARY KEY (NUM),
    CONSTRAINT check_employees_gender CHECK (GENDER = 'm' or GENDER = 'f'),
    CONSTRAINT check_employees_wawgerate CHECK (WAGE_RATE >= 0.1 and WAGE_RATE <= 1.5)
);

create table JOB_HISTORY
(
    NUM            number(6, 0),
    START_DATE     date        not null,
    END_DATE       date,
    JOB            varchar(30) not null,
    NUM_DEPARTMENT number(2) default 1,

    CONSTRAINT fk_job_history FOREIGN KEY (NUM) REFERENCES EMPLOYEES (NUM),
    CONSTRAINT check_job_hist_num_depart CHECK (NUM_DEPARTMENT > 0 and NUM_DEPARTMENT <= 30)
);

-- create user student1 identified by '0102';
-- grant connect to student1;
-- grant select, insert on ssau.EMPLOYEES to student1;
--
-- connect student1;
-- select * from ssau.EMPLOYEES;
--
