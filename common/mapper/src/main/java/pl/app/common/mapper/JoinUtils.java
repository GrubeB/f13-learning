package pl.app.common.mapper;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

import static pl.app.common.mapper.CollectionsUtils.contains;
import static pl.app.common.mapper.CollectionsUtils.createCollectionOfClass;

public class JoinUtils {
    @SuppressWarnings("unchecked")
    public static <E, C extends Collection<E>, V> C collectionJoin(Join type, C c1, C c2) {
        C result = createCollectionOfClass((Class<C>) c1.getClass());
        if (Objects.isNull(type)) {
            return result;
        }
        switch (type) {
            case LEFT, LEFT_INCLUSIVE -> {
                left(result, c1, c2);
                mid(result, c1, c2);
            }
            case LEFT_EXCLUSIVE -> left(result, c1, c2);
            case RIGHT, RIGHT_INCLUSIVE -> {
                mid(result, c1, c2);
                right(result, c1, c2);
            }
            case RIGHT_EXCLUSIVE -> right(result, c1, c2);
            case INNER -> mid(result, c1, c2);
            case FULL, FULL_OUTER_INCLUSIVE -> {
                left(result, c1, c2);
                mid(result, c1, c2);
                right(result, c1, c2);
            }
            case FULL_OUTER_EXCLUSIVE -> {
                left(result, c1, c2);
                right(result, c1, c2);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <E, C extends Collection<E>, V> C collectionJoin(Join type, C c1, C c2, Function<E, V> fieldProvider) {
        if (Objects.isNull(fieldProvider)) {
            return collectionJoin(type, c1, c2);
        }
        C result = createCollectionOfClass((Class<C>) c1.getClass());
        if (Objects.isNull(type)) {
            return result;
        }
        switch (type) {
            case LEFT, LEFT_INCLUSIVE -> {
                left(result, c1, c2, fieldProvider);
                mid(result, c1, c2, fieldProvider);
            }
            case LEFT_EXCLUSIVE -> left(result, c1, c2, fieldProvider);
            case RIGHT, RIGHT_INCLUSIVE -> {
                mid(result, c1, c2, fieldProvider);
                right(result, c1, c2, fieldProvider);
            }
            case RIGHT_EXCLUSIVE -> right(result, c1, c2, fieldProvider);
            case INNER -> mid(result, c1, c2, fieldProvider);
            case FULL, FULL_OUTER_INCLUSIVE -> {
                left(result, c1, c2, fieldProvider);
                mid(result, c1, c2, fieldProvider);
                right(result, c1, c2, fieldProvider);
            }
            case FULL_OUTER_EXCLUSIVE -> {
                left(result, c1, c2, fieldProvider);
                right(result, c1, c2, fieldProvider);
            }
        }
        return result;
    }

    static <E, C extends Collection<E>, V> void left(C result, C c1, C c2) {
        c1.stream()
                .filter(e1 -> !contains(c2, e1))
                .forEach(result::add);
    }

    static <E, C extends Collection<E>, V> void left(C result, C c1, C c2, Function<E, V> fieldProvider) {
        c1.stream()
                .filter(e1 -> !contains(c2, e1, fieldProvider))
                .forEach(result::add);
    }

    static <E, C extends Collection<E>, V> void mid(C result, C c1, C c2) {
        c1.stream()
                .filter(e1 -> contains(c2, e1))
                .forEach(result::add);
    }

    static <E, C extends Collection<E>, V> void mid(C result, C c1, C c2, Function<E, V> fieldProvider) {
        c1.stream()
                .filter(e1 -> contains(c2, e1, fieldProvider))
                .forEach(result::add);
    }

    static <E, C extends Collection<E>, V> void right(C result, C c1, C c2) {
        c2.stream()
                .filter(e2 -> !contains(c1, e2))
                .forEach(result::add);
    }

    static <E, C extends Collection<E>, V> void right(C result, C c1, C c2, Function<E, V> fieldProvider) {
        c2.stream()
                .filter(e2 -> !contains(c1, e2, fieldProvider))
                .forEach(result::add);
    }
}
