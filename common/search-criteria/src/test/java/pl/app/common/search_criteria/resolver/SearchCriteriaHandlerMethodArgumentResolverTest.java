package pl.app.common.search_criteria.resolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@Import(WebMvcConfig.class)
class SearchCriteriaHandlerMethodArgumentResolverTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestController testController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void textContextLoad() throws Exception {
        assertThat(testController).isNotNull();
    }

    @Test
    void testObjectMapper() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria(List.of(
                new SearchCriteriaItem("name", Operator.EQUAL, "Ala")
        ));
        String searchCriteriaJson = objectMapper.writeValueAsString(searchCriteria);
        SearchCriteria reconstitutedSearchCriteria = objectMapper.readValue(searchCriteriaJson, SearchCriteria.class);

        assertThat(reconstitutedSearchCriteria.getCriteria()).hasSize(1);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(0).getField()).isEqualTo("name");
    }

    @Test
    void test() throws Exception {
        SearchCriteria searchCriteria = new SearchCriteria(List.of(
                new SearchCriteriaItem("name", Operator.EQUAL, "Ala")
        ));

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get("/test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchCriteria)))
                .andExpect(status().isOk())
                .andReturn();

        SearchCriteria reconstitutedSearchCriteria = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SearchCriteria.class);
        assertThat(reconstitutedSearchCriteria.getCriteria()).hasSize(1);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(0).getField()).isEqualTo("name");
    }
}