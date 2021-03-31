package com.eugene.repository;

import com.eugene.domain.BlackList;
import com.eugene.domain.Country;
import com.eugene.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackListRepository extends CrudRepository<BlackList,Long> {
    BlackList findByUser(User user);
    BlackList findByCountry(Country country);
}
