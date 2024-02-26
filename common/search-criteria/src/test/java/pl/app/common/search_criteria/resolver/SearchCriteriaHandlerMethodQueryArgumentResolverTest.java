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
import pl.app.common.search_criteria.ConditionOperator;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteria;
import pl.app.common.search_criteria.SearchCriteriaItem;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TestController.class)
@Import(SearchCriteriaHandlerMethodQueryArgumentResolverWebMvcConfig.class)
class SearchCriteriaHandlerMethodQueryArgumentResolverTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TestController testController;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test() throws Exception {
        MvcResult mvcResult; SearchCriteria reconstitutedSearchCriteria;

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test?query=\"year>=2015\""))
                .andExpect(status().isOk())
                .andReturn();
        reconstitutedSearchCriteria = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SearchCriteria.class);
        assertThat(reconstitutedSearchCriteria.getCriteria()).hasSize(1);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(0).getField()).isEqualTo("year");
        assertThat(reconstitutedSearchCriteria.getCriteria().get(0).getOperator()).isEqualTo(Operator.GREATER_THAN_OR_EQUAL);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(0).getValue()).isEqualTo("2015");

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test?query=\"year>=2015 AND name~\"Ala\"\""))
                .andExpect(status().isOk())
                .andReturn();
        reconstitutedSearchCriteria = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SearchCriteria.class);
        assertThat(reconstitutedSearchCriteria.getCriteria()).hasSize(2);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(0).getConditionOperator()).isEqualTo(ConditionOperator.AND);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(1).getField()).isEqualTo("name");
        assertThat(reconstitutedSearchCriteria.getCriteria().get(1).getOperator()).isEqualTo(Operator.LIKE);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(1).getValue()).isEqualTo("Ala");

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test?query=\" year>=2015 AND name~\"Ala\" or month=[10,11] \""))
                .andExpect(status().isOk())
                .andReturn();
        reconstitutedSearchCriteria = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SearchCriteria.class);
        assertThat(reconstitutedSearchCriteria.getCriteria()).hasSize(3);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(1).getConditionOperator()).isEqualTo(ConditionOperator.OR);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(1).getField()).isEqualTo("name");
        assertThat(reconstitutedSearchCriteria.getCriteria().get(1).getOperator()).isEqualTo(Operator.LIKE);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(1).getValue()).isEqualTo("Ala");

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test?query=\" day>29 and year!=[1242,2152, 32   ,42] \""))
                .andExpect(status().isOk())
                .andReturn();
        reconstitutedSearchCriteria = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SearchCriteria.class);
        assertThat(reconstitutedSearchCriteria.getCriteria()).hasSize(2);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(0).getConditionOperator()).isEqualTo(ConditionOperator.AND);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(1).getOperator()).isEqualTo(Operator.NO_IN);
        assertThat(reconstitutedSearchCriteria.getCriteria().get(1).getValues()).hasSize(4);

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test?query=\" day>29 and \""))
                .andExpect(status().isOk())
                .andReturn();
        reconstitutedSearchCriteria = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), SearchCriteria.class);
        assertThat(reconstitutedSearchCriteria.getCriteria()).hasSize(1);
    }
}