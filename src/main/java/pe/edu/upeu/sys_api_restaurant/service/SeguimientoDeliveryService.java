package pe.edu.upeu.sys_api_restaurant.service;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class SeguimientoDeliveryService {

    public String calcularTiempoTranscurrido(LocalDateTime inicio) {
        if (inicio == null) return "00:00:00";
        Duration duracion = Duration.between(inicio, LocalDateTime.now());
        long horas = duracion.toHours();
        long minutos = duracion.toMinutes() % 60;
        long segundos = duracion.getSeconds() % 60;
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
}
