-- Task 1

Create or replace procedure p_task_1
    is
begin
    DBMS_OUTPUT.ENABLE();

    for itr in (select job || ' ' || count(job) as info from EMPLOYEES group by JOB)
        loop
            DBMS_OUTPUT.PUT_LINE(itr.info);
        end loop;
end;

-- Task 2

create or replace procedure p_task_2
    is
begin
    for itr in (select ROWID, START_DATE, END_DATE from JOB_HISTORY)
        loop
            if itr.START_DATE > itr.END_DATE then
                update JOB_HISTORY
                set END_DATE   = itr.START_DATE,
                    START_DATE = itr.END_DATE
                where rowid = itr.ROWID;
            end if;
        end loop;
end;

call P_TASK_2();

select *
from SSAU.JOB_HISTORY;

-- Task 3

create or replace procedure p_task_3
    is
begin
    delete
    from EMPLOYEES
    where NUM in (select NUM from EMPLOYEES where BDAY < TO_DATE('01.01.1969', 'dd.mm.yyyy'));
end;

call p_task_3();

-- Task 4

create table bonus
(
    num  number(6, 0),
    rise number(6, 0)
);

create or replace procedure p_task_4(in_rise in number)
    is
    max_wage_rate number(2, 1);
begin
    select max(wage_rate) into max_wage_rate from employees;

    insert into bonus
        (
            select num, in_rise
            from employees
            where wage_rate = max_wage_rate
        );
end;

call P_TASK_4(1000);

select * from SSAU.BONUS;