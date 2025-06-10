package org.example;
// importamos ruta:
import utils.PathUtils;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;

import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class EquiposTest {

    private WebDriver driver;


    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/ChristianZamora/Desktop/chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(1500);
        driver.quit();
        System.out.println("Navegador cerrado exitosamente");
    }

    @Test
    public void EquipoFormado() {
        driver.get(PathUtils.getHtmlPath("equipos.html"));

        // Verificar el encabezado de Equipo #1
        try {
            WebElement equipoTitulo = driver.findElement(By.xpath("//div[@class='panel-heading' and contains(text(), 'Equipo #1')]"));
            System.out.println(" Los QA aka 'Equipo #1' encontrado correctamente.");
        } catch (Exception e) {
            System.out.println(" No se encontró el encabezado 'Equipo #1'.");
            fail("No se encontró el encabezado 'Equipo #1'.");
        }

        // Verificar el líder
        try {
            WebElement lider = driver.findElement(By.xpath("//div[@class='panel-heading']/p"));
            String textoLider = lider.getText(); // Ej: "Líder: Andres"
            if (textoLider.contains("Andres")) {
                System.out.println("✔ Líder correcto: " + textoLider);
            } else {
                System.out.println("✘ Líder incorrecto: " + textoLider);
                fail("El líder no es el esperado.");
            }
        } catch (Exception e) {
            System.out.println("✘ No se pudo verificar el líder.");
            //fail("No se encontró el elemento del líder.");
        }

        // Verificar roles y jugadores
        WebElement panelBody = driver.findElement(By.className("panel-body"));
        String bodyText = panelBody.getText();

        verificarRol(bodyText, "TOP", "Andres");
        verificarRol(bodyText, "JUNGLA", "Chris");
        verificarRol(bodyText, "MID", "Sele");
        verificarRol(bodyText, "ADC", "Ivan");
        verificarRol(bodyText, "SOPORTE", "Manu");
    }

    // Método auxiliar
    private void verificarRol(String bodyText, String rol, String jugador) {
        String expected = rol + ": " + jugador;
        if (bodyText.contains(expected)) {
            System.out.println("✔ " + expected + " encontrado correctamente.");
        } else {
            System.out.println("✘ Falta o incorrecto: " + expected);
            fail("No se encontró el texto esperado: " + expected);
        }
    }
}
