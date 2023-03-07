package hello.servlet.domain.memeber;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*  동시성 문제가 있다.. 동시성문제 관련시 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려한다.
* */

public class MemberRepository {
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    //싱글톤으로 만들어주기
    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }

    // private 설정으로 생성하지 못하게 막기!
    private MemberRepository(){
    }

    // 메소드
    public Member saved(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id){
        return store.get(id);
    }

    public List<Member> findAll(){
        return new ArrayList<>(store.values());
    }

    public void clearStore(){
        store.clear();
    }

}
