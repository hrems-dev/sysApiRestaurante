package pe.edu.upeu.sys_api_restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportacionExcelResponse {

    private int totalImportados;
    private int totalErrores;
    private List<String> errores;
    private String mensaje;
}
