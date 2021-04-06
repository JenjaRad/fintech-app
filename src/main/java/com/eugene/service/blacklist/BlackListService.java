package com.eugene.service.blacklist;

public interface BlackListService {
    boolean isInUserInABlackList(long personId);
    boolean isCountryInABlackList(long countryId);
}
