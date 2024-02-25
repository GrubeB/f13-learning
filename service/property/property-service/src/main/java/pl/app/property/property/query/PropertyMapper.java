package pl.app.property.property.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.property.application.domain.Property;
import pl.app.property.property.application.domain.PropertyAddress;
import pl.app.property.property.application.domain.PropertyDetails;
import pl.app.property.property.query.dto.PropertyDto;

@Getter
@Component
@RequiredArgsConstructor
public class PropertyMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        // to dto
        addMapper(Property.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(Property.class, PropertyDto.class, e -> modelMapper.map(e, PropertyDto.class));
        addMapper(Property.class, AggregateId.class, e -> new AggregateId(e.getId()));
        // to entity
        addMapper(PropertyDto.class, Property.class, e -> modelMapper.map(e, Property.class));
        // merge
        addMerger(Property.class, this::mergePropertyEntity);
    }

    private Property mergePropertyEntity(Property target, Property source) {
        if (target == null || source == null) {
            return target;
        }
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        if (source.getPropertyType() != null) {
            target.setPropertyType(source.getPropertyType());
        }
        if (source.getCheckInFromTime() != null) {
            target.setCheckInFromTime(source.getCheckInFromTime());
        }
        if (source.getCheckInToTime() != null) {
            target.setCheckInToTime(source.getCheckInToTime());
        }

        if (source.getCheckOutFromTime() != null) {
            target.setCheckOutFromTime(source.getCheckOutFromTime());
        }
        if (source.getCheckOutToTime() != null) {
            target.setCheckOutToTime(source.getCheckOutToTime());
        }
        if (source.getPropertyDetails() != null) {
            this.mergePropertyDetailsEntity(target.getPropertyDetails(), source.getPropertyDetails());
        }
        if (source.getOrganization() != null) {
            target.setOrganization(source.getOrganization());
        }
        return target;
    }

    private PropertyDetails mergePropertyDetailsEntity(PropertyDetails target, PropertyDetails source) {
        if (target == null || source == null) {
            return target;
        }
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        if (source.getEmail() != null) {
            target.setEmail(source.getEmail());
        }
        if (source.getPhone() != null) {
            target.setPhone(source.getPhone());
        }
        if (source.getWebsite() != null) {
            target.setWebsite(source.getWebsite());
        }
        if (source.getDescription() != null) {
            target.setDescription(source.getDescription());
        }
        if (source.getPropertyAddress() != null) {
            this.mergePropertyAddress(target.getPropertyAddress(), source.getPropertyAddress());
        }
        return target;
    }

    private PropertyAddress mergePropertyAddress(PropertyAddress target, PropertyAddress source) {
        if (target == null || source == null) {
            return target;
        }
        if (source.getId() != null) {
            target.setId(source.getId());
        }
        if (source.getAddress1() != null) {
            target.setAddress1(source.getAddress1());
        }
        if (source.getAddress2() != null) {
            target.setAddress2(source.getAddress2());
        }
        if (source.getCity() != null) {
            target.setCity(source.getCity());
        }
        if (source.getCountry() != null) {
            target.setCountry(source.getCountry());
        }
        if (source.getRegion() != null) {
            target.setRegion(source.getRegion());
        }
        if (source.getZipCode() != null) {
            target.setZipCode(source.getZipCode());
        }
        return target;
    }
}
