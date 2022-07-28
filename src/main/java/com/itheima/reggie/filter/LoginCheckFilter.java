package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.itheima.reggie.constant.RConstant.NOT_LOGIN;
import static com.itheima.reggie.constant.SessionConstant.EMPLOYEE_LOGIN_SESSION_KEY;

/**
 * 检查用户是否一句完成登录
 *
 * @author HeYunjia
 */

@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    /**
     * 匹配器
     */
    private static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    /**
     * 放行的路径
     */
    private static final String[] URLS = new String[] {
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "/front/**"
    };

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 本次的请求 URI, 和 ip 地址
        String requestURI = request.getRequestURI();
        String ipAddress = IpUtils.getIpAddress(request);

        // 放行的请求路径
        String[] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        // 判断是否需要拦截, 直接放行
        if (!needFilter(requestURI)) {
            log.info("放行请求: {}, 请求方 ip 为: {}", requestURI, ipAddress);
            filterChain.doFilter(request, response);
            return ;
        }

        // 判断是否登录
        Object attribute = request.getSession().getAttribute(EMPLOYEE_LOGIN_SESSION_KEY);
        if (attribute != null) {
            log.info("员工 {} 在 {} 请求路径 {}", attribute, ipAddress, requestURI);
            filterChain.doFilter(request, response);
            return ;
        }

        // 未登录, 返回未登录的输出流
        log.info("拦截到请求: {}, 请求方 ip 为: {}", requestURI, ipAddress);

        response.getWriter().write(JSON.toJSONString(R.error(NOT_LOGIN)));
    }

    /**
     * 检查 requestURI 是否需要拦截
     *
     * @param requestURI 要检查的路径
     * @return 不拦截 false, 拦截 true
     */
    private boolean needFilter(String requestURI) {
        for (String url : URLS)
            if (PATH_MATCHER.match(url, requestURI))
                return false;

        return true;
    }

}
