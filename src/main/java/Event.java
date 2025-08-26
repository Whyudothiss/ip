import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task{
    private String originalFrom;
    private String originalTo;
    private LocalDate fromDate;
    private LocalDate toDate;
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    public Event(String description, String from, String to) {
        super(description);
        this.originalFrom = from;
        this.originalTo = to;
        parseFromDateTime(this.originalFrom);
        parseToDateTime(this.originalTo);
    }

    private void parseFromDateTime(String dateTimeString) {
        try {
            this.fromDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            this.fromDate = this.fromDateTime.toLocalDate();
        } catch (DateTimeParseException e1) {
            try {
                this.fromDate = LocalDate.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                this.fromDateTime = null;
            } catch (DateTimeParseException e2) {
                this.fromDateTime = null;
                this.fromDate = null;
            }
        }
    }

    private void parseToDateTime(String dateTimeString) {
        try {
            this.toDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            this.toDate = this.toDateTime.toLocalDate();
        } catch (DateTimeParseException e1) {
            try {
                this.toDate = LocalDate.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
            fromString = fromDateTime.format(formatter);
        } else if (fromDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            fromString = fromDate.format(formatter);
        } else {
            fromString = originalFrom;
        }

        if (toDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
            toString = toDateTime.format(formatter);
        } else if (toDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            toString = toDate.format(formatter);
        } else {
            toString = originalTo;
        }
        return "[E][" + this.getStatusIcon() + "] " + description + " (from: " + fromString + " to: " + toString + ")";
    }

}
