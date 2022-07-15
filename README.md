# testing-vehicle-system-spring-boot

# Selenium

So, what actually is the Selenium framework? Simply put, Selenium is a popular automation testing suite which can be used to automate the desktop and mobile web browser interactions. You can perform automation testing by writing code in any of your preferred language supported by Selenium and can easily run your automation scripts to automate testing of an application or a process.

When people think of Selenium, they probably think of the Selenium WebDriver, which is understandable as it is the most used tool in the suite. But to fully utilize Selenium and choose the correct tool for the job, it is good to know what parts make up the popular testing system.

Selenium WebDriver is a browser-specific driver which helps in accessing and launching the different browsers whether it’s desktop browsers or mobile browsers. That means it does not support for example Windows applications. It provides an interface to write and run automation scripts. Every browser has different drivers to run tests.

WebDriver has the capability to test modern and dynamic websites, sites where content is changing dynamically with a click of a button for example. As you will find out later when we go through the architecture, it works by interacting with the browser in more or less the same way as a real user would.

The Selenium WebDriver architecture works in the following way. You write your tests in your preferred programming language, this is communicated in JSON over HTTP (REST API) to the browser-specific driver which then, in turn, instantiate and communicates via HTTP to the browser itself and the browser communicates back with an HTTP response.

Benefits of Selenium and why it is a good tool for automation testing:

- Open Source: Selenium is open source, this means that no licensing or cost is required, it is totally free to download and use. This is not the case for many other automation tools out there.

- Mimic User Actions: As stated earlier, Selenium WebDriver is able to mimic user input, in real scenarios, you are able to automate events like key presses, mouse clicks, drag and drop, click and hold, selecting and much more.

- Easy Implementation: Selenium WebDriver is known for being a user-friendly automation tool. Selenium being Open Source means that users are able to develop extensions for their own needs.

- Tool for every scenario: As mentioned earlier, Selenium is a suite of tools, and you will most likely find something that fits your scenario and your way of working.

- Language Support: One big benefit is multilingual support. Selenium supports all major languages like Java, JavaScript, Python, Ruby, C sharp, Perl, .Net and PHP, giving the developer a lot of freedom and flexibility.

- Browser, Operating System & Device support: Selenium supports many different browsers Chrome, Firefox, Opera, Internet Explorer, Edge, and Safari as well as operating systems (Windows, Linux, Mac)

- Framework Support: Selenium also supports a multitude of frameworks like Maven, Junit, TestNG to make it easier to automate testing. CI and CD tools like Jenkins is also supported, for automating the deployment process.

- Reusability: Scripts written for WebDriver is cross-browser compatible. Testers can therefore run multiple testing scenarios with the same base.

- Community Support: The Selenium community is quite active and open. Therefore, there is a lot of information and help available when needed.

- Advanced User Input: With WebDriver it is possible to request clicking of the browser back and front buttons. A practical feature when testing money        transfer applications for example. This feature is not found in many tools, especially open source.

```
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeleniumScenarioTest {

    @Autowired
    VehicleService vehicleService;

    @Autowired
    UserService userService;

    @Autowired
    VehicleBrandService vehicleBrandService;

    private HtmlUnitDriver driver;

    private static VehicleBrand vehicleBrand1;
    private static VehicleBrand vehicleBrand2;

    private static String user = "user";
    private static String admin = "admin";

    private static boolean dataInitialized = false;

    @BeforeEach
    private void setup(){
        this.driver=new HtmlUnitDriver(true);
        initData();
    }

    @AfterEach
    public void destroy(){
        if(this.driver != null){
            this.driver.close();
        }
    }

    private void initData(){
        if(!dataInitialized){
            vehicleBrand1=vehicleBrandService.add("Volkswagen");

            regularUser=userService.register(user,user,user,user,user);
            adminUser=userService.register(admin,admin,admin,admin,admin);
            adminUser.setRole(Role.ROLE_ADMINISTRATOR);

            dataInitialized=true;
        }
    }
    
    @Test
    public void testAddVehicleWithAdminAuth() throws Exception{
        VehiclesPage vehiclesPage=VehiclesPage.to(this.driver);
        LoginPage loginPage=LoginPage.openLogin(this.driver);
        vehiclesPage=LoginPage.doLogin(this.driver,loginPage,admin,admin);
        vehiclesPage=AddOrEditVehicle.addVehicle(this.driver,vehicleBrand1.getName(),"golf", String.valueOf(VehicleType.CAR),"10000.0");
        vehiclesPage.assertElements(1,1,1,1,1);
    }
```

# MockMvc

