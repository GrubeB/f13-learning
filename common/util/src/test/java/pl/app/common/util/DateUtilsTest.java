package pl.app.common.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;


class DateUtilsTest {

    @Test
    void testDataRangeCollide() {
        // when
        boolean shouldTrue = DateUtils.isDateRangesCollide(
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 3),
                LocalDate.of(2023, 9, 2),
                LocalDate.of(2023, 9, 4)
        );
        boolean shouldTrue2 = DateUtils.isDateRangesCollide(
                LocalDate.of(2023, 9, 2),
                LocalDate.of(2023, 9, 4),
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 3)
        );
        boolean shouldTrue3 = DateUtils.isDateRangesCollide(
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 5),
                LocalDate.of(2023, 9, 2),
                LocalDate.of(2023, 9, 4)
        );
        boolean shouldTrue4 = DateUtils.isDateRangesCollide(
                LocalDate.of(2023, 9, 2),
                LocalDate.of(2023, 9, 4),
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 5)
        );
        boolean shouldFalse = DateUtils.isDateRangesCollide(
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 3),
                LocalDate.of(2023, 9, 3),
                LocalDate.of(2023, 9, 4)
        );
        boolean shouldFalse2 = DateUtils.isDateRangesCollide(
                LocalDate.of(2023, 9, 3),
                LocalDate.of(2023, 9, 4),
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 3)
        );
        boolean shouldFalse3 = DateUtils.isDateRangesCollide(
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 2),
                LocalDate.of(2023, 9, 2)
        );
        boolean shouldFalse4 = DateUtils.isDateRangesCollide(
                LocalDate.of(2023, 9, 2),
                LocalDate.of(2023, 9, 2),
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 1)
        );
        boolean shouldFalse5 = DateUtils.isDateRangesCollide(
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 1),
                LocalDate.of(2023, 9, 1)
        );
        // then
        assertThat(shouldTrue).isTrue();
        assertThat(shouldTrue2).isTrue();
        assertThat(shouldTrue3).isTrue();
        assertThat(shouldTrue4).isTrue();
        assertThat(shouldFalse).isFalse();
        assertThat(shouldFalse2).isFalse();
        assertThat(shouldFalse3).isFalse();
        assertThat(shouldFalse4).isFalse();
        assertThat(shouldFalse5).isFalse();
    }
}