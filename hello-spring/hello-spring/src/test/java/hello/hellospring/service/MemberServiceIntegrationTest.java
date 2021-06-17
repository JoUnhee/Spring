package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/*
* @Transactional에 대한 이해가 필요함
* 각 테스트 케이스를 실행하기 전에 트랜잭션을 걸고, 테스트가 완료되면 롤백하여 무효화한다.
* */

@SpringBootTest /* @SpringBootTest : Spring config대로 모든것을 다 올림 (스프링이 실제로 실행이 된다) */
@Transactional  /* @Transactional  : Test를 실행할 때 transaction을 실행하고, DB에 query 발행 후, test가 끝난 뒤에 rollback 수행 -> DB에 넣었던 data가 남아있지않게 됨 @test 단위 */
class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    /* 
    * 단위 테스트 : 순수한 java service에 대한 검증을 위한 test?? 최소한의 단위로 진행하는 test
    * 통합 테스트 : 이 프로젝트에서는 spring, db 연동 모두를 포함한 test
    * 
    * 가급적이면 순수한 단위 테스트가 훨씬 좋은 테스트일 확률이 높다 -> Q. 작은 단위부터 로직 검증등을 할 수 있기 때문인가 ,,?
    * 
    * Guess : 통합 test처럼 부피가 커지게 되면, testing 로직이 잘못 구성될 수 있기 때문
    * 
    * A.    : 단위 테스트는 주로 서비스 로직에 적용. DB를 연동하게 된다면 단위 테스트가 어려워 진다. 따라서 테스트 전용 repository를 만들어 테스트 시점에 넣어주어야 한다.
    * 이것을 mock 객체라고하는데, 단순히 테스트를 위해서 진짜 db가 아니라 가짜 객체를 하나 만들어서 넣어준다고생각하면 편함. 하지만 이런 가짜 객체를 일일히 개발자가 만든다고 하면
    * 공수가 많이 들기 때문에 mockito같은 라이브러리를 이용, 가짜 객체를 알아서 만들어주도록 한다.
    */
    @Test
    //@Commit : 이 annotation으로 commit하도록 지정할 수 있음 | @Rollback(false)를 지정하면 rollback 수행이 안됨
    void 회원가입() {
        /* DB에 정보가 남아있음 */
        //given : 주어진 data
        Member member = new Member();
        member.setName("jounhee");

        //when : 무엇, 언제 등이 포함됨
        Long saveId = memberService.join(member);

        //then : 검증
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("jounhee");

        Member member2  = new Member();
        member2.setName("jounhee");

        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //then
    }

    @Test
    void 모든_멤버_조회() {
        //given
        Member member1 = new Member();
        member1.setName("jounhee");

        Member member2 = new Member();
        member2.setName("jeanLee");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        List<Member> OF_Batcher = memberService.findMembers();
        assertThat(OF_Batcher.size()).isEqualTo(2);
    }

    @Test
    void 단일_멤버_조회() {
        //given
        Member member1 = new Member();
        member1.setName("jounhee");

        /* shift + F6  : 선언되어있는 변수명 한번에 치환 */
        Member member2 = new Member();
        member2.setName("jeanLee");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        Optional<Member> unhee = memberService.findOne(member1.getId());
        assertThat(unhee.get().getName()).isEqualTo("jounhee");
    }
}