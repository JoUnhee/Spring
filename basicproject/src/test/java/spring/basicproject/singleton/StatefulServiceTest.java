package spring.basicproject.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {

    @Test
    void statefulServiceSingleton(){
        // TODO : 멀티 스레드로 구현해보자
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        //Thread A : A 사용자 10000원 주문
        int priceA = statefulService1.order("userA", 10000);

        //Thread B : B 사용자 20000원 주문
        int priceB = statefulService2.order("userB", 20000);

        //Thread A : 사용자 주문 금액 조회
        // statefulService1.getPrice();
        System.out.println("price = " + priceA);

        Assertions.assertThat(priceA).isEqualTo(10000);


    }

    static class TestConfig{

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }

}