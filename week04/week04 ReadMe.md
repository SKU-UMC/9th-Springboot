<해보면서 기본개념 정리>

- 빌더는 **필드가 많거나 선택적일 때, 객체 초기화 가독성이 중요할 때, 테스트용 객체 생성 시** 유리
- 엔티티는 **불변성 유지, 상속 구조, 필드 제한적**이면 생성자만으로 충분
- **리뷰 조회와 사진 조회가 독립적**이고, 단순히 사진 관리만 하면 되는 상황 → 단방향으로 충분
- `fetch = FetchType.*LAZY` - 지연 로딩 , 기본값 eager 필요할때까지 엔티티에서 가져오지 않음*
- Lazy는 중간 테이블같은 join데이터를 무조건 필요로 할때는 사용하지 않을것임
- 중간테이블을조회할때는무조건참조값들을가져와야한다고생각함
- users-usermission-mission 데이터를 조회할때는 거의 필수적으로 user랑 mission의 데이터가 들어가서 lazy를 굳이? 쓸이유가 없는데 만약 모든 데이터를 success_at을 기준으로 성공한 미션 같은 데이터를 뽑아낸다면 ? lazy를쓰는게 이득임
- 그래서 내린 결론 - lazy는 중간 테이블로 사용할때 fk만 들어있는 table이면 사용안해도된다. 근데 중간테이블에 다른 데이터들이 속하고있을경우에는 확장성을 고려하여 사용하는것이 좋을것같음
- 포인트 칼럼 같은거 제외하고 wrapper class 사용하는게 좋음
- 강한종속관계에서는 Eager사용 , 약한종속에서는 lazy사용
- 근데 양방향인 경우에는 ManyToOne에 LAZY사용 권장

---

이제 양방향 판단할 차례

- 양방향 설계를 할때 직렬화를 주의하며 설계를 해야한다.
- 양방향 사용이유 : 부모기준 자식 탐색 용이 cascade사용가능
- ex) **리뷰 사진을 리뷰에서 바로 가져와야 하는 비즈니스라면** → 양방향이 더 편리

궁금한점 소프트 딜리트할때 양방향 설계에서는 cascade가 일어나지 않는데 그러면 user가 삭제되어도 usermssion은 삭제가 안될텐데.. user가 삭제되면 usermission이 삭제되어야함 사실

일단 softdelete를 할때 연관된 테이블의 softdelete는 서비스 로직에서 구현할 생각임

```java
@Transactional
public void deactivateUser(Long userId) {
Users user = userRepository.findById(userId)
.orElseThrow(() -> new RuntimeException("User not found"));
user.softDelete();
if (user.getUserMissionList() != null) {
    user.getUserMissionList().forEach(UserMission::softDelete);
		}
}

```

이런식 근데 이건 생각해봐야하는 문제

다른 방법으로는 BaseIdAndTimeEntity를 @OVERRIDE 해서 users엔티티에서 구현한 다음 소프트 딜리트 로직을 넣는 방법이 있음

---

< 총정리 >
BaseIdEntity , BaseIdAndTimeEntity를 만들어 엔티티들에게 상속

BaseIdAndTimeEntity에는 softDelete 구현



<역할 분담의 고민>

(궁금한 요소 softdelete는 users테이블만 사용할 것 같은데 ,이러한 경우에 users엔티티에 softDelete 메서드를 Override해서 users가 지워지면 다른것들도 지워지는 cascade방식을 사용하기 위해 Override한 메서드 안에서 cascade로직을 구현하는것이 좋을지, 아니면 service로직에서 users엔티티가 Override안하고 바로 BaseIdAndTimeEntity에서 메서드를 가져와 서비스 로직에서 cascade의 softdelete를 구현하는 것이 좋을지 고민했다. 결론적으로는 Override안하고 서비스 로직에서 처리하려고 결정)



<양방향 매핑에 대한 고민>

양방향 매핑은 user↔usermission , review↔reviewcomment , review↔reviewphoto , store↔review

이렇게 하고 다 Cascade.Remove 옵션을 부모에게 걸어줌 / 자식에게 걸어주는건 오류 위험이 있기때문

그리고 양방향 매핑을 할때는 Lazy옵션을 @ManyToOne 객체에 걸어줌

또한 직렬화 문제 예방을 위해 @JsonIgnore 옵션을  @ManyToOne 객체에 넣어줌

userfood와 userterm을 user와 양방향하지 않은 이유는 유저 입장에서 선호음식이랑 약관 조회를 많이 하지 않을 것이라 생각하기때문에 나중에 user를 softdelete를 했을때 서비스 로직에서 같이 지워주는 방식으로 채택

즉 , 양방향을 설계할때 제일 중요한 점은 하나의 엔티티를 조회할때 다른엔티티를 같이 조회하는것이 성능에 좋은지 나쁜지, 보통은 같이 조회하는지 안하는지 체크했음 userfood와 userterm은 user엔티티를 조회할때 딱히 같이 조회할 순간이 많이 없다고 판단함 다만 mission같은 경우에는 users를 조회할때 많은 조회를 요구할 것 같아서 양방향으로 설계함



<Builder에 대한 고민>

지금 구조에서 필수값이 들어가야하는 필드는 생성자에 @NunNull을 붙여서 사용

또한 선택적 필드는 @Builder패턴을 사용해서 능동적으로 값 넣도록 함

엔티티 하위에 선택이 null인 필드드들을 대비해서 update 메서드를 둠

필수값에 빌더패턴을 넣지 않는 이유는 빌더패턴에서 @NonNull 설정해줘도 나중에 서비스 계층에서 빌더로 넣지 않아도 ide오류가 뜨지 않음 → 개발자들이 실수 할 수 있음

즉 , 선택적 필드에는 빌더를 사용하고 업데이트가 필요한 경우 업데이트 메서드를 둠.

필수 필드에는 생성자에 @NonNull 사용



<Lazy에 대한 고민>

Lazy옵션은 추가 정보를 가지고 있고 단일 엔티티를 조회할 가능성이 있는 엔티티는 Lazy옵션을 사용 ,

단일엔티티아니라 참조하고있는 테이블의 정보를 무조건 사용해야하는 중간테이블 같은 엔티티는

Lazy옵션 미사용

하지만 , 양방향설계를 고려해야하는 경우에는 Lazy사용



<null에 대한 고민>

스키마를 설계할때 리뷰 답글 테이블 같은 경우나 , 리뷰 사진 테이블 같은 경우 칼럼에 답글 / 사진 같은게 있는데 이러한 경우 notnull을 사용하는것이 좋을지.. 나의 생각은 notnull을 사용하는게 맞다 생각함. 이유는 사진테이블에 사진이 null이면 의미가 없다고 생각하기때문.