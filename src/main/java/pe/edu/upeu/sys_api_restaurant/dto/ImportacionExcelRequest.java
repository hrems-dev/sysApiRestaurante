package pe.edu.upeu.sys_api_restaurant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImportacionExcelRequest {

    private String archivo;
    private String tipo;
}
