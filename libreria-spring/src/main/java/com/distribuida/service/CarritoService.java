package com.distribuida.service;

import com.distribuida.model.Carrito;

public interface CarritoService {

    Carrito getOrCreateByClienteId (int clienteId, String Token);
    Carrito addItem(int clienteId, int libroId,int cantidad);
    Carrito updateItemCantidad(int clienteId, long carritoItemid, int nuevaCantidad);
    void removeItem(int clienteId, long carritoItemId);
    void clear(int clienteId);
    Carrito getByClienteId(int clienteId);




    Carrito getOrCreateByToken (String Token);
    Carrito addItem(String token, int libroId,int cantidad);
    Carrito updateItemCantidad(String token, long carritoItemid, int nuevaCantidad);
    void removeItem(String token, long carritoItemId);
    void clearByToken(String token);
    Carrito getByToken(String token);

}
