package josevi.android.com.quicktrade;

/**
 * Created by josevi on 13/12/2017.
 */

public class Usuario {

    private String nick;
    private String nombre;
    private String apellidos;
    private String email;
    private String direccion;

    public Usuario() {

    }

    public Usuario(String nick, String nombre, String apellidos, String email, String direccion) {
        this.nick = nick;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
