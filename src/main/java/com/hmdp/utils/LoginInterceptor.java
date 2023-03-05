package com.hmdp.utils;

import com.hmdp.dto.UserDTO;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 登录状态拦截器
 * 在访问需要处于登录状态的页面时进行拦截
 * 如果登录状态已失效，则拦截，否则放行
 * 如果为初次登录，则将用户保存到ThreadLocal中
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取session
        HttpSession session = request.getSession();
        //2.获取session域中的用户
        Object user = session.getAttribute("user");
        //3.如果用户为空，则拦截
        if(user == null){
            response.setStatus(401);
            return false;
        }
        //4.如果用户处于登录状态，则保存到ThreadLocal中
        UserHolder.saveUser((UserDTO) user);
        //5.放行
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}
