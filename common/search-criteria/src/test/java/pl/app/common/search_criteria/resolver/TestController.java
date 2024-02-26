package pl.app.common.search_criteria.resolver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.app.common.search_criteria.SearchCriteria;

@RestController
public class TestController {
    @GetMapping("/test")
    SearchCriteria test(SearchCriteria searchCriteria) {
        return searchCriteria;
    }
}
