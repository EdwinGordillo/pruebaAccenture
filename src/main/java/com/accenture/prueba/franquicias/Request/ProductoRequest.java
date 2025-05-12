package com.accenture.prueba.franquicias.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoRequest {

    private String nombre;

    private int cantidad;

    private Long sucursalId;

}
