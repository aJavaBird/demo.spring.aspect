package demo.spring.handler;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/** 公共的的springmvc拦截器 */
public class CommonHandlerInterceptor implements HandlerInterceptor {

    private static Logger logger = Logger.getLogger(CommonHandlerInterceptor.class);

    /** 
     * 该方法会在控制器方法前执行，其返回值表示是否中断后续操作。当其返回值为true时，表示继续向下执行；
     * 当其返回值为false时，会中断后续的所有操作（包括调用下一个拦截器和控制器类中的方法执行等）
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        Enumeration enu = request.getParameterNames();
        Map<String, String> reqMap = new HashMap<>();
        while (enu.hasMoreElements()) {
            String paraName = (String) enu.nextElement();
            reqMap.put(paraName, request.getParameter(paraName));
        }
        //        String queueId = UUID.randomUUID().toString().replaceAll("-", "");
        //        request.getSession().setAttribute("queueId", queueId); // 通过session存队列号，是有bug的，应该存redis等地方
        logger.info(String.format("mvc自定义请求拦截-preHandle：请求路径:%s,参数:%s", request.getRequestURI(), reqMap.toString()));
        return true;
    }

    /**
     * 该方法会在控制器方法调用之后，且解析视图之前执行。可以通过此方法对请求域中的模型和视图做出进一步的修改。
     * */
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        logger.info(String.format("mvc自定义请求拦截-postHandle：请求路径:%s,运行结束", request.getRequestURI()));
    }

    /**
     * 该方法会在整个请求完成，即视图渲染结束之后执行。可以通过此方法实现一些资源清理、记录日志信息等工作。
     * */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        logger.info(String.format("mvc自定义请求拦截-afterCompletion：请求路径:%s,运行结束", request.getRequestURI()));
    }
}
