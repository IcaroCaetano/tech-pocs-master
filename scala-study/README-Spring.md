## **Spring with Scala**

Spring can be used with Scala to build robust applications by leveraging **Spring Boot**, **Spring Core**, **Spring Data**, **Spring Security**, and other Spring modules. Scala’s functional and concise nature makes it a great fit for Spring-based applications.

### **Prerequisites**
1. Add the dependencies to `build.sbt`:
```sbt
enablePlugins(JavaAppPackaging)

val springVersion = "3.2.6"
val springBootVersion = "3.2.6"

scalaVersion := "2.13.12"

libraryDependencies ++= Seq(
  "org.springframework.boot" % "spring-boot-starter-web" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-data-jdbc" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-security" % springBootVersion,
  "org.springframework.boot" % "spring-boot-starter-cache" % springBootVersion,
  "com.github.ben-manes.caffeine" % "caffeine" % "3.1.8",
  "org.springframework.boot" % "spring-boot-starter-test" % springBootVersion % Test
)
```

---

## **1. Spring Core - IoC (Inversion of Control)**
In **IoC**, Spring manages the lifecycle and dependencies of objects.

### **Example: Using `@Component` for Dependency Injection**
```scala
import org.springframework.stereotype.Component

@Component
class EmailService {
  def sendEmail(message: String): Unit = println(s"Sending email: $message")
}
```

### **Injecting Dependency**
```scala
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class NotificationService @Autowired()(emailService: EmailService) {
  def notifyUser(): Unit = emailService.sendEmail("Hello, Scala!")
}
```

---

## **2. Spring Core - Bean Scopes**
Spring supports different scopes for beans.

### **Singleton Scope (Default)**
```scala
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
@Scope("singleton")
class SingletonBean {
  def getMessage: String = "I am a Singleton Bean"
}
```

### **Prototype Scope**
```scala
@Component
@Scope("prototype")
class PrototypeBean {
  def getMessage: String = "I am a Prototype Bean"
}
```

---

## **3. Spring Core - Setter vs Constructor Injection**
### **Constructor Injection (Recommended)**
```scala
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService @Autowired()(val emailService: EmailService)
```

### **Setter Injection**
```scala
@Service
class UserService {
  private var emailService: EmailService = _

  @Autowired
  def setEmailService(emailService: EmailService): Unit = {
    this.emailService = emailService
  }
}
```

---

## **4. Spring Core - Bean Post Processor**
```scala
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.stereotype.Component

@Component
class CustomBeanPostProcessor extends BeanPostProcessor {
  override def postProcessBeforeInitialization(bean: Any, beanName: String): Any = {
    println(s"Before Initialization: $beanName")
    bean
  }

  override def postProcessAfterInitialization(bean: Any, beanName: String): Any = {
    println(s"After Initialization: $beanName")
    bean
  }
}
```

---

## **5. Spring Core - @Value for Configuration**
### **application.properties**
```properties
app.name=ScalaSpringApp
```

### **Injecting Configuration Values**
```scala
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppConfig {
  @Value("${app.name}")
  val appName: String = ""

  def getAppName: String = appName
}
```

---

## **6. Spring Boot - REST Support & RestTemplate**
### **Creating a REST Controller**
```scala
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(Array("/api"))
class UserController {
  @GetMapping(Array("/user"))
  def getUser: String = "John Doe"
}
```

### **Consuming APIs with RestTemplate**
```scala
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ApiService {
  private val restTemplate = new RestTemplate()

  def fetchData(): String = restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/1", classOf[String])
}
```

---

## **7. Spring Boot - Integration Testing with REST**
```scala
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpStatus, ResponseEntity}
import org.junit.jupiter.api.Assertions._

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

  @Autowired
  var restTemplate: TestRestTemplate = _

  @Test
  def testGetUser(): Unit = {
    val response: ResponseEntity[String] = restTemplate.getForEntity("/api/user", classOf[String])
    assertEquals(HttpStatus.OK, response.getStatusCode)
    assertEquals("John Doe", response.getBody)
  }
}
```

---

## **8. Spring Data JDBC**
### **Entity Definition**
```scala
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("users")
case class User(@Id id: Long, name: String)
```

### **Repository Definition**
```scala
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
trait UserRepository extends CrudRepository[User, Long]
```

---

## **9. Caching with Caffeine**
```scala
import org.springframework.cache.annotation.{Cacheable, EnableCaching}
import org.springframework.stereotype.Service

@EnableCaching
@Service
class CacheService {
  @Cacheable("users")
  def getUser(id: Long): User = {
    println("Fetching from DB")
    User(id, "Alice")
  }
}
```

---

## **10. Configuring Transaction Management with JPA**
```scala
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionalService {
  @Transactional
  def updateUser(id: Long, name: String): Unit = {
    // Database operations with rollback support
  }
}
```

---

## **11. Spring Security - CSRF Protection**
CSRF protection is enabled by default. To **disable it** (not recommended):
```scala
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.context.annotation.Bean
import org.springframework.security.web.SecurityFilterChain

@Bean
def securityFilterChain(http: HttpSecurity): SecurityFilterChain = {
  http.csrf().disable()
  http.build()
}
```

---

## **12. Spring Security - Authorizing HTTP Requests**
```scala
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.security.web.SecurityFilterChain

@Bean
def securityFilterChain(http: HttpSecurity): SecurityFilterChain = {
  http
    .authorizeHttpRequests(auth => auth
      .requestMatchers("/admin/**").hasRole("ADMIN")
      .requestMatchers("/user/**").authenticated()
      .anyRequest().permitAll()
    )
    .formLogin()
  http.build()
}
```