package ee.era.hangman.model;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class WordsTest {
    private Words words = spy(new Words());

    @Test
    public void choosesRandomTopicAndWord() {
        doReturn(asList(new Word("home", "nail"), new Word("home", "toilet"), new Word("home", "kettle"), new Word("flora", "carnation"), new Word("flora", "bush"), new Word("fauna", "camel")))
                .when(words).getDictionary("en");

        Map<String, Integer> count = new HashMap<>();
        count.put("nail", 0);
        count.put("toilet", 0);
        count.put("kettle", 0);
        count.put("carnation", 0);
        count.put("bush", 0);
        count.put("camel", 0);

        for (int i = 0; i < 2 * 6000; i++) {
            Word randomWord = words.getRandomWord("en");
            count.put(randomWord.getWord(), count.get(randomWord.getWord()) + 1);
        }

        assertTrue(count.get("nail") > 1000);
        assertTrue(count.get("toilet") > 1000);
        assertTrue(count.get("kettle") > 1000);
        assertTrue(count.get("carnation") > 1000);
        assertTrue(count.get("bush") > 1000);
        assertTrue(count.get("camel") > 1000);
    }
}
