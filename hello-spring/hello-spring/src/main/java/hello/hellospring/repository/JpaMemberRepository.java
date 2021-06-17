package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    /* JPA의 모든것은 Entity Manager로부터 동작
    * build gradle에 JPA 라이브러리를 포함하게 했다면, EntityManager를 Spring에서 알아서 생성한다.
    *
    * JPA는 Mybatis를 완벽하게 cover할 수 있는가 ??
    * 
    * A : JPA 자체가 SQL을 100% COVER하기 위해 만들어진것은 아님. JPA를 실무에서 사용하더라도 SQL을 어느정도는 (5~10%라고하는데 ) 사용해야함
    * 특히 통계성 쿼리같은 경우는 SQL을 사용해야함. JPA를 사용하는 프로젝트들은 JPA를 기본으로 하고, JPA로 할 수 없는 상황에 SQL을 가끔 함께 사용하는 정도로 이해하자
    */
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // JPA가 insert query를 생성하여 알아서 생성해주며, 생성할 때, ID를 알아서 증가도 시켜줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); //동일하게 select query 알아서 발행된다
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        /*
         * Table 대상으로 query를 발행하는 것이 아닌, 객체를 대상으로 query를 발행한다고 생각
         * 정확하게는 Entity를 대상으로 query를 발행하는 것 -> select문 같은 경우는 객체 자체를 select 하는거임 ( id나 name 등 특정 컬럼을 지정하는 것이 아님)
         * PK가 아닌 나머지 coloum들을 조회할 때는 이 JPQL라는것을 작성해야함
         */

        List<Member> resultList = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return resultList.stream().findAny(); // 원래는 Optional 해야됨 ㅎㅎ,, 나중에 해보자
    }

    @Override
    public List<Member> findAll() {
        /* ctrl + alt + shift + T -> inline variable 검색하여 사용*/
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}
