package gichat.command;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void parseEvent_extraWhitespace_trimmedCorrectly() {
        String inputWithWhitespace = "  project meeting  /from  2023-10-15 1400  /to  2023-10-15 1600  ";
        String[] result = Parser.parseEvent(inputWithWhitespace);

        assertEquals(3, result.length);
        assertEquals("project meeting", result[0]);
        assertEquals("2023-10-15 1400", result[1]);
        assertEquals("2023-10-15 1600", result[2]);
    }

    @Test
    public void parseEvent_noFromKeyword_throwsException() {
        String invalidInput = "project meeting 2023-10-15 1400 /to 2023-10-15 1600";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            Parser.parseEvent(invalidInput);
        });

        assertEquals("Hey specify the event with /from and /to", exception.getMessage());
    }
}