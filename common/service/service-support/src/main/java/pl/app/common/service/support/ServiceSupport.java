package pl.app.common.service.support;


import pl.app.common.dto_criteria.Dto;
import pl.app.common.mapper.Mapper;
import pl.app.common.mapper.Merger;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public interface ServiceSupport {
    interface MapperSupport {
        Mapper getMapper();
    }
    interface MergerSupport {
        Merger getMerger();
    }
    @SuppressWarnings("unchecked")
    interface DtoSupport {
        default <T> Class<T> getClass(Dto dto) {
            if (dto.getDtoName() == null) {
                return (Class<T>) getDefaultDtoClass();
            }
            Class<?> clazz = getSupportedDtoClasses().get(dto.getDtoName());
            if (clazz == null) {
                return (Class<T>) getDefaultDtoClass();
            }
            return (Class<T>) clazz;
        }

        Map<String, Class<?>> getSupportedDtoClasses();

        default Class<?> getDefaultDtoClass() {
            Iterator<Map.Entry<String, Class<?>>> iterator = getSupportedDtoClasses().entrySet().iterator();
            if (iterator.hasNext()) {
                Class<?> value = iterator.next().getValue();
                if (value == null) {
                    throw new RuntimeException("not found dto class");
                }
                return value;
            } else {
                throw new RuntimeException("not found dto class");
            }
        }
    }

    interface ParentSetterSupport<ENTITY, PARENT_ID> {
        default void settingParentBeforeSave(PARENT_ID parentId, ENTITY entity) {
        }
    }

    interface InterfaceArgumentResolverSupport {
        @SuppressWarnings("unchecked")
        default <T> Class<T> resolveArgumentAtIndex(Class<?> interfaceClass, int index) {
            Type[] typeArguments = getParametrizedInterface(interfaceClass).getActualTypeArguments();
            return (Class<T>) typeArguments[index];
        }

        default ParameterizedType getParametrizedInterface(Class<?> interfaceClass) {
            return Arrays.stream(getClass().getGenericInterfaces())
                    .map(type -> ((Class<?>) type).getGenericInterfaces())
                    .flatMap(Arrays::stream)
                    .map(type -> (ParameterizedType) type)
                    .filter(type -> Objects.equals(interfaceClass.getName(), type.getRawType().getTypeName()))
                    .findAny()
                    .orElseThrow(() -> new RuntimeException("Class must implement interface" + interfaceClass.getName()));
        }
    }
}
