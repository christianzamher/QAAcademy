package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.PathUtils;

import static org.junit.jupiter.api.Assertions.*;

public class FechaOrganizacionTest {

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
    public void testNotificarParticipantes() {
        try {
            String path = PathUtils.getHtmlPath("panel_admin.html");
            driver.get(path);

            // Completar fecha y hora del torneo
            WebElement fechaInput = driver.findElement(By.name("fecha"));
            WebElement horaInput = driver.findElement(By.name("hora"));

            fechaInput.sendKeys("2025-06-15");
            horaInput.sendKeys("15:00");

            // Hacer clic en el botón Notificar Participantes
            WebElement notificarBtn = driver.findElement(By.xpath("//button[text()='Notificar Participantes']"));
            notificarBtn.click();

            // Manejar el alert
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            System.out.println("Texto del popup: " + alertText);

            assertTrue(alertText.contains("guardado correctamente") || alertText.contains("Nombre del equipo"),
                    "El texto del popup no es el esperado: " + alertText);
            Thread.sleep(2000);
            alert.accept();

        } catch (Exception e) {
            e.printStackTrace();
            fail("Test falló con excepción: " + e.getMessage());
        }
    }
}
