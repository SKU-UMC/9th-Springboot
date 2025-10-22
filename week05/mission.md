# 미션

# 리뷰 작성

```sql
public interface ReviewRepository extends JpaRepository<Review, Long> {

    //1. 리뷰 작성
    Review save(Review review);
}

```

# 마이 페이지

```sql
public interface MemberRepository extends JpaRepository<Member, Long> {

    //2. 마이 페이지
    Optional<Member> findById(Long memberId);
}
```

# 진행중, 진행완료 미션 목록

```sql
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {    

    //3. 진행 중, 진행 완료 미션
    @Query(value = "SELECT mm FROM MemberMission mm " +
            "JOIN FETCH mm.mission m " +
            "JOIN FETCH m.store " +
            "WHERE mm.member.id = :memberId ",
            countQuery = "SELECT count(mm) FROM MemberMission mm WHERE mm.member.id = :memberId")   //countQuery 분리(페이징)
    Page<MemberMission> findMemberMissionsByMemberIdWithDetails(Long memberId, Pageable pageable);
}
```

# 홈 화면

```sql
public interface MemberMissionRepository extends JpaRepository<MemberMission, Long> {
   
    //4-1. 홈 화면 - 해당 지역 달성 미션 개수
    @Query("SELECT COUNT(mm) FROM MemberMission mm " +
            "WHERE mm.member.id = :memberId AND mm.mission.store.location.id = :locationId")
    Long countCompletedMissionsInLocation(Long memberId, Long locationId);
}

```

```sql
public interface MissionRepository extends JpaRepository<Mission, Long> {

    //4-2. 홈화면 - 도전 가능 미션 목록
    @Query(value = "SELECT m FROM Mission m " +
            "JOIN FETCH m.store s " +
            "WHERE m.store.location.id = :locationId AND " +
            "NOT EXISTS (" +
            "  SELECT 1 FROM MemberMission mm " +
            "  WHERE mm.member.id = :memberId AND mm.mission = m" +
            ")",
            countQuery = "SELECT count(m) FROM Mission m " +    //countQuery 분리(페이징)
                    "WHERE m.store.location.id = :locationId AND " +
                    "NOT EXISTS (" +
                    "  SELECT 1 FROM MemberMission mm " +
                    "  WHERE mm.member.id = :memberId AND mm.mission = m" +
                    ")")
    Page<Mission> findAvailableMissions(Long locationId, Long memberId, Pageable pageable);

}
```