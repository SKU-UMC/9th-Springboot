
## 2주차 핵심 키워드
- **SOLID**
    - SRP(Single Responsibility Principle): 단일 책임 원칙
    - 클래스(객체)는 단 하나의 책임만 져야한다.
    - OCP(Open Closed Priciple): 개방 폐쇄 원칙
    - 확장에는 열려있어야하며 , 수정에는 닫혀있어야한다.예를 들어 , repository가 다른 DB로 변경되었을때 클라이언트 코드의 수정은 없어야하며 , 구현체의 확장은 가능하다.
    - LSP(Listov Substitution Priciple): 리스코프 치환 원칙
    - 프로그램의 어떤 부분에서 부모 클래스(기반 클래스)를 사용하는 경우, 자식 클래스(파생 클래스)로 바꾸더라도 프로그램의 기능이 정상적으로 동작해야 한다.
    - ISP(Interface Segregation Principle): 인터페이스 분리 원칙
    - 클라이언트는 자신이 사용하지 않는 메서드에 의존하지 않아야 한다.
    - DIP(Dependency Inversion Principle): 의존 역전 원칙
    - 상위 모듈은 하위 모듈에 의존해서는 안 된다. 둘 다 추상화에 의존해야 한다.
- **DI**
    - 필요한 객체(의존성)를 직접 생성하지 않고, 외부에서 대신 넣어주는 것
    - 장점 : 유지보수성 용이함  , 테스트 용이성

  • Spring은 @Autowired, @Component, @Service 등으로 객체를 자동으로 관리하고 주입

- **IoC**

  **IoC**는 사용할 객체를 **직접 생성하지 않고**, 객체의 생명주기 관리를 **외부**(스프링 컨테이너)**에 위임**하는 것.

  스프링은 DI를 통해 제어의 역전을 함

- **생성자 주입 vs 수정자, 필드 주입 차이**
    - 생성자 주입

  생성자를 통한 주입 ,**final** 사용 가능

  불변 , 필수 의존관계에서 사용

  **생성자 호출시점 딱 1번만 호출되는것이 보장된다.**

    - 수정자 주입(setter 주입)

  수정자를 통한 주입 ,**final** 사용 불가능

  선택 , 변경 가능성이 있는 의존관계에서 사용

    - 필드 주입

  필드를 통한 주입 **final** 사용 불가능 , 코드가 간결하다.

  **되도록이면 안쓰는걸 권장**

  why? 순수 자바코드에서 테스트가 어려움, final 설정 불가

    - 일반 메서드 주입

  한번에 여러 필드 주입가능 , **final** 사용 불가

  일반적으로 잘쓰이지 않음

- **AOP**

  공통 관심 사항을 핵심 비즈니스 로직에서 분리해서 재사용하는 프로그래밍 방식

  필요한 이유

    - 로그를 남기는 코드, 트랜잭션 처리, 보안 검사 같은 것들은 반복적으로 일어나는 경우가 많음
    - 서비스마다 일일이 작성하면 중복 + 가독성 저하 생김.
    - 따라서 AOP를 사용
    - @Aspect
        - **AOP(관점 지향 프로그래밍)**에서 사용하는 애노테이션으로,이 클래스가 "관점(Aspect)" 역할을 한다고 Spring에게 알려주는 역할 수행공통 기능을 모아놓은 클래스라는것을 Spring에게 알려줌
    - @Around
AOP에서 공통기능을 끼워넣는 방식

      대상 메서드 실행 "전과 후" 모두에 동작하는 애노테이션

      **("execution( *hello.hello_spring..*(..))")**AOP의 실행 범위를 지정하는 표현식hello.hello_spring패키지와 그 하위패키지 전체에 있는 모든 클래스의 모든 메서드에 AOP를 적용하겠다는 뜻

- **서블릿**
    - 자바로 작성된 서버 프로그램 컴포넌트
    - 클라이언트의 요청을 받아 처리한 후 응답을 생성하는 역할을 함
    - WAS와 자바 애플리케이션 사이의 다리역할을 함

    ```jsx
    @WebServlet(name ="responseHeaderServlet" , urlPatterns = "/response-header")
    public class ResponseHeaderServlet extends HttpServlet {
        @Override
        protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
           } 
    ```

  예시 코드

---

