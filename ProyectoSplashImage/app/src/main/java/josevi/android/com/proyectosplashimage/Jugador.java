package josevi.android.com.proyectosplashimage;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josevi on 23/01/2018.
 */

public class Jugador implements Parcelable {

    //Atributos de la clase
    private String uid_key;
    private String nick = "No nick";    //Indicamos un nick por defecto
    private String nombre = "No name";  //Indicamos un nombre por defecto
    private String apellidos;
    private String email;

    public Jugador(){

    }

    public Jugador(String nick, String nombre, String apellidos) {
        this.nick = nick;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }


    public Jugador(String uid_key, String nick, String nombre, String apellidos, String email) {
        this.uid_key = uid_key;
        this.nick = nick;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.email = email;
    }

    protected Jugador(Parcel in) {
        uid_key = in.readString();
        nick = in.readString();
        nombre = in.readString();
        apellidos = in.readString();
        email = in.readString();
    }

    public String getUid_key() {
        return uid_key;
    }

    public void setUid_key(String uid_key) {
        this.uid_key = uid_key;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid_key);
        dest.writeString(nick);
        dest.writeString(nombre);
        dest.writeString(apellidos);
        dest.writeString(email);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Jugador> CREATOR = new Parcelable.Creator<Jugador>() {
        @Override
        public Jugador createFromParcel(Parcel in) {
            return new Jugador(in);
        }

        @Override
        public Jugador[] newArray(int size) {
            return new Jugador[size];
        }
    };
}