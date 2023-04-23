package vttp2022.batch2a.miniproject2.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.services.AuthenticationService;

@Controller
@RequestMapping(path = "/api/v1/user")
public class UserController {
  
  @Autowired AuthenticationService authSvc;

  @GetMapping(path = "/getUser")
  @ResponseBody
  public ResponseEntity<String> getUser() {

    JsonObject userObj = authSvc.getUser();

    return ResponseEntity.ok(userObj.toString());
  }
}
