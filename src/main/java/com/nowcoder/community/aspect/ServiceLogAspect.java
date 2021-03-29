package com.nowcoder.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhou
 * @create 2021-3-29 19:04
 */


@Component
@Aspect
public class ServiceLogAspect {

    // 创建日志对象
    private static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    // 定义切点
    // * ：表示接受所有的返回值
    // * com.nowcoder.community.service ：包名
    // * com.nowcoder.community.service.* ：service 包下的所有类
    // * com.nowcoder.community.service.*.*(..)：service 包下的所有类中的方法，(..) 表示支持任意类型与数量的参数
    @Pointcut("execution(* com.nowcoder.community.service.*.*(..))")
    public void pointcut() {

    }

    // 在切点前执行
    @Before("pointcut()")
    public void before(JoinPoint joinPoint) {
        // 获取请求上下文持有器中的 request 域属性
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取 request 请求
        HttpServletRequest  request= attributes.getRequest();
        // 获取远程客户端 ip
        String ip = request.getRemoteHost();
        // 获取当前时间
        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        // 获取客户端访问的方法
        String target = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

        // 打印日志：格式为  用户 XXX,在 XXX 时间,访问了 XXX
        logger.info(String.format("用户[%s],在[%s],访问了[%s].", ip, now, target));
    }
}
