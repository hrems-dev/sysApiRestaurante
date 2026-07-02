package pe.edu.upeu.sys_api_restaurant.service;

import org.springframework.stereotype.Service;
import pe.edu.upeu.sys_api_restaurant.entity.Pago;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class ValidacionPagoService {

    public boolean validar(Pago pago) {
        return ThreadLocalRandom.current().nextBoolean();
    }
}
