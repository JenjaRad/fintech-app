package com.test.servise;

import com.eugene.Application;
import com.eugene.domain.BlackList;
import com.eugene.domain.User;
import com.eugene.repository.BlackListRepository;
import com.eugene.repository.UserRepository;
import com.eugene.service.blacklist.BlackListService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
@SpringBootTest(classes = Application.class)
public class BlackListServiceTest {
    @Autowired
    private BlackListRepository blackListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BlackListService blackListService;

    @Test
    public void ifPersonInBlackListReturnTrue(){
        User user = this.userRepository.save(new User("Eugene", "Volkov"));
        this.blackListRepository.save(new BlackList(user));
        boolean inBlackList = this.blackListService.isInUserInABlackList(user.getId());
        assertTrue(inBlackList);
    }
    @Test
    public void ifPersonNotExistInBlackList(){
        User user = this.userRepository.save(new User("Eugene", "Volkov"));
        boolean inBlackList = this.blackListService.isInUserInABlackList(user.getId());
        assertFalse(inBlackList);
    }
    @Test
    public void ifTwoSamePersonsInAList(){
        User user1 = this.userRepository.save(new User("Eugene", "Volkov"));
        User user2 = this.userRepository.save(new User("Eugene", "Volkov"));
        boolean inBlackList1 = this.blackListService.isInUserInABlackList(user1.getId());
        boolean inBlackList2 = this.blackListService.isInUserInABlackList(user2.getId());
        assertTrue(inBlackList1);
        assertFalse(inBlackList2);
    }
 }
