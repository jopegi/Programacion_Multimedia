package josevi.android.com.quicktrade;

import java.io.Serializable;

/**
 * Created by josevi on 31/12/2017.
 */

public class Producto implements Serializable{

    //atributos de la clase
    String usuario;         //Se relacionará con el atributo nick de la clase Usuario
    String nombre;
    String descripcion;
    String categoria;       //Opciones categoría: tecnología, coches, hogar.
    Double precio;

    String uid;             //Uid del usuario logeado

    //Constructor por defecto
    public Producto() {
    }

    /*
    //Constructor parámetros
    public Producto(String usuario, String nombre, String descripcion, String categoria, Double precio) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
    }
    */

    //Constructor parámetros
    public Producto(String usuario, String nombre, String descripcion, String categoria, Double precio, String uid) {
        this.usuario = usuario;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;
        this.uid = uid;
    }


    //Métodos setter y getter
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        uid = uid;
    }

    public String toString(){
        String mensaje = "Producto: " +nombre+ ", Descripción: "+descripcion+", Precio(€): "+precio;
        return mensaje;
    }

}
