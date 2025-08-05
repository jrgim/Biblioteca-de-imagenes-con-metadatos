package imagenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import java.nio.file.Files;
import java.nio.file.Paths;

public class HelperCarpetasImagenes {
    private List<Imagen> coleccionImagenesConMetadatos = new ArrayList<>();
    private List<String> propietario = new ArrayList<>();
    private List<String> modeloObjetivo = new ArrayList<>();
    private List<String> exposicion = new ArrayList<>();
    private List<Double> apertura = new ArrayList<>();
    private List<Double> velocidadObturacion = new ArrayList<>();
    private List<Short> iso = new ArrayList<>();
    private List<Double> longitudFocal = new ArrayList<>();
    
    /**
     * Constructor, anade los valores a los sets.
     */
    public HelperCarpetasImagenes() {
        propietario.add("John Doe");
        propietario.add("Jane Smith");
        propietario.add("Michael Johnson");
        propietario.add("Emily Brown");
        propietario.add("Chris Davis");
        propietario.add("Jennifer Wilson");
        propietario.add("David Martinez");
        propietario.add("Lisa Anderson");
        propietario.add("Robert Taylor");
        propietario.add("Mary Thomas");

        modeloObjetivo.add("Canon EF 50mm f/1.8 STM");
        modeloObjetivo.add("Nikon AF-S DX NIKKOR 35mm f/1.8G");
        modeloObjetivo.add("Sony FE 85mm f/1.8");
        modeloObjetivo.add("Tamron SP 24-70mm f/2.8 Di VC USD G2");
        modeloObjetivo.add("Sigma 18-35mm f/1.8 DC HSM Art");
        modeloObjetivo.add("Olympus M.Zuiko Digital ED 12-40mm f/2.8 PRO");
        modeloObjetivo.add("Fujifilm XF 35mm f/2 R WR");
        modeloObjetivo.add("Panasonic Lumix G 25mm f/1.7 ASPH");
        modeloObjetivo.add("Leica Summilux-TL 35mm f/1.4 ASPH");
        modeloObjetivo.add("Pentax HD DA 16-85mm f/3.5-5.6 ED DC WR");

        exposicion.add("1/250");
        exposicion.add("1/500");
        exposicion.add("1/1000");
        exposicion.add("1/2000");
        exposicion.add("1/4000");
        exposicion.add("1/8000");
        exposicion.add("1/16000");
        exposicion.add("1/32000");
        exposicion.add("1/64000");
        exposicion.add("1/128000");

        apertura.add(2.8);
        apertura.add(4.0);
        apertura.add(5.6);
        apertura.add(8.0);
        apertura.add(11.0);
        apertura.add(16.0);
        apertura.add(22.0);
        apertura.add(32.0);
        apertura.add(45.0);
        apertura.add(64.0);

        velocidadObturacion.add(1 / 2.0);
        velocidadObturacion.add(1 / 30.0);
        velocidadObturacion.add(1 / 40.0);
        velocidadObturacion.add(1 / 20.0);
        velocidadObturacion.add(1 / 40.0);
        velocidadObturacion.add(1 / 80.0);
        velocidadObturacion.add(1 / 16.0);
        velocidadObturacion.add(1 / 32.0);
        velocidadObturacion.add(1 / 64.0);
        velocidadObturacion.add(1 / 128.0);

        iso.add((short) 100);
        iso.add((short) 200);
        iso.add((short) 400);
        iso.add((short) 800);
        iso.add((short) 1600);
        iso.add((short) 3200);
        iso.add((short) 6400);
        iso.add((short) 12800);
        iso.add((short) 25600);
        iso.add((short) 51200);

        longitudFocal.add(18.0);
        longitudFocal.add(24.0);
        longitudFocal.add(35.0);
        longitudFocal.add(50.0);
        longitudFocal.add(70.0);
        longitudFocal.add(85.0);
        longitudFocal.add(105.0);
        longitudFocal.add(135.0);
        longitudFocal.add(200.0);
        longitudFocal.add(300.0);
    }

    /**
     * Crea un directorio si no existe. punto 1.1
     * @param directoryPath
     */
    public static void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    /**
     * Crea carpetas y subcarpetas segun orden cronologico.(anyo/mes/dia)
     * @param rootDirectory
     * @param startYear
     * @param endYear
     * @param maxFoldersPerYear
     * @param minDays
     * @param maxDays
     */
    public static void createRandomFolders(String rootDirectory, int startYear, int endYear, int maxFoldersPerYear,
            int minDays, int maxDays) {
        Random random = new Random();

        for (int year = startYear; year <= endYear; year++) {
            String yearDirectory = rootDirectory + year;
            new File(yearDirectory).mkdirs();

            int numberOfMonths = random.nextInt(maxFoldersPerYear + 1); //num aleatorio de meses
            for (int month = 1; month <= numberOfMonths; month++) {
                String monthDirectory = yearDirectory + "/" + month;
                new File(monthDirectory).mkdirs();
                int numberOfDays = random.nextInt(maxDays - minDays + 1) + minDays; // num aleatorio de dias
                for (int day = 1; day <= numberOfDays; day++) {
                    String dayDirectory = monthDirectory + "/" + day;
                    new File(dayDirectory).mkdirs();
                }
            }
        }
    }

