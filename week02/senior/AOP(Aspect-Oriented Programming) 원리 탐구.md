# AOP(Aspect-Oriented Programming) 원리 탐구

## AOP란?

AOP(Aspect-Oriented Programming, 관점지향 프로그래밍)

애플리케이션의 **핵심 기능**과 여러 곳에서 반복적으로 나타나는 **부가 기능(횡단 관심사, Cross-Cutting Concerns)**을 분리하여 모듈화하는 프로그래밍 패러다임이다.

- **핵심 기능(Core Concern)**: 게시글 등록, 상품 주문 등의 핵심 비즈니스 로직
- **부가 기능(횡단 관심사, Cross-Cutting Concerns)**: 로깅, 트랜잭션 관리, 보안, 성능 측정 등 여러 비즈니스 로직에 공통적으로 적용되는 부가 기능

즉, AOP는 어떤 로직을 **핵심적인 기능**과 **부가적인 기능**의 **관점**으로 나누어 **모듈화**하는 것을 말한다.

---

## OOP와 AOP의 차이점

OOP(Object-Oriented Programming, 객체지향 프로그래밍)

OOP는 **모든 데이터를 현실에 빗대어 객체로 다루는 프로그래밍 기법**을 말한다.

- 특징: 캡슐화, 정보 은닉, 추상화, 상속성, 다형성

AOP는 OOP를 더욱 발전 시키기 위한 개념이다.

- 하나의 소프트웨어가 거대한 OOP로써 설계, 프로그래밍 되었다면, AOP는 **분리를 허용**함으로써 **모듈성을 증가**시키는 것이 목적인 프로그래밍 패러다임이다.

AOP는 프로그램 구조에 대한 또 다른 사고방식을 제공함으로써 OOP를 보완한다.

- OOP에서 **모듈화의 핵심 단위**는 **Class(클래스)**인 반면, AOP에서 모듈화의 단위는 **Aspect(관점)**이다. AOP는 Aspect를 사용하여 **여러 타입과 객체에 걸쳐 있는 관심사를 모듈화**한다.

---

## AOP의 핵심 개념

**Advice, JoinPoint, Pointcut, Aspect, Weaving**

- **Advice**:
    - 실제 실행될 **부가기능(횡단 관심사)** 코드
    - 특정 **JoinPoint**에서 **Aspect**가 취하는 조치(action)
    - Advice 종류
        - **Before Advice**: JoinPoint **실행 이전** 수행
        - **After Returning Advice**: JoinPoint **실행 성공 이후** 수행
        - **After Throwing Advice**: JoinPoint **예외 발생**했을 때 수행
        - **After Advice**: JoinPoint 실행 성공 여부와 관계없이 **항상** 실행
        - **Around Advice**: JoinPoint 실행 **이전과 이후 모두**에 개입 가능 (Before + After)
- **JoinPoint**:
    - 애플리케이션 실행 흐름에서 **Aspect를 적용할 수 있는 모든 지점**
    - **메서드 실행**이나 **예외 처리**와 같이 **프로그램 실행 중의 한 지점**을 말한다.
    - Spring AOP에서 JoinPoint는 주로 **메서드 실행**을 나타낸다.
- **Pointcut**:
    - 여러 **JoinPoint** 중 **Advice**를 적용할 특정 지점을 선별하는 **표현식(Expression)**
    - Advice는 Pointcut 표현식과 연관되며, 지정한 JoinPoint에서 실행된다.
    - Pointcut 표현식으로 매칭되는 JoinPoint의 개념은 AOP의 핵심
- **Aspect**:
    - **부가기능(횡단 관심사)의 모듈화**
    - **Advice + Pointcut**
    - Spring에서 일반 클래스에 `@Aspect` 어노테이션을 사용하여 구현된다.
- **Weaving**:
    - Pointcut에 의해 지정된 지점에 **Aspect**를 **적용하여 핵심 로직과 공통 관심사를 하나로 합치는 과정**
    - Weaving이 **일어나는 시점**에 따라 세 가지로 나눌 수 있다.
        - **컴파일 타임(Compile-time) Weaving**: 컴파일 시점에 AOP 코드가 원본 코드에 삽입되어 새로운 클래스 파일이 생성된다.
        - **로드 타임(Load-time) Weaving**: 클래스가 JVM에 로드될 때 AOP 코드가 적용된다.
        - **런타임(Run-time) Weaving**: 애플리케이션 실행 중에 AOP 코드가 적용된다.
    - **Spring AOP**는 주로 **런타임 Weaving**을 사용하며, 대상 객체에 대한 **프록시(Proxy)**를 만들어 런타임에 **Aspect**를 실행한다.

