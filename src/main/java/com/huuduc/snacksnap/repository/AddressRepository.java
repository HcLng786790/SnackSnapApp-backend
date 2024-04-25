package com.huuduc.snacksnap.repository;

import com.huuduc.snacksnap.data.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address,Long> {

//    Optional<Address> findByUserIdAndDefaultsIsTrue(long userId);
    List<Address> findByUserId(long userId);

    Address findByUserIdAndDefaultsIsTrue(long userId);
}