    // = Punto 1.2
    /**
     * Metodo que crea una imagen con formas de manera aleatoria
     * @param directoryPath
     * @param imageName
     */
    public static void createRandomImage(String directoryPath, String imageName) {
        int width = 500;
        int height = 500;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();

        Random random = new Random();
        for (int i = 0; i < 50; i++) {
            float r = random.nextFloat();
            float g = random.nextFloat();
            float b = random.nextFloat();
            Color randomColor = new Color(r, g, b);
            g2d.setColor(randomColor);
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int size = random.nextInt(50) + 10;

            //Formas aleatorias q dibuja
            switch (random.nextInt(3)) {
                case 0:
                    g2d.fillRect(x, y, size, size);
                    break;
                case 1:
                    g2d.fillOval(x, y, size, size);
                    break;
                case 2:
                    g2d.drawLine(x, y, x + size, y + size);
                    break;
            }
        }
        g2d.dispose();
        File imageFile = new File(directoryPath + "/" + imageName + ".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Funcion recursiva que utiliza el metodo CreateRandomImage para rellenar una ruta dada.
     * @param directoryPath
     * @param imageName
     */
    public static void fillDirectoriesWithImage(String directoryPath, String imageName) {
        File directory = new File(directoryPath);
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    createRandomImage(file.getPath(), imageName);
                    fillDirectoriesWithImage(file.getPath(), imageName);
                }
            }
        }
    }
    // ~Punto 2.2
    /**
     * Metodo que recorre las carpetas de una ruta dada en busca de imagenes con extension .jpeg.
     * Este metodo utiliza el metodo Files.walk de java.
     * @param directoryPath
     * @return
     * @throws IOException
     */
    public List<Imagen> analizadorCarpetasImagenes(String directoryPath) throws IOException {
        List<Imagen> imagenes = new ArrayList<>();
        Files.walk(Paths.get(directoryPath))
                .filter(Files::isRegularFile)
                .filter(path -> path.toString().endsWith(".jpeg"))
                .forEach(path -> {
                    String nombreArchivo = path.getFileName().toString();
                    String nombreImagensin_m  = nombreArchivo.substring(0, nombreArchivo.length()-7);//Sin extenrion ni _m
                    Metadatos metadatos = new Metadatos(path.getParent().toString(), nombreImagensin_m);
                    Imagen imagen = new Imagen(path.getFileName().toString(), path.toString());
                    try{
                        metadatos.extraerMetadatos();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    imagen.setMetadatos(metadatos);
                    imagenes.add(imagen);
                });
        return imagenes;
    }
    
    /**
     * Metodo para introducir metadatos de manera aleatoria utilizando los sets de datos de la clase Helper.
     * Este metodo hace uso del metodo changeMetadata de la clase Metadatos. Posteriormete añade la imagen a la lista de imagenes
     * @param folderPath
     * @param imageName
     * @throws IOException
     */
    public void addMetadataRandom(String folderPath, String imageName) throws IOException {
        String imagePath = folderPath + "/" + imageName + ".jpeg";
        Random random = new Random();
        Metadatos metadatos = new Metadatos(folderPath, imageName);
        String fecha = generarFechaYHoraAleatoria();
        Short isoAleatorio = iso.get(random.nextInt(iso.size()));
        String propietarioAleatorio = propietario.get(random.nextInt(propietario.size()));
        String modeloObjetivoAleatorio = modeloObjetivo.get(random.nextInt(modeloObjetivo.size()));
        Double aperturaAleatoria = apertura.get(random.nextInt(apertura.size()));
        Double velocidadObturacionAleatoria = velocidadObturacion
                .get(random.nextInt(velocidadObturacion.size()));
        String exposicionAleatoria = exposicion.get(random.nextInt(exposicion.size()));
        metadatos.changeMetadata(fecha, isoAleatorio, propietarioAleatorio, modeloObjetivoAleatorio,
                aperturaAleatoria, velocidadObturacionAleatoria, exposicionAleatoria);
        Imagen imagen = new Imagen(imageName, imagePath);
        imagen.setMetadatos(metadatos);
        coleccionImagenesConMetadatos.add(imagen);
    }
    /**
     * Funcion para generar Fecha y hora de manera aleatoria en formato de metadato.
     * @return
     */
    private String generarFechaYHoraAleatoria() {
        Random random = new Random();
        int anyo = random.nextInt(2024 - 1900) + 1900;
        int mes = random.nextInt(12) + 1;
        int dia = random.nextInt(30) + 1;
        int hora = random.nextInt(24);
        int minuto = random.nextInt(60);
        int segundo = random.nextInt(60);
        return String.format("%04d:%02d:%02d %02d:%02d:%02d", anyo, mes, dia, hora, minuto, segundo);
    }
}
