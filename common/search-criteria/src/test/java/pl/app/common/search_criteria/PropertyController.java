package pl.app.common.search_criteria;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("properties")
class PropertyController {
    private final PropertyService service;

    @GetMapping("/test")
    ResponseEntity<List<Property>> fetchAllBySearchCriteriaAndPageable(@Valid SearchCriteria searchCriteria) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.fetchByCriteria(searchCriteria));
    }
}
