package hello.hellospring;

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*  spring data를 위한 주석처리
    // @PersistenceContext -> 원래 spec, spring에서 알아서 DI 해준다.


    EntityManager em;

    @Autowired
    public SpringConfig(EntityManager em) {
        this.em = em;
    }
*/
    /******** 2021-06-14 entity manager를 위해 주석 처리
     *  Config <-> @Service와 같이 annotation을 바로 쓰는 경우와 비교해보자!

     private DataSource dataSource;

     @Autowired public SpringConfig(DataSource dataSource) {
     this.dataSource = dataSource;
     }
     */

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

/*
    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
*/


//    @Bean
//    public MemberRepository memberRepository() {

        //return new MemoryMemberRepository();
        /* jdbc interface만 확장해서 사용
         * Spring container의 DI를 이용해 다형성으로 수정
         * 개방 폐쇄 원칙
         *   확장에는 열려있고, 수정, 변경(interface 수정해야 되고, 그러면 구현한 모든 클래스에 적용시켜야하므로)에는 닫혀있다.
         *
         */
        //return new JdbcMemberRepository(dataSource);
        //return new JdbcTemplateMemberRepository(dataSource);
        // return new JpaMemberRepository(em);

//    }

}
