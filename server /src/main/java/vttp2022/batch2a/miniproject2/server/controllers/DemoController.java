package vttp2022.batch2a.miniproject2.server.controllers;

import static vttp2022.batch2a.miniproject2.server.Utils.generateHash;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.services.AuthenticationService;

@RestController
@RequestMapping(path="/api/v1/demo-controller")
public class DemoController {

  @Autowired private AuthenticationService authSvc;

  @GetMapping(path = "/demo")
  public ResponseEntity<String> getDemo() {
    String vv = generateHash("demo998774@mailinator.com", 1000);
    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>" + vv.length());

    JsonObject demoObj = Json.createObjectBuilder()
        .add("greetings", "Hello, from secure endpoint.")
        .build();
    return ResponseEntity.ok(demoObj.toString());
  }

  @GetMapping(path = "/some")
  public ResponseEntity<String> getSome() {
    
    String role = authSvc.getRole();
    
    if (role.equalsIgnoreCase("ADMIN")) {
      return ResponseEntity.ok("admin");
    }

    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("not admin: unauthorized");
  }
  
}
