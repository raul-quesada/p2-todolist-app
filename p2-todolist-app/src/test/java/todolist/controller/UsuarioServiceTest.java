package todolist.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import todolist.dto.UsuarioData;
import todolist.service.UsuarioService;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @Test
    public void existeAdminDevuelveTrueSiHayAdmin() {
        // Crear admin
        UsuarioData admin = new UsuarioData();
        admin.setEmail("admin@test.com");
        admin.setPassword("1234");
        admin.setAdmin(true);

        usuarioService.registrar(admin);

        boolean resultado = usuarioService.existeAdmin();

        assertThat(resultado).isTrue();
    }

    @Test
    public void existeAdminDevuelveFalseSiNoHayAdmin() {
        boolean resultado = usuarioService.existeAdmin();
        assertThat(resultado).isFalse();
    }

    @Test
    public void cambiarEstadoUsuarioDesactivaYActiva() {
        UsuarioData usuario = new UsuarioData();
        usuario.setEmail("usuario@test.com");
        usuario.setPassword("1234");
        usuario.setAdmin(false);
        usuario.setActivo(true);
        usuario = usuarioService.registrar(usuario);

        // Desactivar usuario
        usuarioService.cambiarEstadoUsuario(usuario.getId(), false);
        UsuarioData modificado = usuarioService.findById(usuario.getId());
        assertThat(modificado.isActivo()).isFalse();

        // Activar usuario
        usuarioService.cambiarEstadoUsuario(usuario.getId(), true);
        modificado = usuarioService.findById(usuario.getId());
        assertThat(modificado.isActivo()).isTrue();
    }
}