package com.eugene.service;

import com.eugene.domain.Country;
import com.eugene.domain.Lending;
import com.eugene.repository.CountryRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository repository;

    @Autowired
    public CountryServiceImpl(final CountryRepository repository) {
        this.repository = repository;
    }

    @Override
    public Country apply(Country country) {
        return this.repository.save(country);
    }

    @Override
    public List<Country> getAll() {
        return Lists.newArrayList(this.repository.findAll());
    }

    @Override
    public List<Country> getByLending(long id) {
        return this.repository.findByLending(new Lending(id));
    }
}
