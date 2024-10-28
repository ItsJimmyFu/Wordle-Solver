import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class WordleBrowserSolver {
    public static void main(String[] args) {
        // Initialize SafariDriver
        WebDriver driver = new SafariDriver();

        // Navigate to wordle website
        driver.get("https://www.nytimes.com/games/wordle/index.html");

        //Press Play Button
        WebElement button = driver.findElement(By.cssSelector("[data-testid='Play']"));
        button.click();

        //Press Close Button
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement button2 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.Modal-module_closeIcon__TcEKb")));
        button2.click();

        //Send Word Input
        Actions actions = new Actions(driver);

        // Target the body element to send the key presses
        WebElement body = driver.findElement(By.tagName("body"));

        // Send the word to Wordle
        actions.sendKeys(body, "tares").sendKeys(Keys.ENTER).perform();

        // Get the row element
        WebElement rowElement = driver.findElement(By.cssSelector("[aria-label='Row 1']"));

        // Define WebDriverWait with a timeout of 10 seconds
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                // Find all child elements (e.g., all <div> elements)
                List<WebElement> children = rowElement.findElements(By.className("Tile-module_tile__UWEHN"));
                for (WebElement child : children) {
                    String attributeValue = child.getAttribute("data-state");
                    if("absent".equals(attributeValue) || "present".equals(attributeValue) || "correct".equals(attributeValue)){
                        return true;
                    }
                }
                return false; // Keep waiting if none of the children have the desired attribute value
            }
        });
        List<WebElement> children = rowElement.findElements(By.className("Tile-module_tile__UWEHN"));
        for (WebElement child : children) {
            System.out.println(child.getText());
            System.out.println(child.getAttribute("aria-label"));
        }

        // Close the browser
        driver.quit();
    }
}
