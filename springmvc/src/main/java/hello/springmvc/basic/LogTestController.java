package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController  //  body 에 걍 넣어버려~
@Slf4j // 롬복 제공 log
public class LogTestController {

//    private final Logger log = LoggerFactory.getLogger(getClass()); //  현재 클래스 지정!

    @RequestMapping("/log-test")
    public String logTest(){
        String name = "Spring";

        System.out.println("name = " + name); /// ->

        // 로그 레벨 단계 ->
        log.trace("trace log = {}, {}", name, name);
        log.debug("debug log = {}", name);  // 디버그 ) 개발서버에서
        log.info("info log = {} ", name); // 비즈니스 정보 ex) 고객요청중 중요한 정보 같은거
        log.warn("warn log = {}", name); // 경고
        log.error("error log = {}", name); // 에러

        return "ok";
    }

}
