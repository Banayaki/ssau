package banayaki.HotelLifeEmulator.logic.entityes;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
@Aspect
public class HumanEntityAspect {
    private final static Logger logger = LoggerFactory.getLogger(HumanEntityAspect.class);

    public HumanEntityAspect() {
        logger.info("sdfgfdgdfgdfgdfgdfgdfgdfgdfg");
    }

    @Pointcut("execution(* banayaki.HotelLifeEmulator.logic.entityes.HumanEntity.live())")
    public void targetMethod() {
    }

    @Around("banayaki.HotelLifeEmulator.logic.entityes.HumanEntityAspect.targetMethod()")
    public Object targetAroundAdvice(ProceedingJoinPoint pjp) throws Throwable{
        logger.info("Was Created: " + pjp.getSignature().getName());
        Object retVal = pjp.proceed();
        logger.info("Was Ended his job: " + pjp.getSignature().getName());
        return retVal;
    }
}