## [부록] 스프링 해체 분석기 - Bean
- AbstractAutowireCapableBeanFactory

  AbstractAutowireCapableBeanFactory.createBean() 에서 본격적인 인스턴스화 진행

  **생성자 선택**: `determineConstructorsFromBeanPostProcessors` → 어떤 생성자를 쓸지 결정 (예: `@Autowired` 붙은 생성자).
  **Bean 인스턴스 생성**: `createBeanInstance()`에서 new 키워드 또는 CGLIB 프록시로 생성.

    - **의존성 주입**: `populateBean()` → 생성된 객체에 필요한 필드/세터 주입.
    - **초기화 콜백**: `@PostConstruct`, `InitializingBean.afterPropertiesSet()` 같은 것들 실행.
- BeanPostProcessor
    - **BeanPostProcessor 적용**: AOP 프록시 적용, 커스텀 로직 주입.
- AutowiredAnnotationBeanPostProcessor

  스프링이 제공하는 **BeanPostProcessor 구현체** 중 하나.

    - 이름 그대로 `@Autowired`, `@Value`, `@Inject` 애노테이션을 처리하는 **후처리기(post-processor)**.
    - `ApplicationContext` 초기화 시 자동으로 등록됨.

<Bean 생성 관리>

<--------프로젝트 초기화 단계---------->

@SpringBootApplication ->@ComponentScan 애노테이션을 통해 Bean, Component, Configuration을 찾음
먼저 프로젝트 초기화를 해야함
ComponentScanAnnotationParser 클래스의 parser메서드를 통해 초기화
parser메서드의
return scanner.doScan(StringUtils.toStringArray(basePackages));를 통해 스캔

<--------BeanDefinition 생성---------->

실제로 어노테이션을 찾는 클래스는 ClassPathBeanDefinitionScanner이다.
ClassPathBeanDefinitionScanner클래스

1. 클래스정보를 읽음
   2.Bean으로 등록할 수 있는지 확인 (예: @Component 붙어 있는지).
   3.BeanDefinition 객체 생성
   주입이 가능하면 autowireCandidate=true로 설정
   이 단계까지는 Bean 껍데기 정보만 등록됨 (즉, 인스턴스는 아직 없음).
   위의 정보를 기반으로 bean 세팅

<-----------------BeanFactory 등록 및 싱글톤 관리------------------->

DefaultListableBeanFactory (스프링 기본 BeanFactory 구현체)가 BeanDefinition들을 가지고 있음.
BeanFactory에서 Bean들을 **싱글톤 기법**으로 생성, 관리한다.
필요한 경우, Bean들을 찾아 주입한다
싱글톤 관리 핵심은 SingletonBeanRegistry.

<-----------Bean 생성 과정 (createBean 메서드 중심)---------------------->

AbstractAutowireCapableBeanFactory.createBean() 단계에서 본격적으로 인스턴스화 진행.
1.생성자 선택: determineConstructorsFromBeanPostProcessors → 어떤 생성자를 쓸지 결정 (예: @Autowired 붙은 생성자).
2.Bean 인스턴스 생성: createBeanInstance()에서 new 키워드 또는 CGLIB 프록시로 생성.
3. 의존성 주입: populateBean() → 생성된 객체에 필요한 필드/세터 주입.
4. 초기화 콜백: @PostConstruct, InitializingBean.afterPropertiesSet() 같은 것들 실행.
5. BeanPostProcessor 적용: AOP 프록시 적용, 커스텀 로직 주입.

<----------------Bean 주입 (DI 과정)------------------>

bean의 의존성 파악 , 저장, 주입 AutowireCapableBeanFactory클래스(모든 책임)
실제 구현체는 AbstractAutowireCapableBeanFactory에서 구현
의존성 후보 탐색 과정:
타입 기반 검색 (예: @Autowired UserService userService).
빈 이름 매칭 (동일 타입이 여러 개일 때).
@Qualifier, @Primary 등으로 후보 결정.
주입 성공 시 해당 객체의 필드/생성자에 연결.

