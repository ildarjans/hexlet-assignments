package exercise;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void testTake() {
        // BEGIN
        List<Integer> arr = Arrays.asList(1, 2, 3, 4, 5);
        List<Integer> result = App.take(arr, 10);


        assertThat(result).hasSameSizeAs(arr);
        for (int i = 0; i < result.size(); i++) {
            assertThat(result.get(i)).isEqualTo(arr.get(i));
        }

        // END
    }
}
