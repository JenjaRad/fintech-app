package com.test.web;

import com.eugene.domain.Country;
import com.eugene.domain.Lending;
import com.eugene.domain.User;
import com.eugene.service.BlackListService;
import com.eugene.service.CountryService;
import com.eugene.service.LendingService;
import com.eugene.service.LimitService;
import com.eugene.web.LendingController;
import com.eugene.web.forms.FailError;
import com.eugene.web.forms.Result;
import com.eugene.web.forms.Success;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(LendingController.class)
public class LendingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LimitService limitService;

    @MockBean
    private LendingService lendingService;

    @MockBean
    private CountryService countryService;

    @MockBean
    private BlackListService blackListService;

    @Test
    public void ifPersonNotInBlackListThenApplyLoan() throws Exception {
        List<Lending> landings = Arrays.asList(new Lending(0.0, "", new User("Eugene", "Volkov"), new Country("ua")));
        given(this.lendingService.getAll()).willReturn(landings);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().
                        string(objectMapper.writeValueAsString(landings)));
    }

    @Test
    public void applyLoan() throws Exception {
        List<Lending> landings = Arrays.asList(new Lending(0.0, "", new User("Eugene", "Volkov"), new Country("ua")));
        given(this.lendingService.getByUser(0)).willReturn(landings);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/0").accept(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().
                        string(objectMapper.writeValueAsString(landings)));
    }

    @Test
    public void applyCountry() throws Exception {
        Country country = new Country("ua");
        given(this.countryService.apply(country)).willReturn(country);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().
                        string(objectMapper.writeValueAsString(country)));
    }

    @Test
    public void ifCountryNotInBlackListThenApply() throws Exception {
        List<Country> countries = Arrays.asList(new Country("ua"));
        given(this.countryService.getAll()).willReturn(countries);
        this.mockMvc.perform(MockMvcRequestBuilders.get(",")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(countries)));
    }
    @Test
    public void applyCountryById() throws Exception {
        List<Country> ua = Arrays.asList(new Country("ua"));
        given(this.countryService.getByCountry(0)).willReturn(ua);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")
        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(ua)));
    }

    @Test
    public void applyUserByName() throws Exception {
        List<Lending> landings = Arrays.asList(new Lending(0.0, " ", new User("Eugene", "Volkov"), new Country("ua")));
        given(this.lendingService.getByUserName("Eugene", "Volkov")).willReturn(landings);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/.")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(landings)));
    }

    @Test
    public void applyThenSave() throws Exception {
        Lending lending = new Lending(0.0, "", new User("Eugene", "Volkov"), new Country("ua"));
        given(this.blackListService.isInUserInABlackList(0)).willReturn(false);
        given(this.lendingService.apply(lending)).willReturn(lending);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(lending))).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().
                        string(objectMapper.writeValueAsString(new Success<>(lending))));
    }

    @Test
    public void applyBlackListCountry() throws Exception {
        given(this.blackListService.isCountryInABlackList(0)).willReturn(true);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Country("ua"))))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(new FailError("Country in a black list"))));
    }

    @Test
    public void ifUserInBlackList() throws Exception {
        given(this.blackListService.isInUserInABlackList(0)).willReturn(true);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Lending(0.0, "", new User("Eugene", "Volkov"), new Country("ua")))))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(new FailError("User not exist"))));
    }

    @Test
    public void applyLimit() throws Exception {
        given(this.limitService.isLimit("")).willReturn(true);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")
                .accept(MediaType.APPLICATION_JSON)).
                andExpect(MockMvcResultMatchers.status().isOk()).
                andExpect(MockMvcResultMatchers.content().
                        string(objectMapper.writeValueAsString(new Success<>("Success apply"))));
    }
}
