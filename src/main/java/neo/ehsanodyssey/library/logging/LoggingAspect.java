package neo.ehsanodyssey.library.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : AmirEhsan Shahmirzaloo (EhsanOdyssey)
 * @mailto : <a href="mailto:ehsan.shahmirzaloo@gmail.com">EhsanOdyssey</a>
 * @project : online-library
 * @created : 2024-02-16 Feb/Fri
 **/
@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Pointcut(
            "within(neo.ehsanodyssey.library.rest.v*.*)" +
                    "|| within(neo.ehsanodyssey.library.service..*)" +
                    "|| within(neo.ehsanodyssey.library.dal.repository..*)"
    )
    public void packagesPointcut() {
    }

    @AfterThrowing(pointcut = "packagesPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        log.error("Exception occurred in {} > {}() with cause --> {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                (e.getMessage() != null ? e.getMessage() : "EMPTY"));
    }

    @Around("packagesPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        if (log.isDebugEnabled()) {
            log.debug("Enter: {} > {}() with argument[s] -->\n{}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()));
        }
        try {
            Object result = joinPoint.proceed();
            if (log.isDebugEnabled()) {
                log.debug("Exit: {} > {}() with result -->\n{}",
                        joinPoint.getSignature().getDeclaringTypeName(),
                        joinPoint.getSignature().getName(),
                        result);
            }
            return result;
        } catch (IllegalArgumentException e) {
            log.error("Illegal arguments passed to {} > {}() --> {}",
                    joinPoint.getSignature().getDeclaringTypeName(),
                    joinPoint.getSignature().getName(),
                    Arrays.toString(joinPoint.getArgs()));
            throw e;
        }
    }
}
