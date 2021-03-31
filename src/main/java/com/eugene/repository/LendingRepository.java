package com.eugene.repository;

import com.eugene.domain.Lending;
import com.eugene.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LendingRepository extends CrudRepository<Lending,Long> {
    List<Lending> findByUser(User user);
}
