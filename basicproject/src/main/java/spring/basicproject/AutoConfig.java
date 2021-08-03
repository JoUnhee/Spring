package spring.basicproject;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // 기존에 만들어 두었던 Config가 많아 필터에 등록하여 Scan대상에서 제외
        // 일반적으로 제어하지 않음
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoConfig {
}
