package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form){
        return "login/loginForm";
    }

//    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){ /// 각각의 field의 error는 아니고 못찾은 거임ㅇㅇ
            // 글로벌 error 메세지 설정!
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.!");
            return "login/loginForm";
        }

        // 성공 처리 ;

        // 쿠기 설정
        // 쿠키 시간 설정, 설정하지 않을 시 세션쿠키(브라우저 종료시 모두종료)
        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
        response.addCookie(idCookie);
        return "redirect:/";
    }

//    @PostMapping("/login")
    public String loginV2(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){ /// 각각의 field의 error는 아니고 못찾은 거임ㅇㅇ
            // 글로벌 error 메세지 설정!
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.!");
            return "login/loginForm";
        }

        // 성공 처리 ;




        // 세션 관리자를 이용해서 세션  생성, 회원 데이터를 보관!!
        sessionManager.createSession(loginMember,response);


        return "redirect:/";
    }


//    @PostMapping("/login")
    public String loginV3(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){ /// 각각의 field의 error는 아니고 못찾은 거임ㅇㅇ
            // 글로벌 error 메세지 설정!
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.!");
            return "login/loginForm";
        }

        // 로그인 성공 처리 로직
        // 세션이 있다면 세션반환, 없다면 신규 세션을 반환한다.
        HttpSession session = request.getSession(); /// getSession(true) default: true
        // 세션이 로그인 회원 정보를 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);


        // 세션 관리자를 이용해서 세션  생성, 회원 데이터를 보관!!


        return "redirect:/";
    }
    @PostMapping("/login")
    public String loginV4(@Validated @ModelAttribute LoginForm form, BindingResult bindingResult,
                          @RequestParam(defaultValue = "/", name = "redirectURL") String redirectURL,
                          HttpServletRequest request){

        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null){ /// 각각의 field의 error는 아니고 못찾은 거임ㅇㅇ
            // 글로벌 error 메세지 설정!
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.!");
            return "login/loginForm";
        }

        // 로그인 성공 처리 로직
        // 세션이 있다면 세션반환, 없다면 신규 세션을 반환한다.
        HttpSession session = request.getSession(); /// getSession(true) default: true
        // 세션이 로그인 회원 정보를 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER,loginMember);


        // 세션 관리자를 이용해서 세션  생성, 회원 데이터를 보관!!


        return "redirect:"+redirectURL;
    }




//    @PostMapping("/logout")
    public String logout(HttpServletResponse response){
        expireCookie(response,"memberId");
        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logoutV2(HttpServletRequest request){
        sessionManager.expire(request);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if(session!=null){
            session.invalidate(); // 세션 delete
        }

        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response,String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
