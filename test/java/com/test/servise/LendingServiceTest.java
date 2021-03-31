package com.test.servise;

import com.eugene.Application;
import com.eugene.domain.Country;
import com.eugene.domain.Lending;
import com.eugene.domain.User;
import com.eugene.repository.CountryRepository;
import com.eugene.repository.UserRepository;
import com.eugene.service.CountryService;
import com.eugene.service.LendingService;
import com.fasterxml.classmate.AnnotationConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@ContextConfiguration({"classpath*:applicationContext.xml"})
@SpringBootTest(classes = Application.class)
public class LendingServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LendingService lendingService;

    @Test
    public void applyLoadThenSave() {
        User user = this.userRepository.save(new User("Eugene", "Petrov"));
        Country country = this.countryRepository.save(new Country("Ukraine"));
        Lending lending = this.lendingService.apply(new Lending(0, "", user, country));
        List<Lending> all = this.lendingService.getAll();
        assertTrue(all.contains(lending));
    }

    @Test
    public void findByPerson() {
        User user = this.userRepository.save(new User("Eugene", "Sidorov"));
        Country country = this.countryRepository.save(new Country("England"));
        Lending loan = this.lendingService.apply(new Lending(0, "", user, country));
        List<Lending> all = this.lendingService.getByUser(user.getId());
        assertSame(all.iterator().next(), is(loan));
    }
}
