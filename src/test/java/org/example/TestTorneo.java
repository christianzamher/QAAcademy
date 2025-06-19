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
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // con esto se organiza los order
public class TestTorneo {

    // Array de elementos:
    private List<RegistroUsuarios> Jugadores() {
        List<RegistroUsuarios> usuarios = new ArrayList<>();
        usuarios.add(new RegistroUsuarios("Andres", "11223344", "andy@kopiustech.com", "axndy#1337", "axdny", 85, "TOP", "JUNGLA", "Argentina"));
        usuarios.add(new RegistroUsuarios("Chris", "123456", "czamora@kopiustech.com", "", "ChrisZ", 75, "JUNGLA", "TOP", "Argentina"));
        usuarios.add(new RegistroUsuarios("Selene", "12345687", "sele@kopiustech.com", "", "SeleBF", 82, "MID", "ADC", "Argentina"));
        usuarios.add(new RegistroUsuarios("Ivan", "123454444", "ivan@kopiustech.com", "", "IvanGG", 70, "ADC", "SOPORTE", "Argentina"));
        usuarios.add(new RegistroUsuarios("Manuel", "12986743", "manuel@kopiustech.com", "", "ManuV", 78, "SOPORTE", "TOP", "Argentina"));

        return usuarios;
    }

    // Verificador de Rol
    private void verificarRol(String bodyText, String rol, String jugador) {
        String expected = rol + ": " + jugador;
        if (bodyText.contains(expected)) {
            System.out.println(" " + expected + " encontrado correctamente.");
        } else {
            System.out.println(" Falta o incorrecto: " + expected);
            fail("No se encontró el texto esperado: " + expected);
        }
    }

    // Se declara el driver de Selenium
    private WebDriver driver;


    //Antes de Cada Test :
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
    @DisplayName("Registro de Usuarios")
    @Order(1)
    public void testRegistro() {
        List<RegistroUsuarios> usuarios = Jugadores();
        Random random = new Random();

        for (RegistroUsuarios usuario : usuarios) {
            try {
                System.out.println("Testeando usuario: " + usuario.getName() + " - " + usuario.getIgn());

                driver.get(PathUtils.getHtmlPath("registro.html"));

                driver.findElement(By.name("nombre")).sendKeys(usuario.getName());
                driver.findElement(By.name("telefono")).sendKeys(usuario.getPhone());
                driver.findElement(By.name("email")).sendKeys(usuario.getEmail());
                driver.findElement(By.name("discord")).sendKeys(usuario.getDiscord());
                driver.findElement(By.name("ign")).sendKeys(usuario.getIgn());
                driver.findElement(By.name("nivel")).sendKeys(String.valueOf(usuario.getLevel()));

                new Select(driver.findElement(By.name("rol_principal"))).selectByVisibleText(usuario.getMainRol());
                new Select(driver.findElement(By.name("rol_secundario"))).selectByVisibleText(usuario.getSecRol());
                new Select(driver.findElement(By.name("pais"))).selectByVisibleText(usuario.getCountry());

                int decision = random.nextInt(2); // 0 = Registrarse, 1 = Cancelar

                if (decision == 0) {
                    System.out.println("Decisión: REGISTRARSE");
                    driver.findElement(By.cssSelector("button[type='submit']")).click();

                    // Esperar y validar alert
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
                    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                    String alertText = alert.getText();
                    System.out.println("Alerta: " + alertText);
                    assertFalse(alertText.isEmpty(), "El mensaje de alerta está vacío");
                    Thread.sleep(2000);
                    alert.accept();
                } else {
                    System.out.println("Decisión: CANCELAR");
                    driver.findElement(By.cssSelector(".btn.btn-danger")).click();
                }

            } catch (Exception e) {
                System.out.println("Error en la prueba con usuario: " + usuario.getName());
                e.printStackTrace();
                fail("Excepción inesperada: " + e.getMessage());
            }
        }
    }

    @Test
    @DisplayName("Nombre de Equipo")
    @Order(2)
    public void testNombreEquipo() {
        try {
            String path = PathUtils.getHtmlPath("nombre_equipo.html");
            driver.get(path);


            WebElement inputNombre = driver.findElement(By.name("nombre_equipo"));


            inputNombre.sendKeys("LosQA");
            Thread.sleep(2000);
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


    @Test
    @DisplayName("Panel Admin ")
    @Order(3)
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
        WebElement volverBtn = driver.findElement(By.xpath("/html/body/div/div/div/div/dl/button/a"));
        volverBtn.click();

        // Esperar hasta que el campo "fecha" esté presente nuevamente
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.name("fecha")));

// 💡 Reencontrar los elementos porque se recargó el DOM
        WebElement fechaInput1 = driver.findElement(By.name("fecha"));
        WebElement horaInput1 = driver.findElement(By.name("hora"));
        fechaInput1.sendKeys(fechaHoy);
        horaInput1.sendKeys("18:30");

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

        // 6. Validar que estamos nuevamente en panel_admin.html
        assertTrue(driver.getCurrentUrl().contains("panel_admin.html"));
        System.out.println(" Retorno  al Panel de Admin desde el mail.");
    }


    @Test
    @DisplayName("Test Panel admin boton volver")
    @Order(4)
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

    @Test
    @DisplayName("Equipo Formado")
    @Order(5)
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
            String textoLider = lider.getText();
            if (textoLider.contains("Andres")) {
                System.out.println(" na na na na " + textoLider);
            } else {
                System.out.println(" Líder no coincide: " + textoLider);
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



}
