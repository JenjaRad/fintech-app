package com.eugene.service;

import com.eugene.domain.Lending;
import com.eugene.domain.User;
import com.eugene.repository.LendingRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LendingServiceImpl implements LendingService {
    private final LendingRepository repository;

    @Autowired
    public LendingServiceImpl(final LendingRepository repository) {
        this.repository = repository;
    }

    @Override
    public Lending apply(final Lending lending) {
        return this.repository.save(lending);
    }

    @Override
    public List<Lending> getAll() {
        return Lists.newArrayList(this.repository.findAll());
    }

    @Override
    public List<Lending> getByUser(long userId) {
        return this.repository.findByUser(new User(userId));
    }

    @Override
    public List<Lending> getByUserName(String name,String surname) {
        return this.repository.findByUser(new User(name,surname));
    }

}