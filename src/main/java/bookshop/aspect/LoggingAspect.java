package bookshop.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.apache.log4j.Logger;

@Aspect
public class LoggingAspect {

	private Logger logger = Logger.getLogger(LoggingAspect.class);

	@Before("execution(* bookshop.service.book.BookService.*(..))")
	public void logBefore(JoinPoint joinPoint) {

		logger.info("Entering " + joinPoint.getSignature().getName() + " method;");
	}
	@AfterReturning(
			pointcut = "execution(* bookshop.service.book.BookService.*(..))",
			returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		logger.info("Method " + joinPoint.getSignature().getName() + " completed successfully and returned " + result);

	}
	@AfterThrowing(
			pointcut = "execution(* bookshop.service.book.BookService.*(..))",
			throwing = "error")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
		logger.info("Method " + joinPoint.getSignature().getName() + " throwed an exception: " + error);
	}
}
