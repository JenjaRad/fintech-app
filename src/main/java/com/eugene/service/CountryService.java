package com.eugene.service;

import com.eugene.domain.Country;

import java.util.List;

public interface CountryService {
    Country apply(Country country);
    List<Country> getAll();
    List<Country> getByLending(long id);

}
