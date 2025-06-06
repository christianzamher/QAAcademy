package org.example;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class RegistroTest {

    // Se declara el driver de Selenium
    private WebDriver driver;
    //private String chromeDriverPath = "ruta/a/chromedriver"; // Actualizar con la ruta correcta
    private String chromeDriverPath = "c:/Users/ChristianZamora/Desktop/chromedriver-win64/chromedriver.exe";
    private  String pathHtml = "C:/Users/ChristianZamora/IdeaProjects/QAAcademy/src/main/resources/TorneoHTML/";

    //Antes de Cada Test :
    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }


    //Despues de Cada Test:
    @AfterEach
    public void destroy() throws InterruptedException {
        if (driver != null){
            Thread.sleep(2000);
            driver.quit();
        }
    }

    // Array de elementos:
    private List<RegistroUsuarios> Jugadores() {
        List<RegistroUsuarios> usuarios = new ArrayList<>();
        usuarios.add(new RegistroUsuarios("Andres", "11223344", "andy@kopiustech.com", "axndy#1337" , "axdny", 85 , "TOP", "JUNGLA","Argentina"));
        usuarios.add(new RegistroUsuarios("Christian", "123456", "czamora@kopiustech.com", "", "ChrisZ" , 75, "JUNGLA" , "TOP", "Argentina"));
        usuarios.add(new RegistroUsuarios("Selene", "12345687", "sele@kopiustech.com", "", "SeleBF" , 82, "MID" , "ADC", "Argentina"));
        usuarios.add(new RegistroUsuarios("Ivan", "123454444", "ivan@kopiustech.com", "", "IvanGG" , 70, "ADC" , "SOPORTE", "Argentina"));
        usuarios.add(new RegistroUsuarios("Manuel", "12986743", "manuel@kopiustech.com", "", "ManuV" , 78, "SOPORTE" , "TOP", "Argentina"));

        return usuarios;
    }

    @Test
    public void testRegistro() {
        List<RegistroUsuarios> usuarios = Jugadores();
        Random random = new Random();

        for (RegistroUsuarios usuario : usuarios) {
            try {
                System.out.println("Testeando usuario: " + usuario.getName() + " - " + usuario.getIgn());

                driver.get(pathHtml + "registro.html");

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
}
