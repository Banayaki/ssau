-- task 1

create or replace trigger t_task_1
    after delete
    on EMPLOYEES
    for each row
declare
begin
    delete from JOB_HISTORY where NUM = :old.NUM;
end;

-- task 2

create or replace trigger t_task_2
    after insert
    on EMPLOYEES
    for each row
declare
begin
    insert into JOB_HISTORY(NUM, START_DATE, END_DATE, JOB)
    values (:new.num, :new.sdate, null, :new.job);
end;

-- task 3

create or replace trigger t_task_3
    before update
    on EMPLOYEES
    for each row
declare
begin
    if :old.job != :new.job then
        :new.wage_rate := 1.0;
    end if;
end;

-- task 4

alter trigger t_task_1 disable;
alter trigger t_task_2 disable;

-- Seems like it can't be written like this. I need to use much more instruments (packages) and if i will use it will be
-- thousands lines of code

create or replace trigger t_task_4
    before update of wage_rate
    on EMPLOYEES
    for each row
declare
    min_num EMPLOYEES.num%TYPE;
begin
    select min(num) into min_num from EMPLOYEES group by num;

    if min_num = :new.num and :old.job = :new.job and :old.wage_rate != :new.wage_rate then
        for itr in (select * from EMPLOYEES where num != min_num and job = :old.job)
            loop
                delete from EMPLOYEES where num = itr.num;
                insert into EMPLOYEES (NUM, FNAME, BDAY, GENDER, JOB, WAGE_RATE, SDATE, ADDRESS) values
                (itr.NUM, itr.FNAME, itr.BDAY, itr.GENDER, itr.JOB, :new.wage_rate, itr.SDATE, itr.ADDRESS);
            end loop;

    end if;
end;

update EMPLOYEES
set WAGE_RATE = 1.4
where num = 1014

