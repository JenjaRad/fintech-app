package com.eugene.repository;

import com.eugene.domain.Country;
import com.eugene.domain.Lending;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CountryRepository extends CrudRepository<Country,Long> {
    List<Country> findByLending(Lending lending);
}
