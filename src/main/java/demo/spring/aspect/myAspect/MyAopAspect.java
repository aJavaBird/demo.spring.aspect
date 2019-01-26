package demo.spring.aspect.myAspect;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/** 定义切面类 */
@Component
@Aspect
public class MyAopAspect {

    private static Logger logger = Logger.getLogger(MyAopAspect.class);

    /**
     * @Pointcut(value = "execution (* demo.spring.aspect.service.impl.*.*(..))")
     * 定义统一拦截的切入点，其中第一个*表示任意返回类型，第二个*表示任意类名，
     * 假如第二个*前面是两个.则表示包括包里面的子包，第三个*表示任意方法名，后面的小括号表示任意参数值
     * 同理，可以是：@Pointcut(value = "execution (* demo.spring.aspect.service.impl.AspectServiceImpl.*(..))")
     * */
    @Pointcut("execution (* demo.spring.aspect.service.impl.*.*(..))")
    public void myPointCut() {

    }

    @Before("myPointCut()")
    public void myPointCutBefore(JoinPoint point) {
        logger.info("切面Before（下面打印方法及参数）");
        printMethodParams(point, null);
    }

    @After("myPointCut()")
    public void myPointCutAfter(JoinPoint point) {
        logger.info("切面After");
    }

    @AfterReturning(value = "myPointCut()", returning = "result")
    public void myPointCutAfterReturning(JoinPoint point, Object result) {
        logger.info("切面AfterReturning（下面打印方法、参数及结果）");
        printMethodParams(point, result);
    }

    @AfterThrowing(pointcut = "myPointCut()", throwing = "throwable")
    public void afterThrowing(JoinPoint point, Throwable throwable) {// 报错切面
        // 打印报错
        printMethodParams(point, "切面-方法异常--" + throwable.getMessage());
    }

    @Around("myPointCut()")
    public Object around(ProceedingJoinPoint point) { // 在程序运行前后执行，此处统计程序运行时间
        String className = point.getTarget().getClass().getSimpleName();
        String methodName = point.getSignature().getName();
        Object[] methodArgs = point.getArgs(); // 获取方法的参数值数组。
        StringBuffer methodArgsStr = new StringBuffer();
        for (Object obj : methodArgs) {
            methodArgsStr.append(obj.toString()).append(",");
        }
        try {
            logger.info(String.format("切面around - start[className=%s][methodName=%s]", className, methodName));
            long startTimeMillis = System.currentTimeMillis();
            //调用 proceed() 方法才会真正的执行实际被代理的方法
            Object result = point.proceed();
            long execTimeMillis = System.currentTimeMillis() - startTimeMillis;
            logger.info(String.format("切面around - end[className=%s][methodName=%s][result=%s]-耗时:%sms", className,
                    methodName, result, execTimeMillis));
            return result;
        } catch (Throwable te) {
            logger.error(te.getMessage(), te);
            throw new RuntimeException(te.getMessage());
        }
    }

    /**
     * 打印类method的名称以及参数
     * @param point 切面
     */
    public void printMethodParams(JoinPoint point, Object result) {
        if (point == null) {
            return;
        }
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        Object[] methodArgs = point.getArgs(); // 获取方法的参数值数组。
        StringBuffer methodArgsStr = new StringBuffer();
        for (Object obj : methodArgs) {
            methodArgsStr.append(obj.toString()).append(",");
        }
        if (methodArgsStr.length() > 0) {
            methodArgsStr.deleteCharAt(methodArgsStr.length() - 1);
        }
        //重新定义日志
        logger.info(String.format(
                "[className = %s][methodName = %s][methodArgs = %s]"
                        + (result == null ? "" : "[result=" + result + "]"),
                className, methodName, methodArgsStr.toString()));
    }

}