정리하면,

**Advice**는 실제 부가기능을 의미하고,

**JointPoint**는 부가기능이 적용가능한 위치를 나타내며,

**Pointcut**은 여러 JoinPoint 중 Advice를 적용할 특정 지점을 나타내는 표현식이다.

**Aspect**는 Advice와 Pointcut을 합친 모듈을 의미하고,

**Weaving**은 Aspect이 핵심기능과 결합하는 과정이다.

---

## 런타임 위빙 vs 컴파일 타임 위빙

런타임 위빙과 컴파일 타임 위빙의 가장 핵심적인 차이는 **"언제 횡단 관심사 코드(Aspect)를 핵심 비즈니스 로직에 결합(Weaving)하는가"**이다.

### 런타임 위빙

런타임 위빙은 **애플리케이션이 실행되고 있을 때 AOP를 적용**한다. 런타임에 클라이언트가 메소드 호출 시 Weaving이 이루어지는 방식이다.

런타임 위빙은 **Spring AOP가 사용하는 대표적인 방식**으로, **프록시(Proxy) 기술을 기반으로 동작**한다. 

클라이언트가 객체를 사용할 때, 스프링 컨테이너는 **대상 객체 대신** **프록시 객체를 제공**하고, 프록시 객체는 **실제 객체의 메소드를 호출하기 전/후로 Advice를 실행**한다.

**장점**

- 소스파일, 클래스 파일에 변형이 없다
- 별도의 컴파일러가 필요 없다.

**단점**

- Point Cut에 대한 Advice 수가 늘어날수록 성능이 떨어진다

### 컴파일 타임 위빙

컴파일 타임 위빙은 **컴파일할 때 원본 코드를 직접 수정하여 AOP를 적용**한다.

이 방식은 **컴파일러**가 .java 소스 코드를 컴파일하여 .class 바이트 코드를 만드는 과정에서 **핵심 기능 앞뒤로 Advice를 삽입하는 방식**이다.
**AspectJ**가 이 방식을 사용하며, **ajc**라는 컴파일러가 필요하다.
장점

- 3가지 위빙 중에서는 **가장 빠른 퍼포먼스**를 보여준다.

단점

- 컴파일 과정에서 lombok과 같이 컴파일 과정에서 코드를 조작하는 플러그인과 충돌이 발생할 가능성이 아주 높다. (거의 같이 사용 불가)
- 별도의 컴파일러가 필요하다

---

## 어노테이션 작동 방식 with AOP

Ex) `@Transactional`의 작동방식

1. 애플리케이션이 실행되고 Bean들을 스캔하며 컨테이너에 등록할 시점에 `@Transactional`이 붙은 메소드가 있다면, 스프링은 **원본 객체를 감싸는 프록시 객체를 동적으로 생성**하여 **스프링 컨테이너에 등록**한다.
2. 런타임에 `@Transactional`이 붙은 **메서드가 호출**되면, **프록시 객체가 메서드 호출을 가로채서 실행**된다. 
3. **프록시 객체**는 미리 정의된 **부가 기능(Advice)를 실행**한다.
    1. **Before Advice**: 트랜잭션 시작
    2. **원본 메서드 실행**: 원본 객체의 메서드를 호출
    3. **After Returning Advice**: 메서드가 예외 없이 성공적으로 끝나면, 트랜잭션을 커밋(Commit)
    4. **After Throwing Advice**: 메서드 실행 중 예외가 발생하면, 트랜잭션을 롤백(Rollback)
4. 최종 결과를 클라이언트에게 반환하여 작동을 마친다.

즉, 개발자가 클래스나 메서드에 **어노테이션을 붙이면** 스프링에서는 해당 객체에 대한 **프록시 객체를 생성**하여 **Bean으로 등록**한다. 이후 해당 **Bean을 주입받은 객체가 실행**될 때, **프록시 객체가 먼저 실행**되어 **부가기능(Advice)를 수행**하고 그 후 **원본 객체가 실행**된다.

- **주의점**
    - 프록시 객체를 Bean으로 등록하는 것이기 때문에, 외부에서 Bean을 주입받아 사용하는 객체가 아니라면 해당 방식이 적용되지 않는다.
    - 예를 들어, 객체 내부에서 this.method() 와 같이 내부 메서드를 실행하면, Bean으로 등록된 객체를 주입받아 사용하는 것이 아니기 때문에 해당 방식이 적용되지 않는다.

