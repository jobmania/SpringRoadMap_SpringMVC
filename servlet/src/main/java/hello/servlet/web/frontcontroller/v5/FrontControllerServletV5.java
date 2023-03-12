package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adator.ControllerV3HandlerAdaptor;
import hello.servlet.web.frontcontroller.v5.adator.ControllerV4HandlerAdaptor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name ="frontControllerV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();  // 모든 콘트롤러 주입 가능 .
    private final List<MyHandlerAdaptor> handlerAdaptors = new ArrayList<>();

    public FrontControllerServletV5() { // 매핑
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdaptors.add(new ControllerV3HandlerAdaptor());
        handlerAdaptors.add(new ControllerV4HandlerAdaptor());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());
        // v4 추가!!
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //MemberFormControllerV3()
        //MemberFormControllerV4());
        Object handler = getHandler(request); // handlermapping.get

        if(handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 1. 핸들러 조회  2. 핸들러 어뎁터 조회
        MyHandlerAdaptor adaptor = getHandelrAdapter(handler); // ControllerV3HandlerAdaptor
        // 3. 핸들(handler) 4. handle 호출  5. 모델 뷰 반환
        ModelView mv = adaptor.handle(request, response, handler);

        // 6. 뷰네임 호출,  7 myView 반환 8. render 호출
        String viewName = mv.getViewName();  // 논리 이름 new-form
        MyView view = viewResolver(viewName); // Myview 반환!
        view.render(mv.getModel(), request,response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdaptor getHandelrAdapter(Object handler) {
        //MemberFormControllerV3()
        //MemberFormControllerV4());
        for (MyHandlerAdaptor adapter : handlerAdaptors) {
            if(adapter.supports(handler)){
               return adapter;
            }
        }
        throw new IllegalArgumentException("handler adaptor를 찾을 수 없습니다." + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);

    }
}
