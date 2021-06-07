package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service
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
         *
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
