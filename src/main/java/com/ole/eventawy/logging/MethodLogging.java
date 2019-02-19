package com.ole.eventawy.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This class used to add log with entrance , exist of evey method call in the system plus the return 
 * and also you can add the Annotation {@link LogExecutionTime} to any method to log its execution time
 * 
 * @author Reda ElSayed
 *
 */
@Aspect
@Component
public class MethodLogging {
	
	 private static final Logger LOGGER = LoggerFactory.getLogger(MethodLogging.class);
	 
	    @Before("execution(* com.ole.eventawy.*.*.*.*(..))")
	    public void logMethodAccessBefore(JoinPoint joinPoint) {
	
	    	LOGGER.info(">>>> Method Starting : " +joinPoint+", with parameters"+"("+ getParametersList(joinPoint).toString() +") --- ");
	    }
	    
	    
	    @AfterReturning(pointcut="execution(* com.ole.eventawy.*.*.*.*(..))",returning ="result")
	    public void logMethodAccessAfter(JoinPoint joinPoint,Object result) {
	    	
	    	
	    	LOGGER.info( "<<<< Method Completed : " + joinPoint +" , With Parameters ("+ getParametersList(joinPoint).toString()+") and the Result is: "+result);
	       
	    }

	    @AfterThrowing(pointcut ="execution(* com.ole.eventawy.*.*.*.*(..))",throwing="error")
	    public void logException(JoinPoint joinPoint,Throwable error) {
	    	
	    	StringWriter errors = new StringWriter();
	    	error.printStackTrace(new PrintWriter(errors));
	    	
	    	LOGGER.info("<<Error>> Method Throw: " + joinPoint+" with parameters ("+getParametersList(joinPoint).toString()+") :: "+error+" --- ");
	    	LOGGER.error("Exception : "+error.toString());
	    	
	    }
	    
	    
	    
	    @Around("@annotation(LogExecutionTime)")
	    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
	        long start = System.currentTimeMillis();
	     
	        Object proceed = joinPoint.proceed();
	     
	        long executionTime = System.currentTimeMillis() - start;
	     
	        LOGGER.info("[[ Execution Time ]]"+joinPoint+" with parameters ("+getParametersList(joinPoint).toString()+") , executed in " + executionTime + "ms");
	        return proceed;
	    }
	    
	    private List<Object> getParametersList(JoinPoint joinPoint){
	    	List<Object> argList = new ArrayList<Object>();
	    	
	    	 Arrays.stream(joinPoint.getArgs()).forEach(argList::add);
	    	 
	    	 return argList;
	    }

}
