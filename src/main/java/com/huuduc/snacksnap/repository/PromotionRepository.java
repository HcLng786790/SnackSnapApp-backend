package com.huuduc.snacksnap.repository;

import com.huuduc.snacksnap.data.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion,Long> {

}
