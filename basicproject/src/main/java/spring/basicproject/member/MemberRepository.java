package spring.basicproject.member;

public interface MemberRepository {
    /*
     * CRUD 구현 (Create / Read / Update / Delete)
     * join / find / update ? / delete ?
     */
    void save(Member member);

    Member findById(Long memberId);

}
