package com.example.umc9th.domain.region.repository;

import com.example.umc9th.global.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegionRepository extends JpaRepository<Region,Long> {
}
