package pl.app.common.search_criteria.resolver;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.app.common.search_criteria.Operator;
import pl.app.common.search_criteria.SearchCriteriaItem;

import java.util.Optional;

class SearchCriteriaHandlerMethodQueryParameterArgumentResolverUnitTest {

    private final SearchCriteriaHandlerMethodQueryParameterArgumentResolver resolver = new SearchCriteriaHandlerMethodQueryParameterArgumentResolver();

    @Test
    void resolveSearchCriteriaItems() {
        Assertions.assertThat(resolver.resolveSearchCriteriaItems(
                "name=\"Ala\"     "
        )).hasSize(1);
        Assertions.assertThat(resolver.resolveSearchCriteriaItems(
                "length=123     "
        )).hasSize(1);
        Assertions.assertThat(resolver.resolveSearchCriteriaItems(
                "name=\"Ala\" AND length>200 OR name=\"Ola\" AND length<120"
        )).hasSize(4);
        Assertions.assertThat(resolver.resolveSearchCriteriaItems(
                "name=\"Ala AND Aga OR Kasia\" AND length>200 OR name=\"Ola\" AND length<120"
        )).hasSize(4);
        Assertions.assertThat(resolver.resolveSearchCriteriaItems(
                "name=\"Ala AND Aga OR Kasia\" and length>200 and length<120 or name=\"Ola\""
        )).hasSize(4);
    }
    @Test
    void resolveSearchCriteriaItem(){
        Optional<SearchCriteriaItem> searchCriteriaItem;
        searchCriteriaItem = resolver.resolveSearchCriteriaItem("name=\"Ala\"     ");
        Assertions.assertThat(searchCriteriaItem.isPresent()).isTrue();
        Assertions.assertThat(searchCriteriaItem.get().getOperator()).isEqualTo(Operator.EQUAL);
        Assertions.assertThat(searchCriteriaItem.get().getValue()).isEqualTo("Ala");

        searchCriteriaItem = resolver.resolveSearchCriteriaItem("name=[\"Ala\"]");
        Assertions.assertThat(searchCriteriaItem.isPresent()).isTrue();
        Assertions.assertThat(searchCriteriaItem.get().getOperator()).isEqualTo(Operator.IN);
        Assertions.assertThat(searchCriteriaItem.get().getValues()).hasSize(1);
        Assertions.assertThat(searchCriteriaItem.get().getValues().get(0)).isEqualTo("Ala");

        searchCriteriaItem = resolver.resolveSearchCriteriaItem("name=[\"Ala\",\"Aga\"]");
        Assertions.assertThat(searchCriteriaItem.isPresent()).isTrue();
        Assertions.assertThat(searchCriteriaItem.get().getOperator()).isEqualTo(Operator.IN);
        Assertions.assertThat(searchCriteriaItem.get().getValues()).hasSize(2);

        searchCriteriaItem = resolver.resolveSearchCriteriaItem("name!=[14,51, 234]");
        Assertions.assertThat(searchCriteriaItem.isPresent()).isTrue();
        Assertions.assertThat(searchCriteriaItem.get().getOperator()).isEqualTo(Operator.NO_IN);
        Assertions.assertThat(searchCriteriaItem.get().getValues()).hasSize(3);

        searchCriteriaItem = resolver.resolveSearchCriteriaItem("name~\"Ala\"");
        Assertions.assertThat(searchCriteriaItem.isPresent()).isTrue();
        Assertions.assertThat(searchCriteriaItem.get().getOperator()).isEqualTo(Operator.LIKE);
        Assertions.assertThat(searchCriteriaItem.get().getValue()).isEqualTo("Ala");

        searchCriteriaItem = resolver.resolveSearchCriteriaItem("year>2015");
        Assertions.assertThat(searchCriteriaItem.isPresent()).isTrue();
        Assertions.assertThat(searchCriteriaItem.get().getOperator()).isEqualTo(Operator.GREATER_THAN);
        Assertions.assertThat(searchCriteriaItem.get().getValue()).isEqualTo("2015");
    }
}