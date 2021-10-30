# spring-boot-tests
A full project showcasing the test cases using Spring boot standard `spring-boot-starter-test` dependency. 

## Topics covered
* Unit test cases 
    * Assertions
    * Exceptions validations
* Repository tests
    * @DataJpaTest 
* Service test cases
    * `MockBean`
    * Specifying mock calls
    * Verifying mock calls
* Controller Test cases   
    * `MockMvC`
    * `AutoConfigureMvC` 
    * Validating JSON output 
    * Passing params to mock MVC call 
* Integration test cases
    * @SpringBootTest  

## Testing Libraries Used
* JUnit
* Mockito
* Jupiter
* MockMVC

## Project details
Spring boot version: 2.5.6

Dependencies

```
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.jetbrains</groupId>
			<artifactId>annotations</artifactId>
			<version>RELEASE</version>
			<scope>compile</scope>
		</dependency>
	</dependencies>
```

