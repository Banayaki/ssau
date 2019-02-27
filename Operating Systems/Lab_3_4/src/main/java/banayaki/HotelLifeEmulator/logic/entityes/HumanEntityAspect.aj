package banayaki.HotelLifeEmulator.logic.entityes;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class HumanEntityAspect {
    private final static Logger logger = LogManager.getLogger(HumanEntity.class);

    @Pointcut("execution(* banayaki.HotelLifeEmulator.logic.entityes.HumanEntity.live())")
    public void target() {}

    @Around("target(void)")
    public Object targetAroundAdvice(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("Was Created: " + pjp.getSignature().getName());
        Object retVal = pjp.proceed();
        logger.info("Was Ended his job: " + pjp.getSignature().getName());
        return retVal;
    }
}
