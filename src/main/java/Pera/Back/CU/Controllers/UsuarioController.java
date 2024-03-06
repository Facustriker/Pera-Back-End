package Pera.Back.CU.Controllers;

import Pera.Back.CU.Entities.Usuario;
import Pera.Back.CU.Services.UsuarioServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/usuario")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioServiceImpl>{


}
