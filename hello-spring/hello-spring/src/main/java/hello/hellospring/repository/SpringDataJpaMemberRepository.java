package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    //JPQL select m from Member m where m.name = ?
    // Interface에 이름만 잘 지어줘도 구현이 끝나벌임 ,,,, 정확하게는 이해가 안되지만 ,,, reflection 기술로 한데 ,,, 슬퍼 ,,
    @Override
    Optional<Member> findByName(String name);
}
