# Java - Multiple Environment

1. Nội dung
    1. Tạo controller
        
        ```python
        package com.example.multiple_environment.multipleenvironment.controller;
        
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.stereotype.Controller;
        import org.springframework.ui.Model;
        
        @Controller
        @RequestMapping("/")
        public class HelloController {
        	@Value("${app.title}")
        	private String title;
        	
        	@GetMapping("")
        	public String hello(Model model) {
        		model.addAttribute("title", title);
        		return "index";
        	}
        }
        ```
        
    2. Tạo template
        
        ```python
        <!DOCTYPE HTML>
        <html xmlns:th="http://www.thymeleaf.org">
        <head> 
            <title th:text="${title}"></title> 
        </head>
        <body>
            <h1 th:text="${title}"></h1>
        </body>
        </html>
        ```
        
    3. Thêm maven properties
        
        ```python
        <properties>
        	...
        	<profile>${profile:dev}</profile>
        </properties>
        ```
        
    4. Tạo các spring profile
        - application.properties
            
            ```python
            spring.profiles.active=@profile@
            app.title=Hello world
            server.port=8084
            ```
        
        - application-dev.properties
            
            ```python
            app.title=Dev environment
            server.port=8085
            ```
            
        - application-test.properties
            
            ```python
            app.title=Test environment
            server.port=8086
            ```
            
        - application-prod.properties
            
            ```python
            app.title=Prod environment
            server.port=8087
            ```
            
    5. Build
        
        ```python
        # build with test environment
        $ mvnw -f . -Dprofile=test package
        
        # build with prod environment
        $ mvnw -f . -Dprofile=prod package
        ```
        
    6. Run package
        
        ```python
        $ java -jar ./target/multiple-environment-0.0.1-SNAPSHOT.jar
        ```
        
    7. Kết quả
        
        ![Untitled](https://i.imgur.com/OB3OXhi.png)
        
        ![Untitled](https://i.imgur.com/r7mJy3i.png)
        
2. Tài liệu tham khảo
    