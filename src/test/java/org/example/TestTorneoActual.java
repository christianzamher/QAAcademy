package org.example;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.PathUtils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestTorneoActual {

    // Array de elementos:
    private List<RegistroUsuarios> Jugadores() {
        List<RegistroUsuarios> usuarios = new ArrayList<>();
        usuarios.add(new RegistroUsuarios("Chris", "123456", "czamora@kopiustech.com", "", "ChrisZ", 75, "JUNGLA", "TOP", "Argentina"));

        return usuarios;
    }

    // Se declara el driver de Selenium
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        //private String chromeDriverPath = "ruta/a/chromedriver"; // Actualizar con la ruta correcta
        String chromeDriverPath = "c:/Users/ChristianZamora/Desktop/chromedriver-win64/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }

    //Despues de Cada Test:
    @AfterEach
    public void destroy() throws InterruptedException {
        if (driver != null) {
            Thread.sleep(2000);
            driver.quit();
        }
    }

    @Test
    @DisplayName("Abandonar equipo")
    @Order(2)
    public void abandonarEquipo() throws InterruptedException {

        //Llamada al path
        driver.get(PathUtils.getHtmlPath("abandonar_equipo.html"));

        // Ubicar al input
        WebElement abandonarField = driver.findElement(By.name("nombre_equipo"));
        //enviarle la palabra
        abandonarField.sendKeys("CONFIRMO");
        //Espera de 2 seg
        Thread.sleep(2000);

        WebElement btnAbandonar = driver.findElement(By.xpath("//*[@id=\"quitTeam\"]/button[1]"));
        btnAbandonar.click();
        Thread.sleep(2000);

        //ubicar el alert
        Alert alert = driver.switchTo().alert();
        //obtener el texto del alert para imprimir por pantalla
        String alertText = alert.getText();
        System.out.println("Texto del popup: " + alertText);
        // aceptar para cerrar
        alert.accept();
        Thread.sleep(1500);

        WebElement btnVolver = driver.findElement(By.xpath("/html/body/div/div/div/div/dl/button"));
        btnVolver.click();


    }



    @Test
    @DisplayName("prueba")
    @Order(1)
    public void testing(){
        try {

            driver.get(PathUtils.getHtmlPath("abandonar_equipo.html"));

            // Palabra a chequear
            String palabra = "CONFIRM0"; //
            WebElement abandonarField = driver.findElement(By.name("nombre_equipo"));
            //enviarle la palabra
            abandonarField.sendKeys(palabra);

            // Validación: todo mayúscula, sin números y debe ser exactamente "CONFIRMO"
            boolean esValida = palabra.matches("^[A-Z]+$") && palabra.equals("CONFIRMO");

            // Aserción: Si es inválida, falla el test y muestra mensaje
            assertTrue(esValida, "❌ La palabra debe ser exactamente 'CONFIRMO'");

            // Si es válida, escribir en campo y hacer clic
            WebElement input = driver.findElement(By.name("nombre_equipo"));
            input.sendKeys(palabra);

            // Ejemplo: hacer clic en un botón después
            WebElement boton = driver.findElement(By.xpath("//button[text()='Abandonar Equipo']")); // ajustá el texto si es distinto
            boton.click();

        } finally {
            System.out.println("Test finalizado");
        }

    }

    @Test
    @DisplayName("Votar nuevo Lider")
    @Order(3)
    public void nuevoLider() throws InterruptedException {

        driver.get(PathUtils.getHtmlPath("votar_lider.html"));

            try {
                //Obtener las opciones del dropdown
                WebElement dropdown = driver.findElement(By.name("pais"));
                List<WebElement> opciones = dropdown.findElements(By.tagName("option"));

                //Verificar que ninguna opción contenga "Andres"
                boolean andresPresente = opciones.stream()
                        .anyMatch(opcion -> opcion.getText().equalsIgnoreCase("Andres"));

                Assertions.assertFalse(andresPresente, "ERROR: Andres no debería estar en la lista de votación");

                //Seleccionar nuevo Lider
                opciones.stream()
                        .filter(op -> op.getText().equalsIgnoreCase("Chris"))
                        .findFirst()
                        .ifPresent(WebElement::click);

                    Thread.sleep(2000);

                // Hacer clic en el botón "Votar"
                WebElement votarBtn = driver.findElement(By.xpath("//button[text()='Votar']"));
                Thread.sleep(1500);
                votarBtn.click();


                // Aceptar el alert
                Alert alert = driver.switchTo().alert();
                Assertions.assertEquals("Ha votado correctamente.", alert.getText());
                Thread.sleep(1500);
                alert.accept();
                Thread.sleep(1500);

                WebElement btnvolver = driver.findElement(By.xpath("/html/body/div/div/div/div/dl/button"));
                btnvolver.click();

                System.out.println("✅ Nuevo Lider votado correctamente");

            } finally {
                System.out.println("✅ Test finalizado con exito ");
            }

    }

}
