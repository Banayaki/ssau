create sequence sec_department_pk
    increment by 1
    start with 1
    maxvalue 30
    minvalue 0
    nocycle;

create table DEPARTMENT
(
    NUM_DEPARTMENT number(2),
    PROPERTY varchar2(20) not null,

    CONSTRAINT pk_ssau_department PRIMARY KEY (NUM_DEPARTMENT),
    CONSTRAINT check_department_num CHECK ( NUM_DEPARTMENT >= 1 and NUM_DEPARTMENT <= 30 )
);

insert into DEPARTMENT values (sec_department_pk.nextval, &property);
insert into DEPARTMENT values (sec_department_pk.nextval, &property);

alter table SSAU.JOB_HISTORY ADD
    CONSTRAINT fk_job_his_num_dept FOREIGN KEY (NUM_DEPARTMENT) REFERENCES DEPARTMENT (NUM_DEPARTMENT);