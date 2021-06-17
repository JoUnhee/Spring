package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//@Service

/* 2021-06-14
*  JPA를 사용하기 위해서는 항상 @Transactional이 있어야 한다고 뭉갬
*  Data를 변경하거나 저장할때는 해당 어노테이션이 필요하다고 한다.


* * ***** @Transactional은 해당 클래스에 트랜잭션 기능이 추가된 프록시 객체가 생성되고, 해당 프록시 객체는 @Transactional이 포함된 method를 호출할 경우, PlatformTransactionManager를 사용
        정상 여부에 따라 Commit 또는 rollback을 수행한다.
*
* 통상적인 @Transactional의 동작은 해당 메서드가 실행될 때 트랜젝션을 시작하고, 해당 메서드가 끝날 때 커밋을 해서 데이터베이스에 전달된 내용을 확정한다.
* <-> 그럼 test code에서는 왜그렇게 했을 까? : testcode에서는 어노테이션을 활용, 롤백을 해서 DB에 전달된 data를 모두 삭제한다.  ???? 이거 물어보면 되겠다...
* testcode에서는 rollback 하는거 같은데,,, 이 service에서는 commit을 수행하는데 왜그러지? -> test에서는 자동적으로 test가 종료되면 rollback 해준다.
*
*  Service 클래스 안의 각 메소드가 각각의 논리적으로 독립된 비즈니스적 역할을 의미, 따라서 이 메소드의 수행을 기반으로 @Transactional을 잡아야 하는것 아닌가 -> 원자성 보장 같은 느낌?
* 그럼 Repository에서도 @Transactional을 사용해도 문제가 없지 않을까??
*
* -> A : 사용해도 된다. 하지만 트랜잭션은 보통 비즈니스 로직을 실행할 때 사용하는 것이 가장 적절하다. 왜냐하면 비즈니스 로직에 문제가 생기면 한번에 해당 비즈니스 로직을 롤백할 수 있기 때문임
* 따라서 비즈니스 로직 단위로 트랜잭션을 사용한다.
* */
@Transactional
public class MemberService {

    /* service는 buisness logic을 구현하므로, buisness 적으로 명명해야함 */
    private final MemberRepository memberRepository;

    @Autowired // MemoryMemberRepository (spring container에 존재)를 맵핑
    public MemberService(MemberRepository memberRepository) {
        /* Dependency injection */
        this.memberRepository = memberRepository;
    }

    /*
     * 회원 가입
     */
    public Long join(Member member) {

        /* 중복 회원 방지
         *  Optional을 통해 null일 가능성이 있는 경우를 처리하는 부수적인 작업을 줄인다.
         */

        /*
            *** Optional 사용하는 꼴이 안예쁘니까 다른 방법 찾아보자 ***

        Optional<Member> result = memberRepository.findByName(member.getName());
        result.ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

         */
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /* extract method | window : ctrl + alt + m*/
    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /*
     *  회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /*
     * 단일 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
