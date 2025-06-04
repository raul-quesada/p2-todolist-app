package todolist.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import todolist.dto.UsuarioData;
import todolist.service.UsuarioService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioWebControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @Test
    public void listaUsuariosRedirigeSiNoHaySesion() throws Exception {
        mockMvc.perform(get("/registered")) // no sessionAttr significa sin sesión
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/errorAcceso"));
    }

    @Test
    public void listaUsuariosMuestraUsuariosSiSesionActiva() throws Exception {
        UsuarioData usuario = new UsuarioData();
        usuario.setId(1L);
        usuario.setEmail("test@test.com");
        usuario.setNombre("Raúl Q.");

        when(usuarioService.findById(1L)).thenReturn(usuario);

        mockMvc.perform(get("/registered").sessionAttr("idUsuarioLogeado", 1L))
                .andExpect(model().attributeExists("usuarios"));
    }

    @Test
    public void toggleActivoBloqueaYDesbloqueaUsuario() throws Exception {
        UsuarioData admin = new UsuarioData();
        admin.setId(1L);
        admin.setAdmin(true);

        UsuarioData usuario = new UsuarioData();
        usuario.setId(2L);
        usuario.setActivo(true);

        when(usuarioService.findById(admin.getId())).thenReturn(admin);
        when(usuarioService.findById(usuario.getId())).thenReturn(usuario);

        mockMvc.perform(post("/usuarios/2/toggleActivo").sessionAttr("idUsuarioLogeado", admin.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/registered"));
    }
}
