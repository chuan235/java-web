package top.gmfcj.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import top.gmfcj.util.CookieUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @description:
 * @author: GMFCJ
 * @create: 2020-03-19 11:43
 */
public class LoginFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = new RequestContext();
        // 被代理的微服务
        String proxy = (String) requestContext.get("proxy");
        HttpServletRequest request = requestContext.getRequest();
        // 请求的URI
        String requestURI = request.getRequestURI();
        // zuul路由后的url
        System.out.println(proxy + "/" + requestURI);
        // 获取客户端的cookie
        String token = CookieUtil.getLoginCookie(request);
        // 将cookie写入请求的header中
        requestContext.addZuulRequestHeader("login_key", token);
        return null;
    }
}
