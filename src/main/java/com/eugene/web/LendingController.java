package com.eugene.web;

import com.eugene.domain.Lending;
import com.eugene.service.BlackListService;
import com.eugene.service.LendingService;
import com.eugene.web.forms.FailError;
import com.eugene.web.forms.Result;
import com.eugene.web.forms.Success;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LendingController {
    private final LendingService lendingService;

    private final BlackListService blackListService;

    @Autowired
    public LendingController(LendingService lendingService, BlackListService blackListService) {
        this.lendingService = lendingService;
        this.blackListService = blackListService;
    }
    @PostMapping("/")
    public Result apply(@RequestBody Lending lending) {
        final Result result;
        if (!blackListService.isInBlackList(lending.getUser().getId())) {
            result = new Success<>(this.lendingService.apply(lending));
        } else {
            result = new FailError(String.format("User %s in blacklist", lending.getUser().getId()));
        }
        return result;
    }
}
