package pl.app.learning.category.query;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;
import pl.app.common.ddd.AggregateId;
import pl.app.common.mapper.BaseMapper;
import pl.app.common.shared.dto.BaseDto;
import pl.app.learning.category.query.dto.CategoryDto;
import pl.app.learning.category.query.dto.SimpleCategoryDto;
import pl.app.learning.category.query.model.CategoryQuery;

import java.util.List;
import java.util.Set;

@Getter
@Component
@RequiredArgsConstructor
public class CategoryQueryMapper extends BaseMapper {
    private final ModelMapper modelMapper;

    @PostConstruct
    void init() {
        TypeMap<CategoryQuery, CategoryDto> typeMap = modelMapper.createTypeMap(CategoryQuery.class, CategoryDto.class);

        Converter<Set<CategoryHasCategoryQuery>, List<SimpleCategoryDto>> parentConverter = context -> context.getSource().stream()
                .map(chc -> new SimpleCategoryDto(chc.getParent().getId(), chc.getParent().getName(), chc.getParent().getDescription(), chc.getParent().getStatus()))
                .toList();
        typeMap.addMappings(mapper -> mapper.using(parentConverter).map(CategoryQuery::getParentCategories, CategoryDto::setParents));
        Converter<Set<CategoryHasCategoryQuery>, List<SimpleCategoryDto>> childConverter = context -> context.getSource().stream()
                .map(chc -> new SimpleCategoryDto(chc.getChild().getId(), chc.getChild().getName(), chc.getChild().getDescription(), chc.getChild().getStatus()))
                .toList();
        typeMap.addMappings(mapper -> mapper.using(childConverter).map(CategoryQuery::getChildCategories, CategoryDto::setChildren));

        addMapper(CategoryQuery.class, CategoryDto.class, e -> modelMapper.map(e, CategoryDto.class));
        addMapper(CategoryQuery.class, SimpleCategoryDto.class, e -> modelMapper.map(e, SimpleCategoryDto.class));
        addMapper(CategoryQuery.class, BaseDto.class, e -> modelMapper.map(e, BaseDto.class));
        addMapper(CategoryQuery.class, AggregateId.class, e -> new AggregateId(e.getId()));
    }
}
