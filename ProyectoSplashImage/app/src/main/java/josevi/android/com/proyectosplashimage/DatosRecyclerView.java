package josevi.android.com.proyectosplashimage;

/**
 * Created by josevi on 06/12/2017.
 */

public class DatosRecyclerView {

    private String nombre;
    private int imagenId;

    public DatosRecyclerView(String nombre, int imagenId) {
        this.nombre = nombre;
        this.imagenId = imagenId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImagenId() {
        return imagenId;
    }

    public void setImagenId(int imagenId) {
        this.imagenId = imagenId;
    }
}
