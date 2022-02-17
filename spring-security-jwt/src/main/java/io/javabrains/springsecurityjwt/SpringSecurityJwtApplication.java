package io.javabrains.springsecurityjwt;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import io.javabrains.springsecurityjwt.response.MessageResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
public class SpringSecurityJwtApplication {

	public static void main(String[] args) {
		try{
			SpringApplication.run(SpringSecurityJwtApplication.class, args);
			System.out.println("JWT is running successfully");
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	@ExceptionHandler(InvalidFormatException.class)
	public ResponseEntity<MessageResponse> handleException(InvalidFormatException e) {
		return ResponseEntity.status(500).body(new MessageResponse("The server encountered a problem."));
	}
}
