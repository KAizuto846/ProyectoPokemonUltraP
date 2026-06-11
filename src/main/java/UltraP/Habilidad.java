package UltraP;

public class Habilidad {

    private String nombre;
    private String descripcion;
    private int poder;
    private int precision; 

    public Habilidad(String nombre, String descripcion, int poder, int precision) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.poder = poder;
        this.precision = precision;
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

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    @Override
    public String toString() {
        return nombre + " (Poder: " + poder + ", Precisión: " + precision + "%)";
    }
}
