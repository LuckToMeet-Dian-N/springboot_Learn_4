package com.wen.test.aop;


import com.wen.test.entity.ResultBean;
import com.wen.test.exception.CheckException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


/**
 * 切面
 */
@Slf4j  //日志注解
@Aspect     //切面
@Order(5)       //执行顺序
@Component      //一般不知道是哪个分类是，就用这玩意将他注入到spring容器中
public class HandlerException {

    /**
     * 切点，找到那些方面要切
     */
    @Pointcut("execution(public * com.wen.test.controller.*.*(..))")
    public void webException(){}

    /**
     * 环切，执行方法前后都切
     * @param proceedingJoinPoint
     * @return
     */
    @Around("webException()")
    public ResultBean handlerControllerMethod(ProceedingJoinPoint proceedingJoinPoint){
        ResultBean resultBean;
        try {
            long startTime = System.currentTimeMillis();
            resultBean= (ResultBean) proceedingJoinPoint.proceed();

            long endTime = System.currentTimeMillis()-startTime;

            log.info("最后花费的时间为："+ endTime);
        }catch (Throwable e){
           resultBean= handlerException(e);
        }
        return resultBean;
    }

    /**
     * 这个是判断异常的类型
     * @param throwable
     * @return
     */
    private ResultBean<?> handlerException(Throwable throwable){
        ResultBean<?> resultBean=new ResultBean();
        if (throwable instanceof CheckException|| throwable instanceof IllegalArgumentException){
            resultBean.setMsg(throwable.getLocalizedMessage());
            resultBean.setCode(ResultBean.CHECK_FAIL);
        }else {
            log.error("未知异常：",throwable);
            resultBean.setMsg("未知异常，请联系管理员");
            resultBean.setCode(ResultBean.UNKNOWN_EXCEPTION);
        }
        return resultBean;


    }
}
