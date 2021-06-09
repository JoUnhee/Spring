
package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new hello.hellospring.repository.MemoryMemberRepository();
    /* Test의 실행 순서는 보장되지 않음
    *  각 Test는 의존관계가 없어야 한다.
    *
    * Test class 생성 후, 개발을 완료 -> TDD
    */

    @AfterEach
    public void afterEach() {
        /* 저장소에 동일한 객체가 삽입되어 해당 메소드를 수행하지 않았을 경우 충돌이 일어남
        *  따라서 하기 메소드로 의존관계를 없애야함함
       */
        repository.clearStore();
    }
    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);
        /* Q. optional에서 바로 get으로 꺼내는게 왜 안좋을까 ???*/
        /* Optional에 대한 이해 필요
         * Optional을 거치지 않고 get으로 꺼낸 경우, Optional에서 정제해주는 정보를 받는게 아닌 직접 찾아버리는 느낌이므로
         * 예외처리 등이 추가해야함
         * Optional에서 만약 NUll을 get할경우 Nullpointerexception이 발생함
         */
        Member result = repository.findById(member.getId()).get();
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("jounhee");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("jounhee2");
        repository.save(member2);

        Member result = repository.findByName("jounhee").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("jounhee");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("jounhee2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);

    }
}