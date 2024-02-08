package pl.app.common.mapper;

import java.util.*;
import java.util.function.Function;

public class CollectionsUtils {
    public static <T, V> Optional<T> getElement(Collection<T> c1, V targetValue) {
        return c1.stream()
                .filter(e -> Objects.equals(e, targetValue))
                .findFirst();
    }

    public static <T, V> Optional<T> getElement(Collection<T> c1, V targetValue, Function<T, V> fieldProvider) {
        return c1.stream()
                .filter(e -> Objects.equals(targetValue, fieldProvider.apply(e) ))
                .findFirst();
    }

    public static <T> boolean contains(Collection<T> collection, T target) {
        return collection.stream()
                .anyMatch(e -> Objects.equals(e, target));
    }

    public static <T> boolean contains(Collection<T> collection, T target, Function<T, ?> fieldProvider) {
        return collection.stream()
                .anyMatch(e -> Objects.equals(fieldProvider.apply(e), fieldProvider.apply(target)));
    }

    @SuppressWarnings("unchecked")
    public static <E, C extends Collection<E>> C createCollectionOfClass(Class<C> collectionType) {
        try {
            return collectionType.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            if (List.class.isAssignableFrom(collectionType)) {
                return (C) new ArrayList<E>();
            } else if (Set.class.isAssignableFrom(collectionType)) {
                return (C) new LinkedHashSet<>();
            }
            return (C) new ArrayList<E>();
        }
    }
}
