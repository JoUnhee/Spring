package spring.basicproject.scan.filter;

import java.lang.annotation.*;

@Target(ElementType.TYPE)           // TODO : annotation 공부하기
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyIncludeComponent {

}
