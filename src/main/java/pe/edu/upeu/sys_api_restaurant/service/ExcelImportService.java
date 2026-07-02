package pe.edu.upeu.sys_api_restaurant.service;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pe.edu.upeu.sys_api_restaurant.dto.ImportacionExcelResponse;
import pe.edu.upeu.sys_api_restaurant.entity.CategoriaProducto;
import pe.edu.upeu.sys_api_restaurant.entity.LugarAtencion;
import pe.edu.upeu.sys_api_restaurant.entity.Mesa;
import pe.edu.upeu.sys_api_restaurant.entity.Producto;
import pe.edu.upeu.sys_api_restaurant.entity.Rol;
import pe.edu.upeu.sys_api_restaurant.repository.CategoriaProductoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.LugarAtencionRepository;
import pe.edu.upeu.sys_api_restaurant.repository.MesaRepository;
import pe.edu.upeu.sys_api_restaurant.repository.ProductoRepository;
import pe.edu.upeu.sys_api_restaurant.repository.RolRepository;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ExcelImportService {

    private final CategoriaProductoRepository categoriaRepo;
    private final ProductoRepository productoRepo;
    private final LugarAtencionRepository lugarRepo;
    private final MesaRepository mesaRepo;
    private final RolRepository rolRepo;

    @Transactional
    public ImportacionExcelResponse importarExcel(MultipartFile archivo) {
        List<String> errores = new ArrayList<>();
        int totalImportados = 0;

        Map<String, Integer> detalle = new LinkedHashMap<>();
        detalle.put("ROLES", 0);
        detalle.put("CATEGORIAS", 0);
        detalle.put("PRODUCTOS", 0);
        detalle.put("LUGARES", 0);
        detalle.put("MESAS", 0);

        try (Workbook workbook = new XSSFWorkbook(archivo.getInputStream())) {

            if (workbook.getSheet("ROLES") != null) {
                int count = importarRoles(workbook.getSheet("ROLES"), errores);
                detalle.put("ROLES", count);
                totalImportados += count;
            }

            if (workbook.getSheet("CATEGORIAS") != null) {
                int count = importarCategorias(workbook.getSheet("CATEGORIAS"), errores);
                detalle.put("CATEGORIAS", count);
                totalImportados += count;
            }

            if (workbook.getSheet("PRODUCTOS") != null) {
                int count = importarProductos(workbook.getSheet("PRODUCTOS"), errores);
                detalle.put("PRODUCTOS", count);
                totalImportados += count;
            }

            if (workbook.getSheet("LUGARES") != null) {
                int count = importarLugares(workbook.getSheet("LUGARES"), errores);
                detalle.put("LUGARES", count);
                totalImportados += count;
            }

            if (workbook.getSheet("MESAS") != null) {
                int count = importarMesas(workbook.getSheet("MESAS"), errores);
                detalle.put("MESAS", count);
                totalImportados += count;
            }

            int totalHojas = 0;
            for (int v : detalle.values()) totalHojas += v > 0 ? 1 : 0;

            String mensaje = totalHojas > 0
                    ? "Importación completada: " + detalle.entrySet().stream()
                        .filter(e -> e.getValue() > 0)
                        .map(e -> e.getValue() + " " + e.getKey())
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("")
                    : "No se encontraron hojas con datos (CATEGORIAS, PRODUCTOS, LUGARES)";

            return ImportacionExcelResponse.builder()
                    .totalImportados(totalImportados)
                    .totalErrores(errores.size())
                    .errores(errores)
                    .mensaje(mensaje)
                    .build();

        } catch (IOException e) {
            return ImportacionExcelResponse.builder()
                    .totalImportados(0)
                    .totalErrores(1)
                    .errores(List.of("Error al leer el archivo: " + e.getMessage()))
                    .mensaje("Error al procesar el archivo Excel")
                    .build();
        }
    }

    private int importarCategorias(Sheet sheet, List<String> errores) {
        int count = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                String nombre = getCellString(row, 0);
                String descripcion = getCellString(row, 1);
                if (nombre == null || nombre.isBlank()) continue;

                if (categoriaRepo.findByNombreCategoria(nombre).isEmpty()) {
                    CategoriaProducto cat = CategoriaProducto.builder()
                            .nombreCategoria(nombre.trim())
                            .descripcionCategoria(descripcion != null ? descripcion.trim() : null)
                            .estadoCategoria(true)
                            .build();
                    categoriaRepo.save(cat);
                    count++;
                }
            } catch (Exception e) {
                errores.add("Fila " + (i + 1) + " (CATEGORIAS): " + e.getMessage());
            }
        }
        return count;
    }

    private int importarProductos(Sheet sheet, List<String> errores) {
        int count = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                String nombre = getCellString(row, 0);
                String descripcion = getCellString(row, 1);
                String precioStr = getCellString(row, 2);
                String categoriaNombre = getCellString(row, 3);
                String stockStr = getCellString(row, 4);

                if (nombre == null || nombre.isBlank()) continue;

                BigDecimal precio = BigDecimal.ZERO;
                if (precioStr != null && !precioStr.isBlank()) {
                    precio = new BigDecimal(precioStr.trim());
                }

                int stock = 0;
                if (stockStr != null && !stockStr.isBlank()) {
                    stock = Integer.parseInt(stockStr.trim());
                }

                CategoriaProducto categoria = null;
                if (categoriaNombre != null && !categoriaNombre.isBlank()) {
                    categoria = categoriaRepo.findByNombreCategoria(categoriaNombre.trim()).orElse(null);
                }

                if (productoRepo.findByNombreProducto(nombre.trim()).isPresent()) {
                    continue;
                }

                Producto prod = Producto.builder()
                        .nombreProducto(nombre.trim())
                        .descripcionProducto(descripcion != null ? descripcion.trim() : null)
                        .precioProducto(precio)
                        .stockProducto(stock)
                        .categoria(categoria)
                        .estadoProducto(true)
                        .build();
                productoRepo.save(prod);
                count++;
            } catch (Exception e) {
                errores.add("Fila " + (i + 1) + " (PRODUCTOS): " + e.getMessage());
            }
        }
        return count;
    }

    private int importarLugares(Sheet sheet, List<String> errores) {
        int count = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                String nombre = getCellString(row, 0);
                String tipo = getCellString(row, 1);
                String direccion = getCellString(row, 2);
                String capacidadStr = getCellString(row, 3);

                if (nombre == null || nombre.isBlank()) continue;
                if (tipo == null || tipo.isBlank()) tipo = "SALON";

                if (lugarRepo.findByNombreLugar(nombre).isEmpty()) {
                    Integer capacidad = null;
                    if (capacidadStr != null && !capacidadStr.isBlank()) {
                        capacidad = Integer.parseInt(capacidadStr.trim());
                    }

                    LugarAtencion lugar = LugarAtencion.builder()
                            .nombreLugar(nombre.trim())
                            .tipoLugar(tipo.trim().toUpperCase())
                            .direccion(direccion != null ? direccion.trim() : null)
                            .capacidadMaxima(capacidad)
                            .estadoLugar(true)
                            .build();
                    lugarRepo.save(lugar);
                    count++;
                }
            } catch (Exception e) {
                errores.add("Fila " + (i + 1) + " (LUGARES): " + e.getMessage());
            }
        }
        return count;
    }

    private int importarRoles(Sheet sheet, List<String> errores) {
        int count = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                String nombre = getCellString(row, 0);
                String descripcion = getCellString(row, 1);
                if (nombre == null || nombre.isBlank()) continue;

                if (rolRepo.findByNombreRol(nombre.trim()).isEmpty()) {
                    Rol rol = Rol.builder()
                            .nombreRol(nombre.trim().toUpperCase())
                            .descripcionRol(descripcion != null ? descripcion.trim() : null)
                            .estadoRol(true)
                            .build();
                    rolRepo.save(rol);
                    count++;
                }
            } catch (Exception e) {
                errores.add("Fila " + (i + 1) + " (ROLES): " + e.getMessage());
            }
        }
        return count;
    }

    private int importarMesas(Sheet sheet, List<String> errores) {
        int count = 0;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            try {
                String nombre = getCellString(row, 0);
                String lugarNombre = getCellString(row, 1);
                String capacidadStr = getCellString(row, 2);

                if (nombre == null || nombre.isBlank()) continue;
                if (lugarNombre == null || lugarNombre.isBlank()) continue;

                LugarAtencion lugar = lugarRepo.findByNombreLugar(lugarNombre.trim()).orElse(null);
                if (lugar == null) {
                    errores.add("Fila " + (i + 1) + " (MESAS): Lugar no encontrado: " + lugarNombre);
                    continue;
                }

                int capacidad = 2;
                if (capacidadStr != null && !capacidadStr.isBlank()) {
                    capacidad = Integer.parseInt(capacidadStr.trim());
                }

                if (mesaRepo.findByNombreMesaAndLugar_IdLugar(nombre.trim(), lugar.getIdLugar()).isPresent()) {
                    continue;
                }

                Mesa mesa = Mesa.builder()
                        .nombreMesa(nombre.trim())
                        .lugar(lugar)
                        .capacidad(capacidad)
                        .estadoMesa(true)
                        .build();
                mesaRepo.save(mesa);
                count++;
            } catch (Exception e) {
                errores.add("Fila " + (i + 1) + " (MESAS): " + e.getMessage());
            }
        }
        return count;
    }

    public byte[] generarPlantilla() throws IOException {
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet rolSheet = wb.createSheet("ROLES");
            String[] rolHeaders = {"nombre", "descripcion"};
            Row rolHeader = rolSheet.createRow(0);
            for (int i = 0; i < rolHeaders.length; i++) {
                rolHeader.createCell(i).setCellValue(rolHeaders[i]);
            }
            rolSheet.createRow(1).createCell(0).setCellValue("Ej: CAJA");
            rolSheet.autoSizeColumn(0);
            rolSheet.autoSizeColumn(1);

            Sheet catSheet = wb.createSheet("CATEGORIAS");
            String[] catHeaders = {"nombre", "descripcion"};
            Row catHeader = catSheet.createRow(0);
            for (int i = 0; i < catHeaders.length; i++) {
                catHeader.createCell(i).setCellValue(catHeaders[i]);
            }
            catSheet.createRow(1).createCell(0).setCellValue("Ej: Bebidas");
            catSheet.autoSizeColumn(0);
            catSheet.autoSizeColumn(1);

            Sheet prodSheet = wb.createSheet("PRODUCTOS");
            String[] prodHeaders = {"nombre", "descripcion", "precio", "categoria", "stock"};
            Row prodHeader = prodSheet.createRow(0);
            for (int i = 0; i < prodHeaders.length; i++) {
                prodHeader.createCell(i).setCellValue(prodHeaders[i]);
            }
            Row prodEj = prodSheet.createRow(1);
            prodEj.createCell(0).setCellValue("Ej: Coca Cola 500ml");
            prodEj.createCell(1).setCellValue("Gaseosa personal");
            prodEj.createCell(2).setCellValue(5.00);
            prodEj.createCell(3).setCellValue("Bebidas");
            prodEj.createCell(4).setCellValue(100);
            prodSheet.autoSizeColumn(0);
            prodSheet.autoSizeColumn(1);
            prodSheet.autoSizeColumn(2);
            prodSheet.autoSizeColumn(3);
            prodSheet.autoSizeColumn(4);

            Sheet lugSheet = wb.createSheet("LUGARES");
            String[] lugHeaders = {"nombre", "tipo", "direccion", "capacidad"};
            Row lugHeader = lugSheet.createRow(0);
            for (int i = 0; i < lugHeaders.length; i++) {
                lugHeader.createCell(i).setCellValue(lugHeaders[i]);
            }
            Row lugEj = lugSheet.createRow(1);
            lugEj.createCell(0).setCellValue("Ej: Mesa 1");
            lugEj.createCell(1).setCellValue("SALON");
            lugEj.createCell(2).setCellValue("Principal");
            lugEj.createCell(3).setCellValue(4);
            lugSheet.autoSizeColumn(0);
            lugSheet.autoSizeColumn(1);
            lugSheet.autoSizeColumn(2);
            lugSheet.autoSizeColumn(3);

            Sheet mesaSheet = wb.createSheet("MESAS");
            String[] mesaHeaders = {"nombre", "lugar", "capacidad"};
            Row mesaHeader = mesaSheet.createRow(0);
            for (int i = 0; i < mesaHeaders.length; i++) {
                mesaHeader.createCell(i).setCellValue(mesaHeaders[i]);
            }
            Row mesaEj = mesaSheet.createRow(1);
            mesaEj.createCell(0).setCellValue("Ej: Mesa 1");
            mesaEj.createCell(1).setCellValue("Ej: Salon Principal");
            mesaEj.createCell(2).setCellValue(4);
            mesaSheet.autoSizeColumn(0);
            mesaSheet.autoSizeColumn(1);
            mesaSheet.autoSizeColumn(2);

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                wb.write(out);
                return out.toByteArray();
            }
        }
    }

    private String getCellString(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) return null;

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue();
            case NUMERIC -> {
                double val = cell.getNumericCellValue();
                yield val == Math.floor(val) ? String.valueOf((long) val) : String.valueOf(val);
            }
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            default -> null;
        };
    }
}
