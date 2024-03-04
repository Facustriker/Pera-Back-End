package Pera.Back.CU.Controllers;

import Pera.Back.CU.Entities.AuthUsuario;
import Pera.Back.CU.Services.AuthUsuarioServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/authUsuario")
public class AuthUsuarioController extends BaseControllerImpl<AuthUsuario, AuthUsuarioServiceImpl>{



}
