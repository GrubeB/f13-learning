package pl.app.ddd.shared;

import pl.app.ddd.annotation.ValueObjectAnnotation;

import java.io.Serializable;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;

@ValueObjectAnnotation
public class DateRange<T extends Comparable<? super T>> implements Serializable {
    private final T fromDate;
    private final T toDate;

    public DateRange(T fromDate, T toDate) {
        if (!isFromDateIsBeforeToDate(fromDate, toDate)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date");
        }
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public T getFromDate() {
        return fromDate;
    }

    public T getToDate() {
        return toDate;
    }
    private boolean isFromDateIsBeforeToDate(T fromDate, T toDate) {
//        return fromDate.getLong(ChronoField.INSTANT_SECONDS) < toDate.getLong(ChronoField.INSTANT_SECONDS)
//                || (fromDate.getLong(ChronoField.INSTANT_SECONDS) == toDate.getLong(ChronoField.INSTANT_SECONDS)
//                && fromDate.getLong(ChronoField.NANO_OF_SECOND) < toDate.getLong(ChronoField.NANO_OF_SECOND));
        return fromDate.compareTo(toDate) <= 0;
    }
}
