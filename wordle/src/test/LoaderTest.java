import main.WordList.Loader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoaderTest {
    @Test
    void testWordListSize() throws Exception {
        Loader loader = new Loader(5);
        Assertions.assertEquals(loader.getWordList().size(),14855);
    }

    @Test
    void testFirstWordLoaded() throws Exception {
        Loader loader = new Loader(5);
        Assertions.assertEquals(loader.getWordList().get(0),"aahed");
    }

    @Test
    void testLastWordLoaded() throws Exception {
        Loader loader = new Loader(5);
        Assertions.assertEquals(loader.getWordList().get(loader.getWordList().size()-1),"zymic");
    }

    @Test
    void testInvalidWordLength() {
        Loader loader;
        Assertions.assertThrows(Exception.class, () -> new Loader(0));
    }
}