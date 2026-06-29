package pe.edu.upeu.sys_api_restaurant.service;

import org.junit.jupiter.api.Test;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.entity.QRLugar;

import static org.junit.jupiter.api.Assertions.*;

class QRLugarServiceTest {

    @Test
    void shouldGenerateQrForLugar() {
        LugarAtencion lugar = LugarAtencion.builder()
                .nombreLugar("Mesa 1")
                .tipoLugar("mesa")
                .direccion("Local principal")
                .capacidadMaxima(4)
                .estadoLugar(true)
                .build();

        QRLugar qr = QRLugar.builder()
                .codigoQR("test-qr")
                .urlQR("http://localhost:8080/api/qr/validate/test-qr")
                .estadoQR(true)
                .build();

        assertNotNull(lugar);
        assertNotNull(qr);
        assertEquals("mesa", lugar.getTipoLugar());
        assertTrue(qr.isEstadoQR());
    }
}
