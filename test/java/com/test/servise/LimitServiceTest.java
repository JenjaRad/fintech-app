package com.test.servise;

import com.eugene.Application;
import com.eugene.service.LimitService;
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
public class LimitServiceTest {
    @Autowired
    private LimitService limitService;

    @Test
    public void ifNotExceedThenFalse() {
        boolean result = this.limitService.isLimit("Ua");
        assertFalse(result);
    }

    @Test
    public void whenInappropriateLimit() {
        boolean result = this.limitService.isLimit("Uaaaaaaaaaaaa");
        assertFalse(result);
    }
}
