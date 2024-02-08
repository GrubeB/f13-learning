package pl.app.common.mapper;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public abstract class BaseMapper implements
        Mapper,
        Merger {
    private final Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> mappers = new HashMap<>();
    private final Map<Class<?>, BiFunction<?, ?, ?>> mergers = new HashMap<>();

    @Override
    public Map<AbstractMap.SimpleEntry<Class<?>, Class<?>>, Function<?, ?>> getMappers() {
        return mappers;
    }

    @Override
    public Map<Class<?>, BiFunction<?, ?, ?>> getMergers() {
        return mergers;
    }
}
