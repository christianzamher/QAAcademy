package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import utils.PathUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertTrue;

public class CPanelAdminTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testFlujoPanelAdminYMailFecha() throws InterruptedException {
        // 1. Ir a panel_admin.html
        String path = PathUtils.getHtmlPath("panel_admin.html");
        driver.get(path);

        // 2. Completar fecha y hora
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        WebElement fechaInput = driver.findElement(By.name("fecha"));
        WebElement horaInput = driver.findElement(By.name("hora"));

        fechaInput.sendKeys(fechaHoy);
        horaInput.sendKeys("18:30");
        Thread.sleep(2000);
        // 3. Clic en "Vista Previa"
        WebElement vistaPreviaBtn = driver.findElement(By.xpath("//a[@href='mail_fecha_torneo.html']"));
        vistaPreviaBtn.click();

        // 4. Validar que estamos en mail_fecha_torneo.html
        assertTrue(driver.getCurrentUrl().contains("mail_fecha_torneo.html"));
        System.out.println("Vista previa del mail cargada correctamente.");
        Thread.sleep(2000);
        // 5. Clic en "Volver"
        WebElement volverBtn = driver.findElement(By.id("btnVolver"));
        volverBtn.click();

        fechaInput.sendKeys(fechaHoy);
        horaInput.sendKeys("18:30");
        // Hacer clic en el botón Notificar Participantes
        WebElement notificarBtn = driver.findElement(By.xpath("//button[text()='Notificar Participantes']"));
        notificarBtn.click();

        // Manejar el alert
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        System.out.println("Texto del popup: " + alertText);

        Assertions.assertTrue(alertText.contains("guardado correctamente") || alertText.contains("Nombre del equipo"),
                "El texto del popup no es el esperado: " + alertText);
        Thread.sleep(2000);
        alert.accept();

        // 6. Validar que estamos nuevamente en panel_admin.html
        assertTrue(driver.getCurrentUrl().contains("panel_admin.html"));
        System.out.println(" Retorno  al Panel de Admin desde el mail.");
    }


}
