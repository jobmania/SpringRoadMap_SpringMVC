package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.v4.ControllerV4;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name ="frontControllerV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();  // 모든 콘트롤러 주입 가능 .
    private final List<MyHandlerAdaptor> handlerAdaptors = new ArrayList<>();
    
}
