package com.ljh.api;

import com.ljh.util.JacksonUtil;
import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * PDA接口公用切面  打印日志，统一异常处理
 * @Author: lvjinhui
 */
@Component
@Aspect
@Scope("request")
public class ApiLogPermissionAspect {


    private static final Logger log = LoggerFactory.getLogger(ApiLogPermissionAspect.class);

    @Pointcut("@annotation(com.ljh.api.ApiLogAndCommonException)")
    public void point() {
    }


    @Around("point() && @annotation(apiLogDesc)")
    public Object parameterCheck(ProceedingJoinPoint joinPoint, ApiLogAndCommonException apiLogDesc) throws Exception {
        String desc=apiLogDesc.desc();//注解方法描述
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Object proceed=null;//返回值
        String method_type="";String api_url="";String targetName="";
        String methodName="";String ipAddr="";String json_param="";
        Long now=System.currentTimeMillis();
        Date req_date=new Date();
        log.info("收到请求>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            HttpServletResponse response=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

            method_type=request.getMethod();
            log.info("请求类型："+method_type);
            targetName = joinPoint.getTarget().getClass().getName();
            methodName = joinPoint.getSignature().getName();

            Class<?>[] par=((MethodSignature) joinPoint.getSignature()).getParameterTypes();

            Method objMethod=joinPoint.getTarget().getClass().getMethod(methodName,par);
            ApiOperation apiOperation=objMethod.getAnnotation(ApiOperation.class);
            if(apiOperation!=null){
                //如果swagger有描述，优先使用swagger的描述
                desc=apiOperation.value();
            }

            Object[] arguments = joinPoint.getArgs();

            int length = arguments.length;

            ipAddr = getRemoteHost(request);
            api_url = request.getRequestURL().toString();
            if(length!=0){
                json_param = JacksonUtil.bean2Json(arguments[length - 1]);
            }


            Map<String,String[]> params=request.getParameterMap();


            // *========控制台输出=========*//
            log.info("执行类：" + targetName);
            log.info("调用方法：" + methodName);
            log.info("方法描述：" + desc);
            log.info("请求IP:" + ipAddr);
            log.info("请求URL:" + api_url);
            String urlParam="";
            for(String key : params.keySet()){
                String[] value = params.get(key);
                log.info("URL参数:" + key+" => "+value[0].toString());
                urlParam+=key+"="+value[0].toString()+"&";
                //System.out.println(key+":"+value);
            }
            log.info("请求体参数：" + json_param);
            proceed = joinPoint.proceed(arguments);



        }catch (Throwable e){
            StringBuilder message = new StringBuilder();//堆栈异常信息
            message.append(e.toString());
            StackTraceElement[] errs=e.getStackTrace();
            for (StackTraceElement stack : errs) {
                message.append("\n\t").append(stack.toString());
            }
            System.out.println("接口报错："+message);

            String errInfo=errs.length>=0?e.getStackTrace()[0].toString():"";

//            proceed= ApiResponse.error("接口请求失败："+errInfo+" -> "+e.getCause());
            proceed= ApiResponse.error("接口请求失败：" + e.getMessage());
        }
        //没有异常直接插入
        Long rep_time = System.currentTimeMillis() - now;
        log.info("接口耗时:" + String.valueOf(rep_time) + " ms");
        String res_json = JacksonUtil.bean2Json(proceed);
        log.info("接口返回:" + res_json);
        log.info("请求结束<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println("");
        System.out.println("");
        System.out.println("");
        System.out.println("");

        return proceed;
    }




    /**
     * 获取目标主机的ip
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

}
