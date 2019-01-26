package demo.spring.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/** 针对特定url（AspectTest）的springmvc拦截器 */
public class AspectTestHandlerInterceptor extends CommonHandlerInterceptor {

    private static Logger logger = Logger.getLogger(AspectTestHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean isOk = super.preHandle(request, response, handler);
        logger.info("mvc自定义请求拦截：AspectTestHandlerInterceptor 的处理（空处理）");
        return isOk;
    }
}
