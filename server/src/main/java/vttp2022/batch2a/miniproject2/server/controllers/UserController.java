package vttp2022.batch2a.miniproject2.server.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.JsonObject;
import vttp2022.batch2a.miniproject2.server.services.AuthenticationService;

@Controller
@RequestMapping(path = "/api/v1/user")
@CrossOrigin(origins="https://od-mini-project-2-2nd-user-link.vercel.app/")
public class UserController {
  
  @Autowired 
  private AuthenticationService authSvc;

  @GetMapping(path = "/getUser")
  @ResponseBody
  public ResponseEntity<String> getUser() {

    JsonObject userObj = authSvc.getUser();

    return ResponseEntity.ok(userObj.toString());
  }
}
