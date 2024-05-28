package Pera.Back.CU.CU5_ABMConfiguracionPrecioPremium;

import Pera.Back.Entities.*;
import Pera.Back.Functionalities.CortarSuperpuestas.SingletonCortarSuperpuestas;
import Pera.Back.Repositories.RepositorioConfiguracionPrecioPremium;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpertoABMConfiguracionPrecioPremium {

    @Autowired
    private final RepositorioConfiguracionPrecioPremium repositorioConfiguracionPrecioPremium;
    public Collection<DTOABMCPP> getConfiguraciones() throws Exception{

        Collection<DTOABMCPP> ret = new ArrayList<>();

        for (ConfiguracionPrecioPremium configuracionPrecioPremium : repositorioConfiguracionPrecioPremium.findAllOrdenadas()) {
            DTOABMCPP dto = DTOABMCPP.builder()
                    .nroConfig(configuracionPrecioPremium.getId())
                    .fechaInicio(configuracionPrecioPremium.getFhaCPP())
                    .fechaFin(configuracionPrecioPremium.getFhbCPP())
                    .build();
            ret.add(dto);
        }

        return ret;
    }

    public void altaConfiguracion(DTODetallesCPP dto) throws Exception{
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);

        if(!dto.getFechaInicio().after(calendar.getTime())) {
            throw new Exception("La fecha de inicio no puede ser pasada");
        }

        ConfiguracionPrecioPremium cpp = ConfiguracionPrecioPremium.builder()
                .fhaCPP(dto.getFechaInicio())
                .fhbCPP(dto.getFechaFin())
                .build();

        for (DTODetallesCPPPrecioPremium plan : dto.getPlanes()) {
            PrecioPremium precioPremium = PrecioPremium.builder()
                    .nombrePP(plan.getNombre())
                    .descripcion(plan.getDescripcion())
                    .diasDuracion(plan.getDuracion())
                    .precio(plan.getPrecio())
                    .build();
            cpp.addPrecio(precioPremium);
        }

        ConfiguracionPrecioPremium cppAnterior = repositorioConfiguracionPrecioPremium.obtenerCPPVigente();

        SingletonCortarSuperpuestas singletonCortarSuperpuestas = SingletonCortarSuperpuestas.getInstancia();
        singletonCortarSuperpuestas.cortar(repositorioConfiguracionPrecioPremium, dto.getFechaInicio(), dto.getFechaFin(), 0L);

        if(cppAnterior.getFhaCPP().before(cpp.getFhaCPP()) && (cppAnterior.getFhbCPP() == null || cppAnterior.getFhbCPP().after(cpp.getFhbCPP()))) {
            //Es envolvente
            ConfiguracionPrecioPremium cppDividida = repositorioConfiguracionPrecioPremium.obtenerCPPDividida(cpp.getFhbCPP(), cppAnterior.getFhbCPP());
            for (PrecioPremium precio : cppAnterior.getPrecios()) {
                cppDividida.addPrecio(PrecioPremium.builder()
                                .nombrePP(precio.getNombrePP())
                                .descripcion(precio.getDescripcion())
                                .diasDuracion(precio.getDiasDuracion())
                                .precio(precio.getPrecio())
                        .build());
            }
            repositorioConfiguracionPrecioPremium.save(cppDividida);
        }

        repositorioConfiguracionPrecioPremium.save(cpp);

    }


    public DTODetallesCPP getDetalleConfiguracion(Long nroConfig) throws Exception{

        Optional<ConfiguracionPrecioPremium> optC = repositorioConfiguracionPrecioPremium.findById(nroConfig);
        if (optC.isEmpty()) throw new Exception("No se encontró la configuración de precios premium");

        ConfiguracionPrecioPremium cpp = optC.get();

        DTODetallesCPP dto = DTODetallesCPP.builder()
                .nroConfig(cpp.getId())
                .fechaInicio(cpp.getFhaCPP())
                .fechaFin(cpp.getFhbCPP())
                .build();

        for (PrecioPremium precio : cpp.getPrecios()) {
            dto.addPlan(DTODetallesCPPPrecioPremium.builder()
                            .nombre(precio.getNombrePP())
                            .descripcion(precio.getDescripcion())
                            .duracion(precio.getDiasDuracion())
                            .precio(precio.getPrecio())
                            .build());
        }

        return dto;
    }

}
