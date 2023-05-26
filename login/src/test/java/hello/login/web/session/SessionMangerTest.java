package hello.login.web.session;

import hello.login.domain.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionMangerTest {

    SessionManager sessionManager = new SessionManager();

    @Test
    void sessionTest(){


        //세션생성 ( 서버 -> 클라이언트)
        MockHttpServletResponse response = new MockHttpServletResponse(); // 스프링이 가짜 객체를 제공!!
        Member member = new Member();
        sessionManager.createSession(member,response); // response->>>

        //요청에 응답 쿠키저장..
        // ( 클라이언트 -> 서버)
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setCookies(response.getCookies()); // mySessionId = 123123-12312-31-23123qwe


        // 세션 조회
        Object result = sessionManager.getSession(request);
        Assertions.assertThat(result).isEqualTo(member);

        // 세션 만료
        sessionManager.expire(request);
        Object expired = sessionManager.getSession(request);
        Assertions.assertThat(expired).isNull();
    }
}
