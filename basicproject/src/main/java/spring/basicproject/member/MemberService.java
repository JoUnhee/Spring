package spring.basicproject.member;

public interface MemberService {
    /* 조회, 회원 가입, 구입*/

    void join(Member member);

    Member findMember(Long memberId);
}
