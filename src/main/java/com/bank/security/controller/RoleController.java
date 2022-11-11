package com.bank.security.controller;

import com.bank.security.domain.Role;
import com.bank.security.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    RoleService roleService;


    @PostMapping("save")
    public ResponseEntity<Role> saveRole(@Valid @RequestBody Role role)
    {
        return ResponseEntity.ok().body(roleService.saveRole(role));
    }

    @PutMapping("update")
    public ResponseEntity<Role> updateRole(@Valid @RequestBody Role role)
    {
        return ResponseEntity.ok().body(roleService.updateRole(role));
    }

    @DeleteMapping("{id}/delete")
    public ResponseEntity<?> deleteRole(@PathVariable("id") Long id)
    {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/get")
    public ResponseEntity<Role> getRole(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(roleService.getRole(id));
    }

    @GetMapping()
    public ResponseEntity<List<Role>> getRoles(){
        return ResponseEntity.ok().body(roleService.getRoles());
    }
}
