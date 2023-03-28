package com.ljh.config.interceptor;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import com.ljh.config.annotion.RoleVaildator;
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
        if(handler instanceof HandlerMethod){
            try {
                HandlerMethod handlerMethod = (HandlerMethod) handler;
                if (Objects.requireNonNull(handlerMethod.getMethod()).isAnnotationPresent(TokenValidatorAnnotaion.class)) {
                    TokenValidatorAnnotaion tokenValidatorAnnotaion = handlerMethod.getMethod().getAnnotation(TokenValidatorAnnotaion.class);
                    if (!tokenValidatorAnnotaion.required()) {
                        return true;
                    }
                }
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
                //如果需要具体的权限验证
                if (Objects.requireNonNull(handlerMethod.getMethod()).isAnnotationPresent(RoleVaildator.class)) {
                    RoleVaildator roleVaildator = handlerMethod.getMethod().getAnnotation(RoleVaildator.class);
                    //需要的权限
                    String needRole = roleVaildator.roles();
                    //用户所拥有的权限
                    String userRoleStr=claims.get(UserContext.CONTEXT_KEY_USER_ROLE,String.class);
                    if(StringUtils.isNotEmpty(needRole)){//配置了具体权限
                        if(StringUtils.isEmpty(userRoleStr)){
                            response.setContentType("application/json;charset=utf-8");
                            response.getWriter().write(JacksonUtil.bean2Json(ApiResponse.roleErr("用户未配置权限")));
                            return false;
                        }else{
                            String userRoles[] = JacksonUtil.json2Bean(userRoleStr,String[].class);
                            if(!ArrayUtil.contains(userRoles,needRole)){//用户没有该权限
                                response.setContentType("application/json;charset=utf-8");
                                response.getWriter().write(JacksonUtil.bean2Json(ApiResponse.roleErr("权限校验失败")));
                                return false;
                            }
                        }
                    }
                }
                UserContext.setUserId(claims.get(UserContext.CONTEXT_KEY_USER_ID,String.class));
                UserContext.setUserName(claims.get(UserContext.CONTEXT_KEY_USER_NAME,String.class));
                UserContext.setTenantId(claims.get(UserContext.CONTEXT_KEY_USER_TENANT_ID,String.class));
                UserContext.setUserRole(claims.get(UserContext.CONTEXT_KEY_USER_ROLE,String.class));
                System.out.println("校验成功");
                return true;
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }else{
            return true;
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
