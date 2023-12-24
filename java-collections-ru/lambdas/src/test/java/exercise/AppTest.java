package exercise;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// BEGIN
class AppTest {

    @Test
    void testEnlargeArrayImage() {
        // BEGIN

        String[][] image = {
                {"*", "*", "*", "*"},
                {"*", " ", " ", "*"},
                {"*", " ", " ", "*"},
                {"*", "*", "*", "*"},
        };

        String[][] expected = {
            {"*", "*", "*", "*", "*", "*", "*", "*"},
            {"*", "*", "*", "*", "*", "*", "*", "*"},
            {"*", "*", " ", " ", " ", " ", "*", "*"},
            {"*", "*", " ", " ", " ", " ", "*", "*"},
            {"*", "*", " ", " ", " ", " ", "*", "*"},
            {"*", "*", " ", " " ," ", " ", "*", "*"},
            {"*", "*", "*", "*", "*", "*", "*", "*"},
            {"*", "*", "*", "*", "*", "*", "*", "*"},
        };

        String[][] result = App.enlargeArrayImage(image);
        assertThat(Arrays.deepToString(result)).isEqualTo(Arrays.deepToString(expected));
        // END
    }
}
// END
