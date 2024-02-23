package pl.app.common.ddd.shared;

import jakarta.persistence.Embeddable;
import pl.app.common.ddd.annotation.ValueObjectAnnotation;

import java.io.Serializable;

@ValueObjectAnnotation
@Embeddable
public class DateRange<T extends Comparable<? super T>> implements Serializable {
    private T fromDate;
    private T toDate;

    @SuppressWarnings("unused")
    protected DateRange() {
        super();
    }

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