<-------------------싱글톤 초기화 (preInstantiateSingleton--------------------->
이렇게 모인 기능들이 "ApplicationContext"이다.
싱글톤 초기화는 ApplicationContext 초기화 시점에 호출됨.
미리 정의된 BeanDefinition들을 순회하면서 싱글톤 Bean들을 전부 인스턴스화.
즉, 등록만 된 BeanDefinition → 실제 객체로 변환하는 최종 단계.

<---------------ApplicationContext 역할------------------->

지금까지 설명한 BeanFactory, SingletonBeanRegistry, BeanPostProcessor 등등을 하나로 묶어 제공하는 것이 ApplicationContext.

---
## [부록] 스프링 해체 분석기 - 전체 구조
[클라이언트 요청]
│

▼

┌─────────────────────┐

│ Servlet Filter │ ← 톰캣 등 서블릿 컨테이너 레벨

│ (ex. LoggingFilter) │

└─────────────────────┘

│

▼

┌─────────────────────┐

│ DispatcherServlet │ ← 스프링 프론트 컨트롤러

└─────────────────────┘

│

▼

┌─────────────────────┐

│ HandlerMapping │ ← 요청 URL → 컨트롤러 매핑

└─────────────────────┘

│

▼

┌───────────────────────────────┐

│ HandlerAdapter                │

│(ex.RequestMappingHandlerAdapter) │

└───────────────────────────────┘

│

▼

┌─────────────────────────────┐

│ Interceptor.preHandle()     │

└─────────────────────────────┘

│

▼

┌─────────────────────────────┐

│ Controller 실행              │

└─────────────────────────────┘

│

▼

┌─────────────────────────────┐

│ Interceptor.postHandle()    │

└─────────────────────────────┘

│

▼

┌─────────────────────────────┐

│ ViewResolver / HttpMessageConverter │

│ → HTML or JSON 변환          │

└─────────────────────────────┘

│

▼

┌─────────────────────────────┐

│ Interceptor.afterCompletion() │

└─────────────────────────────┘

│

▼

[클라이언트 응답]


---

## Filter vs Interceptor

| 구분 | Filter | Interceptor |
|------|--------|-------------|
| 동작 레벨 | 서블릿 컨테이너 | DispatcherServlet 내부 |
| 관리 주체 | 톰캣 등 WAS | 스프링 컨테이너 |
| 주요 목적 | 요청/응답 공통 처리, 로깅, 인코딩 | 컨트롤러 실행 전후 로직, 인증/인가 |
| 구현 방식 | `javax.servlet.Filter` / `OncePerRequestFilter` | `HandlerInterceptor` |
| 실행 시점 | DispatcherServlet 전/후 | Controller 전/후 |

---

## HandlerAdapter

- DispatcherServlet은 다양한 타입의 컨트롤러를 직접 실행하지 않고 **Adapter를 통해 호출**
- 예: `RequestMappingHandlerAdapter` → `@RequestMapping` 기반 메서드 실행
- REST 컨트롤러의 경우 내부적으로 `RequestResponseBodyMethodProcessor`를 통해 **객체 → JSON 변환**

---

## SSR vs REST API

- **SSR (서버 사이드 렌더링)**
    - Controller + ViewResolver
    - 최종 결과: **HTML 반환**

- **REST API**
    - @RestController + HttpMessageConverter
    - 최종 결과: **JSON 반환**

두 방식 모두 동일한 **HandlerAdapter 흐름**을 거치지만, **마지막 변환 단계(ViewResolver vs HttpMessageConverter)** 가 다름.

---

## [부록] 스프링 해체 분석기 -필터


- DelegatingFilterProxyRegistrationBean

  Spring Boot에서 제공하는 편의 클래스

  역할은 **서블릿 컨테이너(Tomcat 등)에 DelegatingFilterProxy를 등록**하면서, 동시에 스프링 빈으로 관리되는 **필터 체인**과 연결해주는 것.
  `DelegatingFilterProxyRegistrationBean`으로 **자동 등록 + 설정가능**

- SecurityFilterAuthConfiguration

  스프링 인증관련 필터체인을 구성하는 설정클래스

    - **톰캣 필터 체인 앞단**에서 동작
    - **DelegatingFilterProxy를 통해 스프링 빈 필터와 연결**
    - **인증/인가 관련 필터들의 순서, URL 매핑, 인증 서비스 연결을 설정**
    - 결국 클라이언트 요청이 DispatcherServlet에 도달하기 전에 **보안 검증을 담당**
- AbstractSecurityWebApplicationInitializer

  Spring Security의 필터 체인을 웹 애플리케이션에 등록하는 역할

  Spring Security 필터 체인을 서블릿 컨테이너(Tomcat 등)에 등록
  이것을 상속받으면 Spring Boot가 아니라 **Spring MVC + Spring Security 환경에서** 필터를 자동 등록.

  <요청흐름>

  클라이언트 요청
  ↓
  톰캣 FilterChain
  ↓
  DelegatingFilterProxy (AbstractSecurityWebApplicationInitializer가 등록)
  ↓
  springSecurityFilterChain (스프링 시큐리티 필터 체인)
    - SecurityContextPersistenceFilter
    - UsernamePasswordAuthenticationFilter
    - ExceptionTranslationFilter
    - FilterSecurityInterceptor
      ↓
      DispatcherServlet

- FilterChainProxy의 VirtualFilterChain 

- FilterChainProxy

    - Spring Security에서 **실제 필터 체인(Security Filter Chain)을 관리하는 “컨테이너”** 역할
    - 톰캣의 **FilterChain** 안에 등록된 **DelegatingFilterProxy**가 호출되면 실제로는 FilterChainProxy가 호출된다.
    - 톰캣 FilterChain과 스프링 Security 필터 체인 연결

  VirtualFilterChain

    - `FilterChainProxy` 안에서는 **매칭된 Security Filter들을 순서대로 실행**하기 위해 **VirtualFilterChain**을 사용

        - VirtualFilterChain = **FilterChainProxy 내부에서 체인을 구현한 객체**

    - 톰캣 FilterChain이 아니라, 스프링 Security 필터만 관리

클라이언트
↓
Filter1.doFilter() [전처리]
↓ chain.doFilter()
Filter2.doFilter() [전처리]
↓ chain.doFilter()
DispatcherServlet (→ Controller → View)
↑
Filter2.doFilter() [후처리]
↑
Filter1.doFilter() [후처리]
↑
클라이언트 응답 (일반적인 필터체인의 경우)

---

(스프링 시큐리티 사용할 경우)

클라이언트 요청
↓
톰캣 FilterChain
↓
DelegatingFilterProxy (서블릿 컨테이너가 관리)
↓
스프링 컨테이너에서 "springSecurityFilterChain" 빈 찾아 실행
↓
여러 Security Filter들 (인증, 인가, 세션 관리, CSRF 등)
↓
DispatcherServlet

<DelegatingFilterProxy→브릿지 역할>

---

## [부록] 스프링 해체 분석기 - 서블릿 & 핸들러

## 요청이 들어오는 구조

1. 클라이언트 → **서블릿 컨테이너** → `ApplicationFilterChain`
    - 필터 체인을 거친 후 마지막에 `servlet.service(request, response)` 호출
2. 이때 `servlet`은 실제 **DispatcherServlet 인스턴스**이지만,

   `service(ServletRequest, ServletResponse)` 메서드가 **HttpServlet**에서 정의되어 있어 처음 디버깅 시에는 HttpServlet로 보임.

   | 클래스 | 역할 |
       | --- | --- |
   | **HttpServlet** | HTTP 메서드(GET, POST, PUT 등) 판단 후 분기 (`doGet`, `doPost` 등) |
   | **HttpServletBean** | 서블릿 + 스프링 Bean 기능 |
   | **FrameworkServlet** | HttpServletBean + ApplicationContext 관리, 초기화, 이벤트 발행 |
   | **DispatcherServlet** | FrameworkServlet 상속, 핵심 요청 처리 로직 구현 (`doService`, `doDispatch`) |
- DispatcherServlet은 **service 메서드를 오버라이드**하지 않음 → HttpServlet의 service가 호출된 것처럼 보임.
- HttpServlet의 service → `HttpServletRequest` / `HttpServletResponse`로 변환 → FrameworkServlet의 service 호출 → DispatcherServlet `doService` → `doDispatch`

## DispatcherServlet 처리 흐름

1. `doDispatch` 실행
    - **HandlerMapping** 통해 요청을 처리할 **Controller** 탐색
        - `@RequestMapping` → `RequestMappingHandlerMapping`
2. **HandlerAdapter** 통해 Controller 호출
    - `RequestMappingHandlerAdapter` 사용
    - 인터셉터 preHandle 호출
    - Controller 메서드 실행
    - 인터셉터 postHandle 호출
3. **응답 처리**
    - `HandlerMethodReturnValueHandlerComposite`에서 적합한 핸들러 선택
    - JSON → `RequestResponseBodyMethodProcessor` → `MappingJackson2HttpMessageConverter` → 직렬화
4. `DispatcherServlet`로 돌아와 후처리
    - 인터셉터 afterCompletion
    - 이벤트 발행, 리소스 정리

## 핵심 포인트

- `servlet.service(request, response)` 호출 시 보이는 HttpServlet은 **DispatcherServlet의 상위 클래스**
- 실제 요청 처리는 DispatcherServlet → HandlerMapping → HandlerAdapter → Controller → ReturnValueHandler → 응답 순서
- JSON 응답은 ViewResolver 없이 MessageConverter를 통해 직렬화
- 인터셉터를 통해 요청 전/후처리 가능
- 스프링 AOP는 Controller 호출 전후에 Advice, Proxy, Reflection 등을 활용해 기능 추가

"필터 체인에서 DispatcherServlet으로 넘어가면, HttpServlet/FrameworkServlet 단계를 거쳐 DispatcherServlet의 doDispatch가 실제 컨트롤러를 호출하고, 응답은 MessageConverter가 JSON 직렬화 후 반환한다.”

