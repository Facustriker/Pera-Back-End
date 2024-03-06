package Pera.Back.CU.Controllers;

import Pera.Back.CU.Entities.Rol;
import Pera.Back.CU.Services.RolServiceImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/rol")
public class RolController extends BaseControllerImpl<Rol, RolServiceImpl>{
}
