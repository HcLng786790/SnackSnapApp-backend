package com.huuduc.snacksnap.repository;

import com.huuduc.snacksnap.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserReopsitory extends JpaRepository<User,Long> {

    User findByCartId(long cartId);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByRoleId(long roleId);

}
