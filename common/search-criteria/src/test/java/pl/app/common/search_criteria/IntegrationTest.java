package pl.app.common.search_criteria;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.app.common.search_criteria.resolver.QueryParameterArgumentResolverWebMvcConfig;

import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@Import(QueryParameterArgumentResolverWebMvcConfig.class)
class IntegrationTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    PropertyController controller;
    @Autowired
    PropertyRepository repository;

    @Test
    void textContextLoad() throws Exception {
        assertThat(controller).isNotNull();
    }

    @BeforeEach
    void beforeAll() {
        var p1 = new Property("Ala", LocalTime.of(8, 0, 0), LocalTime.of(12, 0, 0), Instant.now(),
                Set.of(
                        new PropertyAddress("address1", "city", "country"),
                        new PropertyAddress("address2", "city2", "country2")
                ));
        var p2 = new Property("Ala", LocalTime.of(8, 0, 0), LocalTime.of(12, 0, 0), Instant.now(),
                Set.of(
                        new PropertyAddress("address1", "city", "country"),
                        new PropertyAddress("address2", "city2", "country2")
                ));
        var p3 = new Property("Ala3", LocalTime.of(10, 0, 0), LocalTime.of(14, 0, 0), Instant.now(),
                Set.of(
                        new PropertyAddress("address1", "city", "country"),
                        new PropertyAddress("address2", "city2", "country2")
                ));
        repository.saveAndFlush(p1);
        repository.saveAndFlush(p2);
        repository.saveAndFlush(p3);
    }

    @Test
    void test() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/properties/test"))
                .andExpect(status().isOk())
                .andReturn();
        List<Property> properties = mapper.readValue(mvcResult.getResponse().getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, Property.class));
        Assertions.assertThat(properties).hasSize(3);
    }
    @Test
    void test2() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/properties/test?query=\" name=\"Ala\" \""))
                .andExpect(status().isOk())
                .andReturn();
        List<Property> properties = mapper.readValue(mvcResult.getResponse().getContentAsString(), mapper.getTypeFactory().constructCollectionType(List.class, Property.class));
        Assertions.assertThat(properties).hasSize(2);
    }
}