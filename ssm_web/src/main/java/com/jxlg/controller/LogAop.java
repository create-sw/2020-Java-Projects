package com.jxlg.controller;

import com.jxlg.domain.SysLog;
import com.jxlg.service.ISysLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {
    @Autowired
    private ISysLogService sysLogService;

    @Autowired
    private HttpServletRequest request;

    private Date visitTime;
    private Class clazz;
    private Method method;
    //前置通知，主要是获取开始时间，执行的类是哪一个，执行的方法是哪个
    @Before("execution(* com.jxlg.controller.*.*(..))")
    public void doBefore(JoinPoint jp)throws NoSuchMethodException{
        visitTime=new Date();//获取时间
        clazz=jp.getTarget().getClass();//具体要访问的类
        String  methodName=jp.getSignature().getName();//获取访问的方法名
        Object[] args= jp.getArgs();
        if (args==null||args.length==0) {
            method = clazz.getMethod(methodName);//只能获取无参数的方法
        }else{
            Class[] classes=new Class[args.length];
            for(int i=0;i<args.length;i++)
            {
                classes[i]=args[i].getClass();
            }
            clazz.getMethod(methodName,classes);
        }
    }
    //后置通知
    @After("execution(* com.jxlg.controller.*.*(..))")
    public void doAfter(JoinPoint jp) throws Exception {
    long time= new Date().getTime()-visitTime.getTime();
    String url="";
         //获取URL
        if (clazz!=null&&method!=null&&clazz!=LogAop.class){
            //获取类上的@RequestMapping
            RequestMapping classAnnotation = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){
                String[] classValue = classAnnotation.value();
                //获取方法上的@RequestMapping
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();
                    url=classValue[0]+methodValue[0];

                    //获取访问的IP地址
                    String ip =request.getRemoteAddr();

                    //获取当前用户
                    SecurityContext context = SecurityContextHolder.getContext();
                    User user = (User)context.getAuthentication().getPrincipal();
                    String username= user.getUsername();

                    //将日志相关信息封装到sysLog对象
                    SysLog sysLog= new SysLog();
                    sysLog.setExecutionTime(time);
                    sysLog.setIp(ip);
                    sysLog.setMethod("[类名]"+clazz.getName()+"[方法名]"+method.getName());
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTime(visitTime);
                    //调用service完成操作
                    sysLogService.save(sysLog);
                }
            }
        }

    }

}
