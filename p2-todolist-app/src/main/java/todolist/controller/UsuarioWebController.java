package todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import todolist.service.UsuarioService;
import todolist.dto.UsuarioData;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UsuarioWebController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registered")
    public String listaUsuarios(HttpSession session, Model model) {
        if (session == null || session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/errorAcceso";
        }
        List<UsuarioData> usuarios = usuarioService.findAll();
        model.addAttribute("usuarios", usuarios);
        return "listaUsuarios";
    }

    @GetMapping("/registered/{id}")
    public String descripcionUsuario(@PathVariable("id") Long id, HttpSession session, Model model) {
        if (session == null || session.getAttribute("idUsuarioLogeado") == null) {
            return "redirect:/errorAcceso";
        }
        UsuarioData usuario = usuarioService.findById(id);
        if (usuario == null) {
            return "redirect:/registered";
        }
        model.addAttribute("usuario", usuario);
        return "descripcionUsuario";
    }

    @PostMapping("/usuarios/{id}/toggleActivo")
    public String toggleActivo(@PathVariable Long id, HttpSession session) {
        Long idAdmin = (Long) session.getAttribute("idUsuarioLogeado");
        UsuarioData admin = usuarioService.findById(idAdmin);

        if (admin == null || !admin.isAdmin()) {
            return "redirect:/errorAcceso";
        }

        UsuarioData usuario = usuarioService.findById(id);
        if (usuario != null) {
            usuarioService.cambiarEstadoUsuario(id, !usuario.isActivo());
        }
        return "redirect:/registered";
    }

}
