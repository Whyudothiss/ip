package gichat.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A type of task that has a from and to
 */
public class Event extends Task {
    private String originalFrom;
    private String originalTo;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");
    private static final DateTimeFormatter DATE_FORMATTER =  DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DISPLAY_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
    private static final DateTimeFormatter DISPLAY_DATE_FORMATTER =  DateTimeFormatter.ofPattern("MMM dd yyyy");

    /**
     * Constructs an instance of an Event with the description, from and to
     * @param description The description of the event
     * @param from The start of the event
     * @param to The end of the event
     */
    public Event(String description, String from, String to) {
        super(description);
        this.originalFrom = from;
        this.originalTo = to;
        parseFromDateTime(this.originalFrom);
        parseToDateTime(this.originalTo);
        validateDateTimeOrder();
    }

    private void validateDateTimeOrder() {
        LocalDateTime start = normaliseToDateTime(this.fromDate, this.fromDateTime);
        LocalDateTime end = normaliseToDateTime(this.toDate, this.toDateTime);

        // skip validation step
        if (start == null || end == null) {
            return;
        }

        if (!start.isBefore(end)) {
            String message = (this.fromDateTime != null || this.toDateTime != null)
                            ? "Alamak, Start time cannot be after or same as the end time la"
                            : "Wah you a time traveller? Start date cannot be after end date";
            throw new IllegalArgumentException(message);
        }
    }

    private LocalDateTime normaliseToDateTime(LocalDate date, LocalDateTime dateTime) {
        if (dateTime != null) {
            return dateTime;
        }
        if (date != null) {
            return date.atStartOfDay();
        }
        return null;
    }

    private void parseFromDateTime(String dateTimeString) {
        try {
            this.fromDateTime = LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
            this.fromDate = this.fromDateTime.toLocalDate();
        } catch (DateTimeParseException e1) {
            try {
                this.fromDate = LocalDate.parse(dateTimeString, DATE_FORMATTER);
                this.fromDateTime = null;
            } catch (DateTimeParseException e2) {
                this.fromDateTime = null;
                this.fromDate = null;
            }
        }
    }

    private void parseToDateTime(String dateTimeString) {
        try {
            this.toDateTime = LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
            this.toDate = this.toDateTime.toLocalDate();
        } catch (DateTimeParseException e1) {
            try {
                this.toDate = LocalDate.parse(dateTimeString, DATE_FORMATTER);
                this.toDateTime = null;
            } catch (DateTimeParseException e2) {
                this.toDate = null;
                this.toDateTime = null;
            }
        }
    }

    public String getFrom() {
        return this.originalFrom;
    }

    public String getTo() {
        return this.originalTo;
    }

    public LocalDate getFromDate() {
        return this.fromDate;
    }

    public LocalDateTime getFromDateTime() {
        return this.fromDateTime;
    }

    public LocalDate getToDate() {
        return this.toDate;
    }

    public LocalDateTime getToDateTime() {
        return this.toDateTime;
    }


    @Override
    public String toString() {
        String fromString, toString;
        if (fromDateTime != null) {
            fromString = fromDateTime.format(DISPLAY_DATE_TIME_FORMATTER);
        } else if (fromDate != null) {
            fromString = fromDate.format(DISPLAY_DATE_FORMATTER);
        } else {
            fromString = originalFrom;
        }

        if (toDateTime != null) {
            toString = toDateTime.format(DISPLAY_DATE_TIME_FORMATTER);
        } else if (toDate != null) {
            toString = toDate.format(DISPLAY_DATE_FORMATTER);
        } else {
            toString = originalTo;
        }
        return "[E][" + this.getStatusIcon() + "] " + description + " (from: " + fromString + " to: " + toString + ")";
    }

}
