package tests.task40;

import application_items.Employee;
import driver.Config;
import driver.DriverSingleton;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

public class TableSortSearch {
    private static WebDriver driver;

    @BeforeAll
    public static void setup(){
        driver = DriverSingleton.getInstance().getDriver(Config.CHROME);
        driver.get("https://www.seleniumeasy.com/test/table-sort-search-demo.html");
    }

    @DisplayName("Search sorted result in the table")
    @ParameterizedTest
    @CsvSource({"30, 200000.00"})
    public void selectFromDropdown(Integer minAge, Double maxSalary){
        Select dropdown = new Select(driver.findElement(By.xpath("//select[@name='example_length']")));
        dropdown.selectByIndex(0);
        List<Employee> employees = retrieveData(minAge, maxSalary);
        for (Employee employee : employees){
            System.out.println(employee);
        }
    }

    @AfterAll
    public static void cleanup(){
        driver.close();
        driver.quit();
    }

    private List<Employee> retrieveData(Integer minAge, Double maxSalary){
        List<Employee> employees = new ArrayList<>();
        String tableCellPattern = "//table[@id='example']//tbody/tr[%s]/td[%s]";
        int numOfRow = driver.findElements(By.xpath("//table[@id='example']//tbody/tr")).size();
        int numOfCell = driver.findElements(By.xpath("//table[@id='example']//tbody/tr[1]/td")).size();
        for (int iRow = 1; iRow <= numOfRow; iRow++){
            String  employeeName = null, employeePosition = null, employeeOffice = null;
            Integer age = null;
            Double salary = null;
            for(int iCell = 1; iCell <= numOfCell; iCell++){
                switch (iCell){
                    case 1:
                        employeeName = driver.findElement(By.xpath(String.format(tableCellPattern, iRow, iCell))).getText();
                        break;
                    case 2:
                        employeePosition = driver.findElement(By.xpath(String.format(tableCellPattern, iRow, iCell))).getText();
                        break;
                    case 3:
                        employeeOffice = driver.findElement(By.xpath(String.format(tableCellPattern, iRow, iCell))).getText();
                        break;
                    case 4:
                        age = Integer.valueOf(driver.findElement(By.xpath(String.format(tableCellPattern, iRow, iCell))).getText());
                        break;
                    case 6:
                        String text = driver.findElement(By.xpath(String.format(tableCellPattern, iRow, iCell))).getText();
                        salary = Double.valueOf(text.replaceAll("[^0-9]", ""));
                        break;
                }
            }
            if (age > minAge && salary < maxSalary){
                employees.add(Employee.builder()
                                .name(employeeName)
                                .position(employeePosition)
                                .office(employeeOffice)
                        .build()
                );
            }
        }
        return employees;
    }

    /*private List<Employee> retrieveData(Integer minAge, Double maxSalary){
        String tableCellPattern = "//table[@id='example']//tbody/tr[%s]/td[%s]";
        int numOfRow = driver.findElements(By.xpath("//table[@id='example']//tbody/tr")).size();
        //List<WebElement> elements = driver.findElements(By.xpath("//table[@id='example']//tbody/tr"));
        List<Employee> employees = new ArrayList<>();
        for (int iRow = 1; iRow <= numOfRow; iRow++){
            Employee employee = new Employee();
            int numOfCell = driver.findElements(By.xpath("//table[@id='example']//tbody/tr[1]/td")).size();
            for(int iCell = 1; iCell <= numOfCell; iCell++){
                switch (iCell){
                    case 1:
                        String name = driver.findElement(By.xpath(String.format(tableCellPattern, iRow, 1))).getText();
                        employee.setName(name);
                        break;
                    case 2:
                        String positions = driver.findElement(By.xpath(String.format(tableCellPattern, iRow, 2))).getText();
                        employee.setPosition(positions);
                        break;
                    case 3:
                        String office = driver.findElement(By.xpath(String.format(tableCellPattern, iRow, 3))).getText();
                        employee.setOffice(office);
                        break;
                    case 4:
                        Integer age = Integer.valueOf(driver.findElement(By.xpath(String.format(tableCellPattern, iRow, 4))).getText());
                        employee.setAge(age);
                        break;
                    case 6:
                        String text = driver.findElement(By.xpath(String.format(tableCellPattern, iRow, 6))).getText();
                        Double salary = Double.valueOf(text.replaceAll("[^0-9]", ""));
                        employee.setSalary(salary);
                        break;
                }
            }
            if (employee.getAge() > minAge && employee.getSalary() < maxSalary){
                employees.add(employee);
            }
        }
        return employees;
    }*/


}
