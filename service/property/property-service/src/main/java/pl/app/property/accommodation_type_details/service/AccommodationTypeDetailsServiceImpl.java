package pl.app.property.accommodation_type_details.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_type_details.mapper.AccommodationTypeDetailsMapper;
import pl.app.property.accommodation_type_details.persistance.AccommodationTypeDetailsRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class AccommodationTypeDetailsServiceImpl implements
        AccommodationTypeDetailsService {
    private final AccommodationTypeDetailsRepository repository;
    private final AccommodationTypeDetailsMapper mapper;
}
