package spring.basicproject.member;

public class MemberServiceImpl implements MemberService{

    /* MemberRepository라는 인터페이스만 존재, 추상화에 의존 -> DIP 준수*/
    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
