package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component //: component로 scan하여 사용해도 되지만 springconf에 @bean으로 관리하는게 더 편할수도 있음
public class TimeTraceAop {
    /*
     * helloController -> memberService (기존 방식)
     *
     * (AOP 적용 후 )                                 joinPoint.proceed()
     * helloController  -------->       프록시 객체         -------->      memberService
     *                                 memberService
     *
     *
     * Container에 Spring bean을 등록할 때, AOP가 적용되어 있다면, 프록시 객체를 앞선에 같이 등록한다.
     * 이후 joinPoint.proceed()를 통해 실제 memberService에 구현되어 있는 method들이 실행된다.
     *
     *
     * 현 예제에서 Component가 아닌, @Bean으로 등록하면 순환 참조 오류가 발생
     * @Around의 코드를 열어보면, SpringConfig의 timeTraceAop 메서드도 AOP 처리하게 되어있다. 근데 이게 바로 자기 자신인 Time Trace Aop를 생성하는 코드
     * 따라서 순환 참조가 발생 -> 반면 componentscan을 활용하면 AOP의 대상이 되는 이런 코드 자체가 없기 때문에 문제가 발생하지 않는다.
     * 만약 bean으로 등록하여 사용하고 싶다면, AOP 대상에서 SpringConfig가 빠질 수 있게끔 Around를 수정해줘야함
     * ex : @Around("execution(* hello.hellospring..*(..)) && !target(hello.hellospring.SpringConfig)")
     *
     *
     * +++ Interceptor라는 친구도 존재하는데, HandlerInterceptor의 기능적인 측면을 봤을 때 AOP와 동일해보임
     * 차이점은 Interceptor는 웹에 특화, 따라서 웹과 관련된 기술을 사용하는 포인트에서만 사용할 수 있어서 범용성이 없지만, AOP는 범용성이 크다
     * 실무에서 사용하는 AOP의 예로는, @Transactional, @Async를 통한 비동기 처리
     *
     * */

    @Around("execution(* hello.hellospring..*(..))") // 공통 관심사 적용 대상
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}
