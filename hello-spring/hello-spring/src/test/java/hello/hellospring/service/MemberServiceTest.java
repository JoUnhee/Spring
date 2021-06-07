package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/* ctrl + shift + T for window
 *
 * Test code는 과감하게 한글로 변경해도 된다
 * shit + F10 : 이전 실행 다시 실행
 */
class MemberServiceTest {

    MemberService memberService;
    /* memberService에 존재하는 repository와 하기에 생성한 객체는 다른 객체이나, static 멤버이기 때문에 동일한 곳에 접근하긴함 */
    //MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given : 주어진 data
        Member member = new Member();
        member.setName("jounhee");

        //when : 무엇, 언제 등이 포함됨
        Long saveId = memberService.join(member);

        //then : 검증
        /* repository를 꺼내야하는데, 일단 memberService에서 꺼내보자
         * 반환값에 맞게 감싸기 단축키 : ctrl + alt + V
         * */
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

        /* 이전 test와 동일하게 clear를 해줘야함 */
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
        // 동일한 예외가 던져지기 때문에 성공할거임
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
/*      예외처리를 try / catch로 할 수 있으나 뭔가 애매함 (왜징 )
        try {
            memberService.join(member2);
            fail("예외가 발생해야 합니다.");
        } catch (IllegalStateException e) {
            // nth
            assertThat(e.getMessage()).isEqualTo("2미 존재하는 회원쓰!");
        }
*/


        //then
    }

    @Test
    void 모든_멤버_조회() {
        //given
        Member member1 = new Member();
        member1.setName("jounhee");

        /* shift + F6 */
        Member member2 = new Member();
        member2.setName("jeanLee");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        List<Member> OF_Batcher = memberService.findMembers();
        assertThat(OF_Batcher.size()).isEqualTo(2);
        //assertThat(OF_Batcher.size()).isEqualTo(3);
    }
    @Test
    void 단일_멤버_조회() {
        //given
        Member member1 = new Member();
        member1.setName("jounhee");

        /* shift + F6 */
        Member member2 = new Member();
        member2.setName("jeanLee");

        //when
        memberService.join(member1);
        memberService.join(member2);

        //then
        Optional<Member> unhee = memberService.findOne(member1.getId());
        assertThat(unhee.get().getName()).isEqualTo("jounhee");
        //assertThat(unhee.get().getName()).isEqualTo("jeanLee");
    }
}