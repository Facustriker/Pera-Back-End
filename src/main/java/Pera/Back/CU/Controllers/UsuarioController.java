package Pera.Back.CU.Controllers;

import Pera.Back.CU.Entities.Usuario;
import Pera.Back.CU.Services.UsuarioServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/usuario")
public class UsuarioController extends BaseControllerImpl<Usuario, UsuarioServiceImpl>{


}
