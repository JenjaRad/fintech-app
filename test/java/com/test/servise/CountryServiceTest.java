package com.test.servise;

import com.eugene.Application;
import com.eugene.domain.Country;
import com.eugene.domain.Lending;
import com.eugene.domain.User;
import com.eugene.repository.CountryRepository;
import com.eugene.repository.LendingRepository;
import com.eugene.service.CountryService;
import com.eugene.service.LendingService;
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
@ContextConfiguration(locations = {"classpath*:/applicationContext.xml"})
@SpringBootTest(classes = Application.class)
public class CountryServiceTest {
    @Autowired
    private CountryService countryService;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private LendingService lendingService;

    @Autowired
    private LendingRepository lendingRepository;

    @Test
    public void applyCountryThanSave() {
        Country country = this.countryRepository.save(new Country("ua"));
        this.countryService.apply(country);
        List<Country> result = this.countryService.getAll();
        assertTrue(result.contains(country));
    }

    @Test
    public void applyAndFindByLending() {
        Country country = this.countryRepository.save(new Country("ua"));
        Lending lending = this.lendingRepository.save(new Lending(0, "", new User("Eugene", "Volkov"), country));
        this.lendingService.apply(lending);
        this.countryService.apply(country);
        List<Country> list = this.countryService.getByLending(lending.getId());
        assertSame(list.iterator().hasNext() ? list.iterator().next() : false, is(country));
    }
}
