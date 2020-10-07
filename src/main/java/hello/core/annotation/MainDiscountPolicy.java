package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;


// Qualifier는 컴파일 때 문자라 오류잡기가 어려움 어노테이션으로 직접만들기
// Qualifier의 어노테이션들 가져오고
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")  // Qualifier의 역할을 한다
public @interface MainDiscountPolicy {
}
