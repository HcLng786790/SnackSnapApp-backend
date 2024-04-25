package com.huuduc.snacksnap.repository;

import com.huuduc.snacksnap.data.entity.CartDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartDetailsRepositoy extends JpaRepository<CartDetails,Long> {

    List<CartDetails> findAll();

    Optional<CartDetails> findById(long id);

    List<CartDetails> findByCartId(long id);

    boolean deleteById(long id);

    List<CartDetails> findByProductIdAndOrdersIdIsNull(long productId);
}
