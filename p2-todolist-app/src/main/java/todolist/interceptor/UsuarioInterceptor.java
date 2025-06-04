package todolist.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import todolist.authentication.ManagerUserSession;
import todolist.service.UsuarioService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UsuarioInterceptor implements HandlerInterceptor {

    @Autowired
    private ManagerUserSession managerUserSession;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        Long userId = managerUserSession.usuarioLogeado();
        if (userId != null) {
            request.setAttribute("usuario", usuarioService.findById(userId));
        }
        return true;
    }
}
