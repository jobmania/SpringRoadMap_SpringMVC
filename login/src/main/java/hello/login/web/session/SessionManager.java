package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "mySessionId"; // 상수로 만들어서 실수를 예방 (ctrl+ alt + c)
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>(); // 여러 쓰레드가 접근하기 때문에!


   /**
    *  세션 생성
    * */
   public void createSession(Object value, HttpServletResponse response){
       //sessionId 생성,  값을 세션에 저장.
       String sessionId = UUID.randomUUID().toString();
       sessionStore.put(sessionId, value);

       // 쿠키 생성
       Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
       response.addCookie(mySessionCookie);
   }

   public Object getSession(HttpServletRequest request){
//       Cookie[] cookies = request.getCookies();
//       if(cookies ==null){
//           return null;
//       }
//
//       for (Cookie cookie : cookies) {
//           if(cookie.getName().equals(SESSION_COOKIE_NAME)){
//               return sessionStore.get(cookie.getValue());
//           }
//       }
//       return null;

       Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
       if(sessionCookie==null){
           return null;
       }

       return sessionStore.get(sessionCookie.getValue());
   }

   /**
    * 세션만료
    * */
   public void expire(HttpServletRequest request){
       Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
       if(sessionCookie !=null){
           sessionStore.remove(sessionCookie.getValue());
       }
   }


   public Cookie findCookie(HttpServletRequest request, String cookieName){
       if(request.getCookies()==null){
           return null;
       }

       return Arrays.stream(request.getCookies())
               .filter(c -> c.getName().equals(cookieName))
               .findFirst()
               .orElse(null);

   }

}
