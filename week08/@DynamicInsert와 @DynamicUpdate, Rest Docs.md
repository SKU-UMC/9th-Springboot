# @DynamicInsert와 @DynamicUpdate, Rest Docs

# @DynamicInsert와 @DynamicUpdate

## 기존 JPA

---

JPA가 엔티티를 DB에 추가하거나 수정할 때 기본전략은 엔티티의 모든 필드를 업데이트하는 방식이다.

즉, 의도한 추가/수정한 필드(칼럼) 외의 테이블의 모든 필드 값이 추가되거나 수정되는 것이다.

예를 들어,

Member 엔티티의 name과 age를 수정하려고 할 때

```sql
UPDATE Member SET name=?, age=?
```

이런 쿼리가 작동한다고 생각한다.

하지만 실제 작동하는 쿼리는

```sql
UPDATE Member SET id=?, name=?, age=?, role=?, email=? ...
```

의도된 필드 외의 필드도 포함하여 update가 진행된다.

더 자세히 말하면,

`findById()` 등으로 **영속성 컨텍스트**에 각 필드들을 캐싱해놓고, `member.setName()` 등의 메소드가 실행되어 `update`가 실행되면, JPA는 **더티 체킹**을 진행한다.

처음 조회했을 때의 값과 현재 값을 비교하여 수정이 일어나면 위에 서술한 전체 필드 `update` 쿼리를 실행하는 방식이다.

`insert`도 마찬가지로,

`name`과 `age` 값만 설정하고 실행하여도

```sql
INSERT INTO Member (id, name, age, role, email) values (?, ?, ?, ?, ?)
```

전체 필드 추가 쿼리가 실행된다. (지정하지 않은 role, email 등은 null 값)

## @DynamicInsert

---

`@DynamicInsert`가 적용되면 전체 필드 insert가 아닌 특정 필드만 insert하는 쿼리가 실행된다.

```sql
INSERT INTO Member (id, name, age, role, email) values (?, ?, ?, ?, ?)
```

```sql
INSERT INTO Member (name, age) values (?, ?)
```

## @DynamicUpdate

---

`@DynamicUpdate`가 적용되면 전체 필드 update가 아닌 특정 필드만 update하는 쿼리가 실행된다.

```sql
UPDATE Member SET id=?, name=?, age=?, role=?, email=? ...
```

```sql
UPDATE Member SET name=?, age=?
```

## 왜 JPA 구현체는 불필요하게 모든 필드를 업데이트할까?

---

전체 필드를 업데이트하면 DB에 보내지는 데이터 전송량이 증가한다는 단점이 있지만,

항상 같은 쿼리를 사용하기 때문에 애플리케이션 로딩 시점에 쿼리를 미리 생성해두고 재사용할 수 있고,

DB에 동일한 쿼리를 보내면 DB는 이전에 파싱된 쿼리를 재사용할 수 있다.

`@DynamicInsert`, `@DynamicUpdate`는 매번 쿼리를 동적으로 생성해야한다.

## 언제 적용하면 좋을까?

---

```java
@DynamicInsert
@DynamicUpdate
@Entity
public class Member {
    // ...
}
```

몇 개의 필드만 자주 수정되거나, 필드의 개수가 매우 많을 경우 적용하면 좋다.

# Rest Docs

## Rest Docs란?

---

테스트 코드를 기반으로 API 문서를 생성해주는 도구

개발자가 작성한 테스트가 성공해야만 문서가 생성되기 때문에, 항상 실제 API의 동작과 일치하는 정확한 문서를 보장

문서가 코드를 따라가지 못하는 문제를 해결한다.

## Swagger와 Rest Docs

---

### Swagger

**장점**

- API를 테스트해 볼 수 있는 화면을 제공하여 **간편하게 동작 테스트** 가능

**단점**

- 코드에 **어노테이션을 직접 작성**해야 하기 때문에 코드가 지저분해지고, 가독성이 떨어진다.
- 테스트 기반이 아니기에 **문서가 정확하다고 확신할 수 없다.**
- 모든 오류에 대한 여러 가지 응답을 문서화할 수 없다.

### Rest Docs

**장점**

- 테스트 기반으로 문서가 작성되어 테스트 코드가 일치하지 않으면 빌드가 실패하기 때문에 **문서를 신뢰**할 수 있다.
- 테스트 코드에서 명세를 작성하기 때문에 비즈니스 로직의 **가독성에 영향을 미치지 않는다.**
- 문서화 하기 위해 **반드시 테스트 코드를 작성**해야 하므로, **안정적인 애플리케이션**을 만들도록 유도한다.
- 생성되는 **HTML, PDF** 등의 디자인과 구조를 원하는 대로 **커스터마이징**이 가능하다.

**단점**

- 문서화를 위해 **반드시 테스트 코드를 작성**해야 하므로, **불편하고 개발 속도가 더딜 수 있다.**
- Swagger UI처럼 문서 페이지에서 바로 API를 테스트해 볼 수 있는 기능은 제공하지 않는다.

## 언제 사용하면 좋을까?

---

**Swagger**

- 빠른 개발 속도와 편리한 테스트가 중요할 때

**Rest Docs**

- 문서의 정확성과 신뢰도가 중요하고, 코드의 깔끔함을 유지하고 싶을 때