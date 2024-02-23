package pl.app.property.organization.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.property.organization.application.domain.Organization;
import pl.app.property.organization.query.dto.OrganizationDto;

@Getter
@Component
@RequiredArgsConstructor
public class OrganizationMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        // to dto
        addMapper(Organization.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(Organization.class, OrganizationDto.class, e -> modelMapper.map(e, OrganizationDto.class));
        addMapper(Organization.class, AggregateId.class, e -> new AggregateId(e.getId()));
        // to entity
        addMapper(OrganizationDto.class, Organization.class, e -> modelMapper.map(e, Organization.class));
        // merge
        addMerger(Organization.class, this::mergeOrganizationEntity);
    }

    private Organization mergeOrganizationEntity(Organization target, Organization source) {
        if (target == null || source == null) {
            return target;
        }
        if (source.getName() != null) {
            target.setName(source.getName());
        }
        return target;
    }
}
