-- Task 1
select *
from EMPLOYEES emp
where emp.NUM not in
      (
--           Подзапрос. ID рабочих, которые меняли работу.
          select distinct jh_1.NUM
          from JOB_HISTORY jh_1
                   join JOB_HISTORY jh_2 on jh_1.NUM = jh_2.NUM
          where jh_1.JOB != jh_2.JOB
          group by jh_1.NUM
      );

-- Task 2
select emp.FNAME
from EMPLOYEES emp
where emp.NUM in (
--     Подзапрос. Таблица с работниками которые меняли работу за последний год
    select distinct jh_1.NUM
    from JOB_HISTORY jh_1
             left join JOB_HISTORY jh_2 on jh_1.NUM = jh_2.NUM
    where MONTHS_BETWEEN(SYSDATE, NVL(jh_1.END_DATE, SYSDATE)) <= 12
      and MONTHS_BETWEEN(SYSDATE, NVL(jh_2.END_DATE, SYSDATE)) <= 12
      and jh_1.JOB != jh_2.JOB
);

-- Task 3
select NUM_DEPARTMENT
from for_select_task_3
where dept_count = (select MAX(dept_count)
                    from for_select_task_3);

create or replace view for_select_task_3 as
select jh_1.NUM_DEPARTMENT, count(jh_1.NUM_DEPARTMENT) as dept_count
from JOB_HISTORY jh_1
         inner join JOB_HISTORY jh_2 on jh_1.NUM = jh_2.NUM
where jh_1.JOB != jh_2.JOB
group by jh_1.NUM_DEPARTMENT;

-- Task 4
select distinct emp.FNAME
from EMPLOYEES emp
         left join JOB_HISTORY JH on emp.NUM = JH.NUM
where emp.NUM != 1014
  and (emp.JOB = (select emp.JOB
                  from EMPLOYEES emp
                  where emp.NUM = 1014)
    or jh.JOB = (select emp.JOB
                 from EMPLOYEES emp
                 where emp.NUM = 1014));

-- Task 5
select *
from EMPLOYEES emp
         right join JOB_HISTORY JH on emp.NUM = JH.NUM
         right join DEPARTMENT D on JH.NUM_DEPARTMENT = D.NUM_DEPARTMENT
where emp.GENDER = 'm'
  and emp.WAGE_RATE = 1.5;

-- Task 6
select PROPERTY
from select_for_task_6
where COUNT = (select max(COUNT) from select_for_task_6);

create or replace view select_for_task_6 as
select d.PROPERTY, jh.NUM_DEPARTMENT, count(jh.NUM_DEPARTMENT) as "COUNT"
from JOB_HISTORY jh
         inner join DEPARTMENT D on jh.NUM_DEPARTMENT = D.NUM_DEPARTMENT
where END_DATE is null
group by d.PROPERTY, jh.NUM_DEPARTMENT;
-- Task 7
select d.PROPERTY
from JOB_HISTORY jh
         right join DEPARTMENT D on jh.NUM_DEPARTMENT = D.NUM_DEPARTMENT
where END_DATE is null
group by d.PROPERTY, jh.NUM_DEPARTMENT
having count(jh.NUM_DEPARTMENT) > &num;
