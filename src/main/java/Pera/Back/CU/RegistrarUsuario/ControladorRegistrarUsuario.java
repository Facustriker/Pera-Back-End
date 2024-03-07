package Pera.Back.CU.RegistrarUsuario;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@Scope("session")
@RequestMapping(path = "/RegistrarUsuario")
public class ControladorRegistrarUsuario {
    @Autowired
    protected ExpertoRegistrarUsuarioImpl experto;

    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody DTORegisterRequest request, HttpSession session) {
        System.out.println(session.getId());
        try {
            String ret = experto.register(request);
            session.setAttribute("expertoRegistrarUsuario", experto);
            return ResponseEntity.ok(ret);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "/ingresarCodigo")
    public ResponseEntity<DTOAuthResponse> ingresarCodigo(@RequestParam int codigo, HttpSession session){
        System.out.println(session.getId());
        ExpertoRegistrarUsuarioImpl experto = (ExpertoRegistrarUsuarioImpl) session.getAttribute("expertoRegistrarUsuario");
        try {
            return ResponseEntity.ok(experto.ingresarCodigo(codigo));
        } catch (Exception e) {
            DTOAuthResponse dto = new DTOAuthResponse();
            dto.setError(e.getMessage());
            return ResponseEntity.badRequest().body(dto);
        }
    }
}
