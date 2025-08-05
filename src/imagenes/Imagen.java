package imagenes;

public class Imagen {
    String nombre;
    String ruta;
    Metadatos metadatos;

    /**
     * Constructor imagen
     * 
     * @param nombre
     * @param ruta
     */
    public Imagen(String nombre, String ruta) {
        this.nombre = nombre;
        this.ruta = ruta;
    }

    public String getNombre() {
        return nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public String getFecha() {
        return metadatos.getFechaOriginal();
    }

    public short getISO() {
        return metadatos.getISO();
    }

    /**
     * Funcion para introducir metadatos
     * 
     * @param metadatos
     */
    public void setMetadatos(Metadatos metadatos) {
        this.metadatos = metadatos;

    }
}
