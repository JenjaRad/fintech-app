package com.eugene.service;

public interface BlackListService {
    boolean isInUserInABlackList(long personId);
    boolean isCountryInABlackList(long countryId);
}
