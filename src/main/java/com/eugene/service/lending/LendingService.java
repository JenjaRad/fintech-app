package com.eugene.service.lending;

import com.eugene.domain.Lending;
import com.eugene.domain.User;

import java.util.List;

public interface LendingService {
    Lending apply(Lending lending);

    List<Lending> getAll();

    List<Lending> getByUser(long userId);

    List<Lending> getByUserName(String name,String surname);
}
