package pl.app.property.accommodation_type_details.application.domain.service;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.app.property.accommodation_type_details.application.port.in.AccommodationTypeDetailsService;
import pl.app.property.accommodation_type_details.application.port.in.command.CreateAccommodationTypeDetailsCommand;
import pl.app.property.accommodation_type_details.query.dto.AccommodationTypeDetailsDto;
import pl.app.property.accommodation_type_details.query.AccommodationTypeDetailsMapper;
import pl.app.property.accommodation_type_details.application.port.out.AccommodationTypeDetailsRepository;

@Service
@RequiredArgsConstructor
@Transactional
@Getter
class AccommodationTypeDetailsServiceImpl implements
        AccommodationTypeDetailsService {
    private final AccommodationTypeDetailsRepository repository;
    private final AccommodationTypeDetailsMapper mapper;
}
