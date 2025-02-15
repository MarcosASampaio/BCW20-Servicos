package com.soulcode.Servicos.Controllers;

import com.soulcode.Servicos.Models.User;
import com.soulcode.Servicos.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("servicos")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public List<User> usuarios() {
        return userService.listar();
    }

    @PostMapping("/usuarios")
    public ResponseEntity<User> inserirUsuario(@RequestBody User user) {
        String senhaCodificada = passwordEncoder.encode(user.getPassword());
        user.setPassword(senhaCodificada);
        user = userService.cadastrar(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
   @PutMapping("/usuarios/{loginUsuario}")
    public ResponseEntity<User> alterarSenha(@PathVariable String loginUsuario, @RequestParam("password") String password, @RequestHeader ("Authorization") String headers){
        String senhaUser = passwordEncoder.encode(password);
        userService.alterarSenha(loginUsuario, senhaUser, headers);
        return ResponseEntity.ok().build();
   }

   @PatchMapping("/usuarios/{loginUsuario}")
    public ResponseEntity<User> desativeAccount(@PathVariable String loginUsuario, Principal principal){
        if (principal.getName().equals(loginUsuario)){
            userService.desativeAccount(loginUsuario);
        }
        return ResponseEntity.ok().build();
   }
}
