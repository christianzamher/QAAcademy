package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PathUtils;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class NombreEquipoTest {

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
    public void testNombreEquipo() {
        try {
            String path = PathUtils.getHtmlPath("nombre_equipo.html");
            driver.get(path);

            WebElement inputNombre = driver.findElement(By.name("nombre_equipo"));
            inputNombre.sendKeys("LosQA");

            WebElement botonGuardar = driver.findElement(By.xpath("//button[text()='Guardar Nombre']"));
            botonGuardar.click();

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            Alert alerta = wait.until(ExpectedConditions.alertIsPresent());

            String mensajeAlerta = alerta.getText();
            System.out.println("Mensaje del popup: " + mensajeAlerta);
            assertEquals("Nombre del equipo guardado correctamente.", mensajeAlerta);
            Thread.sleep(2000);
            alerta.accept();

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test falló: " + e.getMessage());
        }
    }
}