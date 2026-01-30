package es.eroski.misumi.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public interface LoggingAdvice {

	public abstract Object daoLogCall(ProceedingJoinPoint call)
			throws Throwable;

}