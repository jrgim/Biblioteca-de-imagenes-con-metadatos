package imagenes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.ImagingException;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.jpeg.exif.ExifRewriter;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputDirectory;
import org.apache.commons.imaging.formats.tiff.write.TiffOutputSet;

public class Metadatos {
    private short iso = 0;
    private String propietario = null, fechaOriginal = null, modeloObjetivo = null, exposicion = null;
    private double apertura = 0, velocidadObturacion = 0, altura = 0, anchura = 0;
    private String rutaCarpeta;
    private String nombreImagen;

    /**
     * Constructor
     * 
     * @param rutaCarpeta
     * @param nombreImagen sin formato(.jpeg)
     */
    public Metadatos(String rutaCarpeta, String nombreImagen) {
        this.rutaCarpeta = rutaCarpeta;
        this.nombreImagen = nombreImagen;
    }

    /**
     * Funcion para introducir metadatos en una imagen con la ruta dada en la clase
     * Metadatos
     * 
     * @param met_fechaOriginal
     * @param met_iso
     * @param met_propietario
     * @param met_modeloObjetivo
     * @param met_apertura
     * @param met_velocidadObturacion
     * @param met_exposicion
     * @throws IOException
     * @throws ImagingException
     * @throws ImagingException
     */
    public void changeMetadata(String met_fechaOriginal, short met_iso,
            String met_propietario, String met_modeloObjetivo, double met_apertura, double met_velocidadObturacion,
            String met_exposicion)
            throws IOException, ImagingException, ImagingException {

        String imagen = rutaCarpeta + "/" + nombreImagen + ".jpeg";
        final File jpegImageFile = new File(imagen);

        String fileName = jpegImageFile.getName();

        String fileNameWithoutExtension = fileName.substring(0, fileName.lastIndexOf(".jpeg"));
        final File dst = new File(jpegImageFile.getParent(), fileNameWithoutExtension + "_m.jpeg");
        
        try (FileOutputStream fos = new FileOutputStream(dst); OutputStream os = new BufferedOutputStream(fos)) {
            TiffOutputSet outputSet = null;
            final ImageMetadata metadata = Imaging.getMetadata(jpegImageFile);
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
            if (null != jpegMetadata) {
                final TiffImageMetadata exif = jpegMetadata.getExif();
                if (null != exif) {
                    outputSet = exif.getOutputSet();
                }
            }
            if (null == outputSet) {
                outputSet = new TiffOutputSet();
            }

            {
                final TiffOutputDirectory exifDirectory = outputSet.getOrCreateExifDirectory();
                // =AQUI SE CAMBIAN LOS METADATOS
                // =1º Eliminar la etiqueta de metadatos existente y agregar metadatos
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                exifDirectory.add(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, met_fechaOriginal);
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_ISO);
                exifDirectory.add(ExifTagConstants.EXIF_TAG_ISO, met_iso);
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_CAMERA_OWNER_NAME);
                exifDirectory.add(ExifTagConstants.EXIF_TAG_CAMERA_OWNER_NAME, met_propietario);
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_LENS_MODEL);
                exifDirectory.add(ExifTagConstants.EXIF_TAG_LENS_MODEL, met_modeloObjetivo);
                RationalNumber rationalApertura = new RationalNumber((int) (met_apertura * 10), 10);
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_APERTURE_VALUE);
                exifDirectory.add(ExifTagConstants.EXIF_TAG_APERTURE_VALUE, rationalApertura);
                RationalNumber rationalVelocidadObturacion = new RationalNumber((int) (met_velocidadObturacion * 10),
                        10);
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE);
                exifDirectory.add(ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE, rationalVelocidadObturacion);
                exifDirectory.removeField(ExifTagConstants.EXIF_TAG_EXPOSURE);
                exifDirectory.add(ExifTagConstants.EXIF_TAG_EXPOSURE, met_exposicion);
            }

            new ExifRewriter().updateExifMetadataLossless(jpegImageFile, os, outputSet);
            deleteImage(jpegImageFile);
        }
    }

    /**
     * Funcion para borrar una imagen
     * 
     * @param imagenABorrar
     * @return
     */
    static private boolean deleteImage(File imagenABorrar) {
        return imagenABorrar.delete();
    }

    /**
     * Metodo que extrae metadatos y los guarda en la clase
     * 
     * @throws ImagingException
     * @throws IOException
     */
    public void extraerMetadatos() throws ImagingException, IOException {
        String imagen = rutaCarpeta + "/" + nombreImagen + "_m.jpeg";
        File imagenInicial = new File(imagen);
        RationalNumber RacionalApertura, RacionalVelocidadObturacion;

        if (imagen.endsWith(".jpg") || imagen.endsWith(".jpeg")) {
            JpegImageMetadata m = (JpegImageMetadata) Imaging.getMetadata(imagenInicial);

            if (m.getExif() != null) {
                TiffImageMetadata t = m.getExif();
                TiffField a = null;

                if (t.findField(ExifTagConstants.EXIF_TAG_ISO) != null) {
                    a = t.findField(ExifTagConstants.EXIF_TAG_ISO);
                    iso = Short.parseShort(a.getValueDescription());
                }
                if (t.findField(ExifTagConstants.EXIF_TAG_CAMERA_OWNER_NAME) != null) {
                    a = t.findField(ExifTagConstants.EXIF_TAG_CAMERA_OWNER_NAME);
                    propietario = a.getValueDescription();
                }
                if (t.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL) != null) {
                    a = t.findField(ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL);
                    fechaOriginal = a.getValueDescription();
                }
                if (t.findField(ExifTagConstants.EXIF_TAG_LENS_MODEL) != null) {
                    a = t.findField(ExifTagConstants.EXIF_TAG_LENS_MODEL);
                    modeloObjetivo = a.getValueDescription();
                }
                if (t.findField(ExifTagConstants.EXIF_TAG_APERTURE_VALUE) != null) {
                    a = t.findField(ExifTagConstants.EXIF_TAG_APERTURE_VALUE);
                    RacionalApertura = (RationalNumber) a.getValue();
                    apertura = RacionalApertura.doubleValue();
                }
                if (t.findField(ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE) != null) {
                    a = t.findField(ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE);
                    RacionalVelocidadObturacion = (RationalNumber) a.getValue();
                    velocidadObturacion = RacionalVelocidadObturacion.doubleValue();
                }
                if (t.findField(ExifTagConstants.EXIF_TAG_EXPOSURE) != null) {
                    a = t.findField(ExifTagConstants.EXIF_TAG_EXPOSURE);
                    exposicion = a.getValueDescription();
                }
                BufferedImage imagenTam = ImageIO.read(imagenInicial);
                altura = imagenTam.getHeight();
                anchura = imagenTam.getWidth();
            }
        }
    }

    /**
     * Obtener ISO
     * 
     * @return iso
     */
    public short getISO() {
        return iso;
    }

    /**
     * Obtener Propietario
     * 
     * @return
     */
    public String getPropietario() {
        return propietario;
    }

    /**
     * Obtener fecha imagen
     * 
     * @return
     */
    public String getFechaOriginal() {
        return fechaOriginal;
    }

    public String getModeloObjetivo() {
        return modeloObjetivo;
    }

    public String getExposicion() {
        return exposicion;
    }

    public double getApertura() {
        return apertura;
    }

    public double getVelocidadObturacion() {
        return velocidadObturacion;
    }

    public double getAltura() {
        return altura;
    }

    public double getAnchura() {
        return anchura;
    }
}
