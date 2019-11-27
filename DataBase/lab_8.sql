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
alter trigger t_task_3 disable;
alter trigger t_task_4 disable;

-- Seems like it can't be written like this. I need to use much more instruments (packages) and if i will use it will be
-- thousands lines of code

create or replace trigger t_task_4
    before update of WAGE_RATE on SSAU.EMPLOYEES
    for each row
declare
    pragma autonomous_transaction;
    min_id number;
begin
    select min(num) into min_id from SSAU.EMPLOYEES;
    if :old.num = min_id and :new.job = :old.job and
       :old.wage_rate != :new.wage_rate then
                update SSAU.EMPLOYEES
                set WAGE_RATE = :new.wage_rate
                where num != min_id and job = :old.job;
    end if;
    commit;
end;

update SSAU.EMPLOYEES
set WAGE_RATE = 0.5
where NUM = 102;
commit;