package pl.app.common.ddd.shared;

import pl.app.common.ddd.annotation.ValueObjectAnnotation;

import java.io.Serializable;

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
        return fromDate.compareTo(toDate) <= 0;
    }
}
