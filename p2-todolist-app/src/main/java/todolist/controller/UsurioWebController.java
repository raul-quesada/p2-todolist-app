package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import todolist.service.UsuarioService;
import todolist.dto.UsuarioData;

import java.util.List;

@Controller
public class UsurioWebController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registered")
    public String listaUsuarios(Model model) {
        List<UsuarioData> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuarios";
    }

    @GetMapping("/registered/{id}")
    public String descripcionUsuario(@PathVariable("id") Long id, Model model) {
        UsuarioData usuario = usuarioService.findById(id);
        if (usuario == null) {
            // Puedes redirigir a la lista si no existe el usuario
            return "redirect:/registered";
        }
        model.addAttribute("usuario", usuario);
        return "descripcionUsuario";
    }

}
