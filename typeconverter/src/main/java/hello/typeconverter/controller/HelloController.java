package hello.typeconverter.controller;

import hello.typeconverter.type.IpPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class HelloController {

    @GetMapping("/hello-v1")
    public String helloV1(HttpServletRequest request){
        String data = request.getParameter("data"); // 문자 타입으로 조회가 된다
        Integer integer = Integer.valueOf(data);// 숫자타입으로 변경
        System.out.println("integer = " + integer);
        return "ok";
    }

    @GetMapping("/hello-v2")
    public String helloV2(@RequestParam Integer data){
        System.out.println(data);
        return "se";
    }

    @GetMapping("/ip-port")
    public String ipPort(@RequestParam IpPort ipPort){
        System.out.println(ipPort.getPort());
        System.out.println(ipPort.getIp());
        return "ok";
    }
}
