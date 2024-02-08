package pl.app.common.mapper;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static pl.app.common.mapper.CollectionsUtils.*;

public class MergerUtils {

    @SuppressWarnings("unchecked")
    public static <E, C extends Collection<E>, V> C collectionMerge(Join type, C c1, C c2, BiFunction<E, E, E> merger) {
        C result = createCollectionOfClass((Class<C>) c1.getClass());
        if (Objects.isNull(type)) {
            return result;
        }
        switch (type) {
            case LEFT, LEFT_INCLUSIVE -> {
                JoinUtils.left(result, c1, c2);
                mergeMid(result, c1, c2, merger);
            }
            case LEFT_EXCLUSIVE -> JoinUtils.left(result, c1, c2);
            case RIGHT, RIGHT_INCLUSIVE -> {
                mergeMid(result, c1, c2, merger);
                JoinUtils.right(result, c1, c2);
            }
            case RIGHT_EXCLUSIVE -> JoinUtils.right(result, c1, c2);
            case INNER -> mergeMid(result, c1, c2, merger);
            case FULL, FULL_OUTER_INCLUSIVE -> {
                JoinUtils.left(result, c1, c2);
                mergeMid(result, c1, c2, merger);
                JoinUtils.right(result, c1, c2);
            }
            case FULL_OUTER_EXCLUSIVE -> {
                JoinUtils.left(result, c1, c2);
                JoinUtils.right(result, c1, c2);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <E, C extends Collection<E>, V> C collectionMerge(Join type, C c1, C c2, BiFunction<E, E, E> merger, Function<E, V> fieldProvider) {
        if (Objects.isNull(fieldProvider)) {
            return collectionMerge(type, c1, c2, merger);
        }
        C result = createCollectionOfClass((Class<C>) c1.getClass());
        if (Objects.isNull(type)) {
            return result;
        }
        switch (type) {
            case LEFT, LEFT_INCLUSIVE -> {
                JoinUtils.left(result, c1, c2, fieldProvider);
                mergeMid(result, c1, c2, merger, fieldProvider);
            }
            case LEFT_EXCLUSIVE -> JoinUtils.left(result, c1, c2, fieldProvider);
            case RIGHT, RIGHT_INCLUSIVE -> {
                mergeMid(result, c1, c2, merger, fieldProvider);
                JoinUtils.right(result, c1, c2, fieldProvider);
            }
            case RIGHT_EXCLUSIVE -> JoinUtils.right(result, c1, c2, fieldProvider);
            case INNER -> mergeMid(result, c1, c2, merger, fieldProvider);
            case FULL, FULL_OUTER_INCLUSIVE -> {
                JoinUtils.left(result, c1, c2, fieldProvider);
                mergeMid(result, c1, c2, merger, fieldProvider);
                JoinUtils.right(result, c1, c2, fieldProvider);
            }
            case FULL_OUTER_EXCLUSIVE -> {
                JoinUtils.left(result, c1, c2, fieldProvider);
                JoinUtils.right(result, c1, c2, fieldProvider);
            }
        }
        return result;
    }

    private static <E, C extends Collection<E>, V> void mergeMid(C result, C c1, C c2, BiFunction<E, E, E> merger) {
        c1.stream().filter(e1 -> contains(c2, e1))
                .map(e1 -> {
                    Optional<E> e2 = getElement(c2, e1);
                    return e2.map(e -> merger.apply(e1, e)).orElse(e1);
                }).forEach(result::add);
    }

    private static <E, C extends Collection<E>, V> void mergeMid(C result, C c1, C c2, BiFunction<E, E, E> merger, Function<E, V> fieldProvider) {
        c1.stream().filter(e1 -> contains(c2, e1, fieldProvider))
                .map(e1 -> {
                    Optional<E> e2 = getElement(c2, fieldProvider.apply(e1), fieldProvider);
                    return e2.map(e -> merger.apply(e1, e)).orElse(e1);
                }).forEach(result::add);
    }
}