MockMvc is defined as a main entry point for server-side Spring MVC testing. Tests with MockMvc lie somewhere between between unit and integration tests. They aren't unit tests because endpoints are tested in integration with a Mocked MVC container with mocked inputs and dependencies.

Spring MVC Test Framework
The Spring MVC Test framework provides first class support for testing Spring MVC code with a fluent API that you can use with JUnit, TestNG, or any other testing framework. It is built on the Servlet API mock objects from the spring-test module and, hence, does not use a running Servlet container. It uses the DispatcherServlet to provide full Spring MVC runtime behavior and provides support for loading actual Spring configuration with the TestContext framework in addition to a standalone mode, in which you can manually instantiate controllers and test them one at a time.

Server-Side Tests
You can write a plain unit test for a Spring MVC controller by using JUnit or TestNG. To do so, instantiate the controller, inject it with mocked or stubbed dependencies, and call its methods (passing MockHttpServletRequest, MockHttpServletResponse, and others, as necessary). However, when writing such a unit test, much remains untested: for example, request mappings, data binding, type conversion, validation, and much more. Furthermore, other controller methods such as @InitBinder, @ModelAttribute, and @ExceptionHandler may also be invoked as part of the request processing lifecycle.

The goal of Spring MVC Test is to provide an effective way to test controllers by performing requests and generating responses through the actual DispatcherServlet.

Spring MVC Test also provides client-side support for testing code that uses the RestTemplate. Client-side tests mock the server responses and also do not use a running server.

Spring MVC Test builds on the familiar “mock” implementations of the Servlet API available in the spring-test module. This allows performing requests and generating responses without the need for running in a Servlet container. For the most part, everything should work as it does at runtime with a few notable exceptions, as explained in spring-mvc-test-vs-end-to-end-integration-tests

Example: 
```
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.;

@SpringJUnitWebConfig(locations = "test-servlet-context.xml")
class ExampleTests {

	MockMvc mockMvc;

	@BeforeEach
	void setup(WebApplicationContext wac) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
    	public void testAddVehicle() throws Exception{
        MockHttpServletRequestBuilder vehicleRequest = MockMvcRequestBuilders.post("/vehicles/add")
                .param("brand", "Toyota")
                .param("model", "Corolla")
                .param("type", VehicleType.CAR.name())
                .param("price", "30000.0");
        this.mockMvc.perform(vehicleRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/vehicles"));
    }
}
```

# JUnit

JUnit is a Java unit testing framework that's one of the best test methods for regression testing. An open-source framework, it is used to write and run repeatable automated tests. As with anything else, the JUnit testing framework has evolved over time.

1.1. Overview
JUnit is a popular unit-testing framework in the Java ecosystem. JUnit 5 added many new features based on the Java 8 version of the language.

1.2. Configuration for using JUnit 5
To use JUnit 5 you have to make the libraries available for your test code. Jump to the section which is relevant to you, for example read the Maven part, if you are using Maven as build system.

1.3. How to define a test in JUnit?
A JUnit test is a method contained in a class which is only used for testing. This is called a Test class. To mark a method as a test method, annotate it with the @Test annotation. This method executes the code under test.

2.1. Assertions and assumptions
JUnit 5 comes with multiple assert statements, which allows you to test your code under test. Simple assert statements like the following allow to check for true, false or equality. All of them are static methods from the org.junit.jupiter.api.Assertions.* package.

2.2. Testing for exceptions
Testing that certain exceptions are thrown are be done with the org.junit.jupiter.api.Assertions.expectThrows() assert statement. You define the expected Exception class and provide code that should throw the exception.

3.1. https://www.vogella.com/tutorials/JUnit/article.html Full documentation for JUnit testing

```
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
public class JUnitProgram {
    @Test
    public void getVehicleTest()
    {
        List<Vehicle> list = new ArrayList<Vehicle>();
        VehicleBrand vehicleBrand=new VehicleBrand("BMW");
        Vehicle vehicle1 = new Vehicle(vehicleBrand, "B", VehicleType.CAR, 2000.00);
        Vehicle vehicle2 = new Vehicle(vehicleBrand, "Q7", VehicleType.CAR, 2000.00);
        Vehicle vehicle3 = new Vehicle(vehicleBrand, "B", VehicleType.CAR, 2000.00);
        list.add(vehicle1);
        list.add(vehicle2);
        list.add(vehicle3);
        when(vehicleRepository.findAll()).thenReturn(list);
        List<Vehicle> vehicles = vehicleRepository.findAll();
        assertEquals(3, list.size());
        verify(vehicleRepository, times(1)).findAll();
    }
}
```
