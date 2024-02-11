package pl.app.common.mapper;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static pl.app.common.mapper.CollectionsUtils.*;

public class MergerUtils {
    public static <E, C extends Collection<E>, V> C mergeCollections(Join type, C target, C source, BiFunction<E, E, E> merger) {
        if (Objects.isNull(type)) {
            return target;
        }
        switch (type) {
            case LEFT, LEFT_INCLUSIVE -> {
                mergeMid(target, source, merger);
            }
            case LEFT_EXCLUSIVE -> {
                target.removeIf(targetElement -> contains(source, targetElement)); // removeMid
            }
            case RIGHT, RIGHT_INCLUSIVE, FULL, FULL_OUTER_INCLUSIVE -> {
                mergeMid(target, source, merger);
                source.stream()
                        .filter(sourceElement -> !contains(target, sourceElement))
                        .forEach(target::add); // addRight
            }
            case RIGHT_EXCLUSIVE -> {
                List<E> temp = new ArrayList<>(source.size());
                source.stream()
                        .filter(sourceElement -> !contains(target, sourceElement))
                        .forEach(temp::add); // addRight
                target.clear();
                target.addAll(temp);
            }
            case INNER -> {
                mergeMid(target, source, merger);
                target.removeIf(targetElement -> !contains(source, targetElement)); // removeLeft
            }
            case FULL_OUTER_EXCLUSIVE -> {
                source.stream()
                        .filter(sourceElement -> !contains(target, sourceElement))
                        .forEach(target::add); // addRight
                target.removeIf(targetElement -> contains(source, targetElement)); // removeMid
            }
        }
        return target;
    }

    public static <E, C extends Collection<E>, V> C mergeCollections(Join type, C target, C source, BiFunction<E, E, E> merger, Function<E, V> fieldProvider) {
        if (Objects.isNull(type)) {
            return target;
        }
        if (Objects.isNull(fieldProvider)) {
            return mergeCollections(type, target, source, merger);
        }
        switch (type) {
            case LEFT, LEFT_INCLUSIVE -> {
                mergeMid(target, source, merger, fieldProvider);
            }
            case LEFT_EXCLUSIVE -> {
                target.removeIf(targetElement -> contains(source, targetElement, fieldProvider)); // removeMid
            }
            case RIGHT, RIGHT_INCLUSIVE,
                    FULL, FULL_OUTER_INCLUSIVE -> {
                target.removeIf(targetElement -> !contains(source, targetElement, fieldProvider)); // removeLeft
                mergeMid(target, source, merger, fieldProvider);
                source.stream()
                        .filter(sourceElement -> !contains(target, sourceElement, fieldProvider))
                        .forEach(target::add); // addRight
            }
            case RIGHT_EXCLUSIVE -> {
                List<E> temp = new ArrayList<>(source.size());
                source.stream()
                        .filter(sourceElement -> !contains(target, sourceElement, fieldProvider))
                        .forEach(temp::add); // addRight
                target.clear();
                target.addAll(temp);
            }
            case INNER -> {
                mergeMid(target, source, merger, fieldProvider);
                target.removeIf(targetElement -> !contains(source, targetElement, fieldProvider)); // removeLeft
            }
            case FULL_OUTER_EXCLUSIVE -> {
                source.stream()
                        .filter(sourceElement -> !contains(target, sourceElement, fieldProvider))
                        .forEach(target::add); // addRight
                target.removeIf(targetElement -> contains(source, targetElement, fieldProvider)); // removeMid
            }
        }
        return target;
    }


    @SuppressWarnings("unchecked")
    public static <E, C extends Collection<E>, V> C mergeCollectionsInNew(Join type, C c1, C c2, BiFunction<E, E, E> merger) {
        C result = createCollectionOfClass((Class<C>) c1.getClass());
        if (Objects.isNull(type)) {
            return result;
        }
        switch (type) {
            case LEFT, LEFT_INCLUSIVE -> {
                JoinUtils.addLeft(result, c1, c2);
                mergeMid(result, c1, c2, merger);
            }
            case LEFT_EXCLUSIVE -> JoinUtils.addLeft(result, c1, c2);
            case RIGHT, RIGHT_INCLUSIVE -> {
                mergeMid(result, c1, c2, merger);
                JoinUtils.addRight(result, c1, c2);
            }
            case RIGHT_EXCLUSIVE -> JoinUtils.addRight(result, c1, c2);
            case INNER -> mergeMid(result, c1, c2, merger);
            case FULL, FULL_OUTER_INCLUSIVE -> {
                JoinUtils.addLeft(result, c1, c2);
                mergeMid(result, c1, c2, merger);
                JoinUtils.addRight(result, c1, c2);
            }
            case FULL_OUTER_EXCLUSIVE -> {
                JoinUtils.addLeft(result, c1, c2);
                JoinUtils.addRight(result, c1, c2);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <E, C extends Collection<E>, V> C mergeCollectionsInNew(Join type, C c1, C c2, BiFunction<E, E, E> merger, Function<E, V> fieldProvider) {
        if (Objects.isNull(fieldProvider)) {
            return mergeCollectionsInNew(type, c1, c2, merger);
        }
        C result = createCollectionOfClass((Class<C>) c1.getClass());
        if (Objects.isNull(type)) {
            return result;
        }
        switch (type) {
            case LEFT, LEFT_INCLUSIVE -> {
                JoinUtils.addLeft(result, c1, c2, fieldProvider);
                mergeMid(result, c1, c2, merger, fieldProvider);
            }
            case LEFT_EXCLUSIVE -> JoinUtils.addLeft(result, c1, c2, fieldProvider);
            case RIGHT, RIGHT_INCLUSIVE -> {
                mergeMid(result, c1, c2, merger, fieldProvider);
                JoinUtils.addRight(result, c1, c2, fieldProvider);
            }
            case RIGHT_EXCLUSIVE -> JoinUtils.addRight(result, c1, c2, fieldProvider);
            case INNER -> mergeMid(result, c1, c2, merger, fieldProvider);
            case FULL, FULL_OUTER_INCLUSIVE -> {
                JoinUtils.addLeft(result, c1, c2, fieldProvider);
                mergeMid(result, c1, c2, merger, fieldProvider);
                JoinUtils.addRight(result, c1, c2, fieldProvider);
            }
            case FULL_OUTER_EXCLUSIVE -> {
                JoinUtils.addLeft(result, c1, c2, fieldProvider);
                JoinUtils.addRight(result, c1, c2, fieldProvider);
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

    private static <E, C extends Collection<E>, V> void mergeMid(C target, C source, BiFunction<E, E, E> merger) {
        target.stream().filter(targetElement -> contains(source, targetElement))
                .forEach(targetElement -> {
                    Optional<E> sourceElement = getElement(source, targetElement);
                    sourceElement.map(e -> merger.apply(targetElement, e));
                });
    }

    private static <E, C extends Collection<E>, V> void mergeMid(C target, C source, BiFunction<E, E, E> merger, Function<E, V> fieldProvider) {
        target.stream().filter(targetElement -> contains(source, targetElement, fieldProvider))
                .forEach(targetElement -> {
                    Optional<E> sourceElement = getElement(source, fieldProvider.apply(targetElement), fieldProvider);
                    sourceElement.map(e -> merger.apply(targetElement, e));
                });
    }
}