## Spring AOP와 프록시 패턴(Proxy Pattern)

Spring AOP는 프록시(Proxy) 패턴을 기반으로 동작한다. 기존 코드를 수정하지 않고, 프록시 객체를 통해 부가 기능(Advice)을 추가하는 것이 핵심이다.

- 프록시 패턴이란?
    
    소프트웨어 디자인 패턴 중 하나로 오리지널 객체(Real Object) 대신 프록시 객체(Proxy Object)를 사용해 로직의 흐름을 제어하는 디자인 패턴
    

### 작동 원리

1. **프록시 생성**: Spring 컨테이너는 AOP가 적용된 Bean에 대해 실제 객체(Target)를 감싸는 프록시 객체를 생성
2. **메서드 호출 가로채기**: 클라이언트가 Bean의 메서드를 호출하면, 실제 객체가 아닌 프록시 객체가 먼저 호출을 받는다.
3. **부가 기능(Advice) 실행**: 프록시 객체는 메서드 호출 전후에 AOP 설정에 따라 정의된 부가 기능(Aspect의 Advice)을 실행
4. **실제 객체(Target) 메서드 호출**: 부가 기능 실행 후, 프록시 객체는 실제 객체의 메서드를 호출
5. **결과 반환**: 실제 객체의 실행 결과를 클라이언트에게 반환

**요약: Client 요청 → 프록시의 부가기능(Advice) 먼저 실행 → 실제 객체(Target) 실행 → 결과 반환**

![AOP 프록시 적용 전](./image/%ED%94%84%EB%A1%9D%EC%8B%9C1.png)

AOP 프록시 적용 전

![AOP 프록시 적용 후                                          ](./image/%ED%94%84%EB%A1%9D%EC%8B%9C2.png)

AOP 프록시 적용 후                                          

> 사진출처: [https://ik0501.tistory.com/entry/Spring-Spring-AOP-프록시-패턴](https://ik0501.tistory.com/entry/Spring-Spring-AOP-%ED%94%84%EB%A1%9D%EC%8B%9C-%ED%8C%A8%ED%84%B4)
> 

### JDK 동적 프록시와 CGLIB 프록시

Spring은 프록시 객체를 생성하기 위 해 두 가지 기술을 사용한다.

어떤 기술을 사용할지는 **AOP 적용 대상(Target)**이 **인터페이스를 구현했는지 여부에 따라 결정**된다.

**JDK 동적 프록시(JDK Dynamic Proxy)**

- JDK에서 제공하는 Dynamic Proxy는 인터페이스를 기반으로 프록시를 생성해주는 방식이다.
- 인터페이스를 기반으로 프록시를 생성해주기 때문에 인터페이스의 존재가 필수적이다.
- Java의 Reflection 패키지에 존재하는 Proxy라는 클래스를 통해 Proxy 객체를 생성한다.

**CGLib**

- CGLib는 인터페이스가 아닌 클래스 기반으로 바이트코드를 조작하여 프록시를 생성한다.
- Spring Boot에서는 CGLib를 Default로 사용한다.

둘 모두 런타임 위빙 방식이며 프록시 패턴으로 동작한다.

> 참고자료
> 
> 
> [https://engkimbs.tistory.com/entry/스프링AOP](https://engkimbs.tistory.com/entry/%EC%8A%A4%ED%94%84%EB%A7%81AOP)
> 
> [https://docs.spring.io/spring-framework/reference/core/aop/introduction-defn.html](https://docs.spring.io/spring-framework/reference/core/aop/introduction-defn.html)
> 
> [https://velog.io/@k7nsuy/3관점-지향-프로그래밍AOP-객체-지향-프로그래밍OOP](https://velog.io/@k7nsuy/3%EA%B4%80%EC%A0%90-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8DAOP-%EA%B0%9D%EC%B2%B4-%EC%A7%80%ED%96%A5-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8DOOP)
> 
> [https://velog.io/@dnjwm8612/AOP-Weaving-Proxy](https://velog.io/@dnjwm8612/AOP-Weaving-Proxy)
> 
> [https://f-lab.kr/insight/understanding-spring-aop-and-proxy](https://f-lab.kr/insight/understanding-spring-aop-and-proxy)
> 
> [https://will-of-rough.tistory.com/58](https://will-of-rough.tistory.com/58)
> 
> [https://velog.io/@suhongkim98/JDK-Dynamic-Proxy와-CGLib#jdk-dynamic-proxy](https://velog.io/@suhongkim98/JDK-Dynamic-Proxy%EC%99%80-CGLib#jdk-dynamic-proxy)
>