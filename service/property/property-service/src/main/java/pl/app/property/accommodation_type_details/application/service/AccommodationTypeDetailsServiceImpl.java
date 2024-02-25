package pl.app.property.accommodation_type_details.application.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_type_details.application.port.in.AccommodationTypeDetailsService;
import pl.app.property.accommodation_type_details.application.port.out.AccommodationTypeDetailsRepository;
import pl.app.property.accommodation_type_details.query.AccommodationTypeDetailsMapper;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class AccommodationTypeDetailsServiceImpl implements
        AccommodationTypeDetailsService {
    private final AccommodationTypeDetailsRepository repository;
    private final AccommodationTypeDetailsMapper mapper;
    private final AccommodationTypeDetailsMapper merger;
}
