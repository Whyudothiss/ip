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

    @Override
    public String toString() {
        return "[D][" + this.getStatusIcon() +  "] " + this.description + "(by:" + this.originalBy + ")";
    }

}
