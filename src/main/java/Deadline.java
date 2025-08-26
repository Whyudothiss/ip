import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    private String originalBy;
    private LocalDate byDate; // if user puts in date only
    private LocalDateTime byDateTime; // if user puts in both date and time

    public Deadline(String description, String by) {
        super(description);
        this.originalBy = by;
        parseDateTime(this.originalBy);
    }

    private void parseDateTime(String dateTimeString) {
        try {
            // try to convert string to yyyy-MM-dd HHmm format first
            this.byDateTime = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
            this.byDate = this.byDateTime.toLocalDate();
        } catch (DateTimeParseException e1) {
            // if format is wrong, will get thrown a dateTimeParseException, then try again using a different format
            try {
                this.byDate = LocalDate.parse(dateTimeString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                this.byDateTime = null; // since user did not specifiy time
            } catch (DateTimeParseException e2) {
                // If both times fail, meaning user did not give proper time or date, keep as string
                this.byDateTime = null;
                this.byDate = null;
            }
        }
    }

    public String getBy() {
        return this.originalBy;
    }

    public LocalDate getByDate() {
        return this.byDate;
    }

    public LocalDateTime getByDateTime() {
        return this.byDateTime;
    }

    @Override
    public String toString() {
        String dateString;
        if (this.byDateTime != null) {
            // Format as MMM dd yyyy, h:mm a
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
            dateString = byDateTime.format(formatter);
        } else if (this.byDate != null) {
            // Format as MMM dd yyyy
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            dateString = byDate.format(formatter);
        } else {
            // just fall back to string
            dateString = originalBy;
        }
        return "[D][" + this.getStatusIcon() +  "] " + this.description + " (by: " + dateString + ")";
    }

}
