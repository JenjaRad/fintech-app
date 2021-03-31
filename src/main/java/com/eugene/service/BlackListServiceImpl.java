package com.eugene.service;

import com.eugene.domain.Country;
import com.eugene.domain.User;
import com.eugene.repository.BlackListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlackListServiceImpl implements BlackListService {
    private final BlackListRepository repository;

    @Autowired
    public BlackListServiceImpl(BlackListRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean isInUserInABlackList(long personId) {
        return this.repository.findByUser(new User(personId)) != null;
    }

    @Override
    public boolean isCountryInABlackList(long countryId) {
        return this.repository.findByCountry(new Country(countryId))!=null;
    }
}
