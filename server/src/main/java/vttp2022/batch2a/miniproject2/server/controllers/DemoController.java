package vttp2022.batch2a.miniproject2.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.Utils;
import vttp2022.batch2a.miniproject2.server.services.AuthenticationService;

@RestController
@RequestMapping(path="/api/v1/demo-controller")
public class DemoController {

  @Autowired private AuthenticationService authSvc;

  @GetMapping(path = "/demo")
  public ResponseEntity<String> getDemo() {
    JsonObject demoObj = Json.createObjectBuilder()
        .add("greetings", "Hello, from secure endpoint")
        .build();
    return ResponseEntity.ok(demoObj.toString());
  }

  @GetMapping(path = "/admin")
  public ResponseEntity<String> getSome() {
    
    String role = authSvc.getRole();
    
    if (role.equalsIgnoreCase("ADMIN")) {
      JsonObject adminObj = Json.createObjectBuilder()
          .add("greetings", "Hello, from admin's secure endpoint")
          .build();
      return ResponseEntity.ok(adminObj.toString());
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
        .body(Utils.createError("Only admins are allowed through this endpoint")
            .toString()
        );
  }
  
}
