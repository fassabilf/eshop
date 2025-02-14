package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Alert;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class ProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setUpTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_isSuccessful(ChromeDriver driver) {
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.sendKeys("Cap Bangao");
        quantityInput.sendKeys("1212");

        submitButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("/product/list"));

        String pageContent = driver.getPageSource();
        assertTrue(pageContent.contains("Cap Bangao"));
        assertTrue(pageContent.contains("1212"));
    }

    @Test
    void editProduct_isSuccessful(ChromeDriver driver) {
        // Step 1: Create a product first
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.sendKeys("Shampoo Lama");
        quantityInput.sendKeys("10");
        submitButton.click();

        // Step 2: Find the edit button and click it
        driver.get(baseUrl + "/product/list");
        WebElement editButton = driver.findElement(By.xpath("//a[contains(text(), 'Edit')]"));
        editButton.click();

        // Step 3: Edit the product
        WebElement nameEditInput = driver.findElement(By.id("nameInput"));
        WebElement quantityEditInput = driver.findElement(By.id("quantityInput"));
        WebElement saveButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameEditInput.clear();
        nameEditInput.sendKeys("Shampoo Baru");
        quantityEditInput.clear();
        quantityEditInput.sendKeys("20");
        saveButton.click();

        // Step 4: Verify changes
        String pageContent = driver.getPageSource();
        assertTrue(pageContent.contains("Shampoo Baru"));
        assertTrue(pageContent.contains("20"));
    }


    @Test
    void deleteProduct_isSuccessful(ChromeDriver driver) {
        // Step 1: Create a product first
        driver.get(baseUrl + "/product/create");

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        WebElement submitButton = driver.findElement(By.cssSelector("button[type='submit']"));

        nameInput.sendKeys("Produk Hapus");
        quantityInput.sendKeys("99");
        submitButton.click();

        // Step 2: Go to product list
        driver.get(baseUrl + "/product/list");

        // Step 3: Find the row that contains "Produk Hapus"
        WebElement targetRow = driver.findElement(By.xpath("//tr[td[contains(text(),'Produk Hapus')]]"));

        // Step 4: Find the delete button inside that row and click it
        WebElement deleteButton = targetRow.findElement(By.xpath(".//a[contains(text(), 'Delete')]"));
        deleteButton.click();

        // Step 5: Handle alert confirmation
        Alert alert = driver.switchTo().alert();
        alert.accept();

        // Step 6: Verify deletion
        String pageContent = driver.getPageSource();
        System.out.println(pageContent);
        assertFalse(pageContent.contains("Produk Hapus"));
    }


}
