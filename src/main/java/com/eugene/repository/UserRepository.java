package com.eugene.repository;

import com.eugene.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
public interface UserRepository extends CrudRepository<User,Long> {
}
