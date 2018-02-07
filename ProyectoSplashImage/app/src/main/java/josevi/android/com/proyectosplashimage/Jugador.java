package josevi.android.com.proyectosplashimage;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by josevi on 23/01/2018.
 */

public class Jugador implements Parcelable {

    //Atributos de la clase
    private String nick = "No nick";    //Indicamos un nick por defecto
    private String nombre = "No name";  //Indicamos un nombre por defecto
    private int puntos;

    //Constructor 1
    public Jugador() {

    }

    //Constructor 2
    public Jugador(String nombre){

        this.nombre = nombre;
    }

    //Constructor 3
    public Jugador(String nick, String nombre){

        this.nick = nick;
        this.nombre = nombre;
    }

    //Métodos setter y getter
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    //Método toString()
    @Override
    public String toString() {
        return "Jugador{" +
                "nombre='" + nombre + '\'' +
                ", nick='" + nick + '\'' +
                ", puntos=" + puntos +
                '}';
    }

    protected Jugador(Parcel in) {
        nick = in.readString();
        nombre = in.readString();
        puntos = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nick);
        dest.writeString(nombre);
        dest.writeInt(puntos);
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
