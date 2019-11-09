-- Task 1
select *
from EMPLOYEES emp
where emp.NUM in
      (
          select DISTINCT emp.num
          from EMPLOYEES emp
                   left join JOB_HISTORY jh on emp.NUM = JH.NUM
          group by emp.NUM, emp.JOB, jh.JOB
          having count(emp.NUM) = 1
             and emp.JOB = NVL(jh.JOB, emp.JOB)
      );

-- Task 2
select emp.FNAME
from EMPLOYEES emp
where emp.NUM in (
    select DISTINCT emp.NUM
    from EMPLOYEES emp
             left join JOB_HISTORY jh on emp.NUM = JH.NUM
    where MONTHS_BETWEEN(jh.END_DATE, SYSDATE) <= 12
    group by emp.NUM, emp.JOB, jh.JOB
    having count(emp.NUM) > 1
);

-- Task 3
select NUM_DEPARTMENT
from (select jh.NUM_DEPARTMENT, count(jh.NUM_DEPARTMENT)
      from EMPLOYEES emp
               left join JOB_HISTORY jh on emp.NUM = JH.NUM
      where emp.job != NVL(jh.job, emp.job)
      group by jh.NUM_DEPARTMENT
      order by count(jh.NUM_DEPARTMENT) DESC)
where ROWNUM = 1;

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

-- Task 7
select *
from (
         select dept.NUM_DEPARTMENT, count(dept.NUM_DEPARTMENT) as "COUNT"
         from DEPARTMENT dept
                  right join EMPLOYEES E on dept.NUM_DEPARTMENT = E.NUM_DEPARTMENT
         group by dept.NUM_DEPARTMENT
     )
where ROWNUM = 1;


-- Task 8
select dept.NUM_DEPARTMENT, count(dept.NUM_DEPARTMENT) as "COUNT"
from DEPARTMENT dept
         right join EMPLOYEES E on dept.NUM_DEPARTMENT = E.NUM_DEPARTMENT
group by dept.NUM_DEPARTMENT
having count(dept.NUM_DEPARTMENT) > 2;
