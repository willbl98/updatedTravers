package com.traversconsultingus.tcwebshell.controllers.request;

import com.traversconsultingus.tcwebshell.entity.Role;
import com.traversconsultingus.tcwebshell.dao.RoleDAO;
import javassist.NotFoundException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value="/api/roles")
@CommonsLog
public class RolesController {
    @Autowired
    private RoleDAO roleDAO;

    @GetMapping("/{id}")
    public Role get(@PathVariable long id) throws NotFoundException{

        Role role = roleDAO.findRoleById(id);
        if(role == null){
            throw new NotFoundException("Role with id "+id+" was not found.");
        }
        return role;
    }

    @PostMapping("/")
    public Role save(@RequestParam Map<String, String> requestParams, Role m, BindingResult result, Model model) {
        m = roleDAO.saveRole(m);
        return m;
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {

        Role m = roleDAO.findRoleById(id);
        if(m != null) {
            roleDAO.deleteRole(m);
            return "Role with id "+id+" successfully deleted";

        } else {
            return "Role with id "+id+" was not found and could not be deleted";
        }
    }


    @GetMapping("/list")
    public Map<String, List<Role>> list() {
        Map<String, List<Role>> outMap = new HashMap<String, List<Role>>();
        List<Role> list = roleDAO.listRoles();
        outMap.put("data", list);
        return outMap;
    }

}
