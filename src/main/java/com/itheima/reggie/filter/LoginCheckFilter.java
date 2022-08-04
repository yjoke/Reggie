package com.itheima.reggie.filter;

import cn.hutool.json.JSONUtil;
import com.itheima.reggie.dto.R;
import com.itheima.reggie.util.LoginAccountHolder;
import com.itheima.reggie.util.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.itheima.reggie.util.constant.RConstant.NOT_LOGIN;
import static com.itheima.reggie.util.constant.SessionConstant.*;

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
            "/employee/login",  // 后台登录
            "/employee/logout", // 后台退出
            "/backend/**",      // 后台的静态资源
            "/front/**"         // 前台的静态资源
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

        // 判断是否需要拦截, 直接放行
        if (!needFilter(requestURI)) {
            filterChain.doFilter(request, response);
            return ;
        }

        String loginValue = (String) request.getSession().getAttribute(LOGIN_KEY);

        if (loginValue == null) {
            // 未登录, 返回未登录的输出流
            log.info("拦截到请求: {}, 请求方 ip 为: {}", requestURI, ipAddress);

            response.getWriter().write(JSONUtil.toJsonStr(R.error(NOT_LOGIN)));
            return ;
        }

        LoginAccountHolder.set(loginValue);
        log.info("用户 {} 在 {} 请求路径 {}", loginValue, ipAddress, requestURI);
        filterChain.doFilter(request, response);
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
