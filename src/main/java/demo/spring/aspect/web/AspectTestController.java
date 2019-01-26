package demo.spring.aspect.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.spring.aspect.service.IAspectService;

@Controller
@RequestMapping("/aspectTest")
public class AspectTestController extends BaseController {

    private static Logger logger = Logger.getLogger(AspectTestController.class);

    @Autowired
    private IAspectService aspectService;

    @RequestMapping("/addFromTo")
    public void addFromTo(HttpServletRequest request, HttpServletResponse response, long start, long end) {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("start", start);
        dataMap.put("end", end);
        logger.info("params:" + dataMap);
        long result = aspectService.addFromTo(start, end);
        dataMap.put("result", result);
        logger.info("result:" + dataMap);
        renderString(response, dataMap.toString(), "text/json");
    }

    @RequestMapping("/divide")
    public void divide(HttpServletRequest request, HttpServletResponse response, long a, long b) {

        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("a", a);
        dataMap.put("b", b);
        logger.info("params:" + dataMap);
        long result = aspectService.divide(a, b);
        dataMap.put("result", result);
        logger.info("result:" + dataMap);
        renderString(response, dataMap.toString(), "text/json");
    }

}
