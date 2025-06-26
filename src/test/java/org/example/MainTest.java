package org.example;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MainTest {
    private WebDriver driver;
    private String chromeDriverPath = "c:/Users/ChristianZamora/Desktop/chromedriver-win64/chromedriver.exe";

    @BeforeEach
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        driver = new ChromeDriver();
    }

    @AfterEach
    public void destroy(){
        if (driver != null){
            driver.quit();
        }
    }

    private List<FormA> obtenerUsuarios() {
        List<FormA> usuarios = new ArrayList<>();
        usuarios.add(new FormA("Niko", "B", "QA Mentor", "10+"));
        usuarios.add(new FormA("juan1", "mrtn1", "Dev mmsr", "5-9"));
        usuarios.add(new FormA("juan", "mrtn", "Dev msr", "2-4"));
        usuarios.add(new FormA("juan12", "mrtn12", "Dev jr", "0-1"));
        return usuarios;
    }

    @Test
    public void testFormularioCompleto() {
        List<FormA> usuarios = obtenerUsuarios();

        for (FormA usuario : usuarios) {
            System.out.println("Testeando usuario: " + usuario.getFirstName() + " " + usuario.getLastName());

            driver.get("https://formy-project.herokuapp.com/form");

            WebElement firstNameInput = driver.findElement(By.xpath("(//input[@id='first-name'])[1]"));
            firstNameInput.sendKeys(usuario.getFirstName());

            WebElement lastNameInput = driver.findElement(By.xpath("(//input[@id='last-name'])[1]"));
            lastNameInput.sendKeys(usuario.getLastName());

            WebElement jobTitleInput = driver.findElement(By.xpath("(//input[@id='job-title'])[1]"));
            jobTitleInput.sendKeys(usuario.getJobTitle());

            WebElement radioButton = driver.findElement(By.xpath("(//input[@id='radio-button-2'])[1]"));
            radioButton.click();

            WebElement dropdown = driver.findElement(By.xpath("(//select[@id='select-menu'])[1]"));
            Select select = new Select(dropdown);
            select.selectByVisibleText(usuario.getYearsOfExp());

            WebElement submitButton = driver.findElement(By.xpath("//a[@role='button']"));
            submitButton.click();

            // Esperar a que el mensaje de confirmación aparezca
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("(//div[@role='alert'])[1]")));

            String tituloPagina = driver.getTitle();
            assertEquals("Formy", tituloPagina, "El título de la página no es el esperado");


            WebElement mensajeConfirmacion = driver.findElement(By.xpath("(//div[@role='alert'])[1]"));
            String mensaje = mensajeConfirmacion.getText();
            assertEquals("The form was successfully submitted!", mensaje, "El mensaje de confirmación no es el esperado");
        }
    }

    @Test
    public void camposVacios(){
        // Dado: un formulario vacío
        driver.get("https://formy-project.herokuapp.com/form");

        // Cuando: intentamos enviar el formulario sin completar campos
        WebElement submitButton = driver.findElement(By.cssSelector("a[role='button']"));
        submitButton.click();

        // Entonces: se mantiene en la misma página
        String tituloPagina = driver.getTitle();
        assertEquals("Formy", tituloPagina, "El título de la página no es el esperado");
    }

}
