package pl.pdebala.eniro.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import pl.pdebala.eniro.EniroTaskApplication;
import pl.pdebala.eniro.rpository.SearchHistoryRepository;
import pl.pdebala.eniro.service.CompanyBasicSearchService;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

/**
 * Created by pdebala on 2016-10-10.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(SearchController.class)
@ContextConfiguration(classes={EniroTaskApplication.class})
public class SearchControllerTest {

    @Autowired
    private MockMvc mvc;

    @Mock
    private CompanyBasicSearchService companyBasicSearchService;


    @Test
    public void shouldRedirectToSearchPage() throws Exception {
        this.mvc.perform(get("/enirotest")
                .accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk());
    }

//    @Configuration
//    public static class Config {
//        @Bean
//        CompanyBasicSearchService companyBasicSearchService() {
//            return Mockito.mock(CompanyBasicSearchService.class);
//        }
//
//        @Bean
//        SearchHistoryRepository searchHistoryRepository() {
//            return Mockito.mock(SearchHistoryRepository.class);
//        }
//    }

}