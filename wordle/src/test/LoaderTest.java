import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoaderTest {
    @Test
    void testWordListSize() throws Exception {
        Loader loader = new Loader(5);
        Assertions.assertEquals(14855,loader.getWordList().size());
    }

    @Test
    void testFirstWordLoaded() throws Exception {
        Loader loader = new Loader(5);
        Assertions.assertEquals("aahed",loader.getWordList().get(0));
    }

    @Test
    void testLastWordLoaded() throws Exception {
        Loader loader = new Loader(5);
        Assertions.assertEquals("zymic",loader.getWordList().get(loader.getWordList().size()-1));
    }

    @Test
    void testInvalidWordLength() {
        Loader loader;
        Assertions.assertThrows(Exception.class, () -> new Loader(0));
    }
}