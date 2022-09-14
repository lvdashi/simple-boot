package com.ljh.config.interceptor;

import cn.hutool.core.util.ObjectUtil;
import com.ljh.entity.UserContext;
import com.ljh.api.ApiResponse;
import com.ljh.config.annotion.TokenValidatorAnnotaion;
import com.ljh.util.JacksonUtil;
import com.ljh.util.JwTokenUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 权限验证拦截器
 * @author lvjinhui
 * @date 2022/03/03 17:20
 **/
@Slf4j
public class TokenValidatorInterceptor implements HandlerInterceptor {

    /**
     * 前置处理器
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            if (Objects.requireNonNull(handlerMethod.getMethod()).isAnnotationPresent(TokenValidatorAnnotaion.class)) {
                TokenValidatorAnnotaion tokenValidatorAnnotaion = handlerMethod.getMethod().getAnnotation(TokenValidatorAnnotaion.class);
                if (!tokenValidatorAnnotaion.required()) {
                    return true;
                }
            }
        }catch (ClassCastException err){
            System.out.println("请求头转换异常："+handler.toString());
            return true;
        }
        try {
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JacksonUtil.bean2Json(ApiResponse.tokenErr("token为空")));
                return false;
            }
            //注释此部分可临时放开权限验证
            Claims claims = JwTokenUtil.getInstance().parseToken(token);
            if (ObjectUtil.isEmpty(claims)) {
                response.setContentType("application/json;charset=utf-8");
                response.getWriter().write(JacksonUtil.bean2Json(ApiResponse.tokenErr("token校验失败")));
                return false;
            }
            UserContext.setUserId(claims.get(UserContext.CONTEXT_KEY_USER_ID,String.class));
            UserContext.setUserName(claims.get(UserContext.CONTEXT_KEY_USER_NAME,String.class));
            UserContext.setTenantId(claims.get(UserContext.CONTEXT_KEY_USER_TENANT_ID,String.class));
            System.out.println("token校验成功");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        UserContext.remove();
    }
}
