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

-- Изменение даты увольнения проверка что бы дата была больше start
create or replace trigger t_test_task
    after update of end_date on JOB_HISTORY
    for each row
declare
begin
    DBMS_OUTPUT.ENABLE();
    if :new.end_date < :new.start_date then
        DBMS_OUTPUT.PUT_LINE('Cant update');
        rollback;
    end if;
end;

update SSAU.JOB_HISTORY set END_DATE = TO_DATE('2020-02-02', 'yyyy-mm-dd') where num = 1015;
