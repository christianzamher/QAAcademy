package org.example.tests;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.PathUtils;

import static org.junit.jupiter.api.Assertions.*;

public class DMailEquipoTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ChristianZamora/Desktop/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(2000);
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testBotonVolver() {
        try {
            String path = PathUtils.getHtmlPath("mail_equipo_creado.html");
            driver.get(path);

            WebElement botonVolver = driver.findElement(By.xpath("//button[contains(., 'Volver')]"));
            botonVolver.click();

            Thread.sleep(2000);

            // Validar que se redirigió correctamente
            assertTrue(driver.getCurrentUrl().contains("nombre_equipo.html"),
                    "La redirección no fue correcta. URL actual: " + driver.getCurrentUrl());

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test falló: " + e.getMessage());
        }
    }
}
