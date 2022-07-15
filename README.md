# testing-vehicle-system-spring-boot

# Selenium

Selenium is an open source umbrella project for a range of tools and libraries aimed at supporting browser automation. It provides a playback tool for authoring functional tests across most modern web browsers, without the need to learn a test scripting language.

Selenium is the first thing that comes to mind when one is planning to automate the testing of web applications. Selenium is a beneficial tool because it is not only open source but also a portable software testing framework for web applications that support multiple languages like Java, C#, Ruby, Python. Choosing the right language depends on the application under test, the supporting community, available test automation frameworks, usability, elegance, and of course, seamless build integration.

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
	void getAccount() throws Exception {
		this.mockMvc.perform(get("/accounts/1")
				.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType("application/json"))
			.andExpect(jsonPath("$.name").value("Lee"));
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
