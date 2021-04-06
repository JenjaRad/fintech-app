package com.eugene.web;

import com.eugene.domain.Lending;
import com.eugene.service.blacklist.BlackListService;
import com.eugene.service.lending.LendingService;
import com.eugene.web.forms.FailError;
import com.eugene.web.forms.Result;
import com.eugene.web.forms.Success;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.slf4j.LoggerFactory.getLogger;
@RestController
public class LendingController {
    private Logger logger = getLogger(LendingController.class);
    private final LendingService lendingService;

    private final BlackListService blackListService;

    @Autowired
    public LendingController(LendingService lendingService, BlackListService blackListService) {
        this.lendingService = lendingService;
        this.blackListService = blackListService;
    }
    @PostMapping("/")
    public Result apply(@RequestBody Lending lending) {
        logger.info("Apply lending ",lending.getId());
        final Result result;
        if (!blackListService.isInUserInABlackList(lending.getUser().getId())) {
            result = new Success<>(this.lendingService.apply(lending));
        } else {
            result = new FailError(String.format("User %s in blacklist", lending.getUser().getId()));
        }
        return result;
    }
}
