package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.apache.commons.io.FilenameUtils;

import imagenes.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.tree.TreeNode;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Frame2 extends JFrame {
    private JButton BotonSeleccionCarpeta;
    private JButton BotonCrearImagenyCarpetas;
    private JTree jTree1;
    private JTable tabla;
    String selectedFolderPath = "src/files";
    private JLabel label;
    private JButton BotonAnalizarFotosCarpetas;
    private JButton BotonAnadirMetadatos;
    private JButton BotonFiltrar;
    private HelperCarpetasImagenes helper = new HelperCarpetasImagenes();
    private String nombreImagenes = "image";
    private List<Imagen> imagenesAnalizadas;

    /**
     * Frame principal para mostrar imagenes y datos
     */
    public Frame2() {
        setLayout(new BorderLayout());

        BotonSeleccionCarpeta = new JButton("Seleccionar Carpeta");
        BotonCrearImagenyCarpetas = new JButton("Crear Imagen y Carpetas");
        BotonAnadirMetadatos = new JButton("Añadir Metadatos");
        BotonFiltrar = new JButton("Filtrar");

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("src/files");
        addFilesToNode(root, new File("src/files"));
        jTree1 = new JTree(root);

        // Tabla para metadatos
        String[] columnNames = { "Titulo", "Dato" };
        DefaultTableModel modeloTabla = new DefaultTableModel(columnNames, 0);
        tabla = new JTable(modeloTabla);

        jTree1.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) jTree1.getLastSelectedPathComponent();
                if (selectedNode != null) {
                    // Ruta completa del nodo seleccionado
                    TreeNode[] nodes = selectedNode.getPath();
                    StringBuilder filePathBuilder = new StringBuilder(selectedFolderPath + "/");
                    for (int i = 1; i < nodes.length; i++) {
                        filePathBuilder.append(nodes[i].toString());
                        if (i < nodes.length - 1) {
                            filePathBuilder.append("/");
                        }
                    }

                    String filePath = filePathBuilder.toString();

                    File selectedFile = new File(filePath);
                    System.out.println("Selected file: " + selectedFile);
                    String extension = FilenameUtils.getExtension(selectedFile.getName()).toLowerCase();
                    if (selectedFile.isFile() && (extension.equals("jpg") || extension.equals("jpeg"))) {
                        try {
                            //Mostrar imagen
                            ImageIcon imageIcon = new ImageIcon(ImageIO.read(selectedFile));
                            label.setIcon(imageIcon);
                            label.revalidate();
                            label.repaint();

                            Path path = Paths.get(selectedFile.getAbsolutePath());
                            String nombreArchivo = path.getFileName().toString();
                            String nombreImagensin_m = nombreArchivo.substring(0, nombreArchivo.length() - 7);// Sin extenrion ni _m

                            // Extraer metadatos de la imagen seleccionada
                            Metadatos metadatos = new Metadatos(selectedFile.getParent().toString(), nombreImagensin_m);
                            metadatos.extraerMetadatos();
                            String propietario = metadatos.getPropietario();
                            String[] meterPropietario = { "Propietario", propietario };
                            String[] fechaOriginal = { "Fecha", metadatos.getFechaOriginal() };
                            String[] iso = { "ISO", String.valueOf(metadatos.getISO()) };
                            String[] modeloObjetivo = { "Modelo de objetivo", metadatos.getModeloObjetivo() };
                            String[] apertura = { "Apertura", String.valueOf(metadatos.getApertura()) };
                            String[] velocidadObturacion = { "Velocidad de obturación",
                                    String.valueOf(metadatos.getVelocidadObturacion()) };
                            String[] exposicion = { "Exposición", metadatos.getExposicion() };
                            String[] altura = { "Altura", String.valueOf(metadatos.getAltura()) };
                            String[] anchura = { "Anchura", String.valueOf(metadatos.getAnchura()) };
                            
                            // datos en la tabla
                            modeloTabla.setRowCount(0);
                            modeloTabla.addRow(fechaOriginal);
                            modeloTabla.addRow(meterPropietario);
                            modeloTabla.addRow(iso);
                            modeloTabla.addRow(modeloObjetivo);
                            modeloTabla.addRow(apertura);
                            modeloTabla.addRow(velocidadObturacion);
                            modeloTabla.addRow(exposicion);
                            modeloTabla.addRow(altura);
                            modeloTabla.addRow(anchura);

                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    } else {
                        System.out.println("Selected file is not a .jpg or .jpeg image");
                    }
                } else {
                    System.out.println("No node selected");
                }
            }
        });

        label = new JLabel();

        JScrollPane treeScrollPane = new JScrollPane(jTree1);
        treeScrollPane.setPreferredSize(new Dimension(200, Integer.MAX_VALUE));
        add(treeScrollPane, BorderLayout.WEST);
        add(label, BorderLayout.CENTER);
        add(new JScrollPane(tabla), BorderLayout.EAST);

        // Agregar un ActionListener a los distintos botones
        BotonSeleccionCarpeta.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    selectedFolderPath = selectedFile.getPath(); // Almacenar la ruta de la carpeta seleccionada
                    DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(selectedFolderPath);
                    addFilesToNode(newRoot, selectedFile);
                    jTree1.setModel(new DefaultTreeModel(newRoot));
                }
            }
        });

        BotonAnalizarFotosCarpetas = new JButton("Analizar Fotografías y Carpetas");
        BotonAnadirMetadatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    HelperCarpetasImagenes helper = new HelperCarpetasImagenes();
                    Files.walk(Paths.get(selectedFolderPath))
                            .filter(Files::isRegularFile)
                            .filter(p -> p.toString().endsWith(".jpg") || p.toString().endsWith(".jpeg"))
                            .forEach(p -> {
                                try {
                                    String directoryPath = p.getParent().toString();
                                    String fileNameWithExtension = p.getFileName().toString();
                                    String fileName = fileNameWithExtension.substring(0,
                                            fileNameWithExtension.lastIndexOf("."));
                                    helper.addMetadataRandom(directoryPath, fileName);
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            });

                    // Actualizar el JTree después de añadir los metadatos
                    DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(selectedFolderPath);
                    addFilesToNode(newRoot, new File(selectedFolderPath));
                    jTree1.setModel(new DefaultTreeModel(newRoot));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        BotonAnalizarFotosCarpetas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String rutaAnalizar = JOptionPane.showInputDialog("Introduzca la ruta a analizar");
                    imagenesAnalizadas = helper.analizadorCarpetasImagenes(rutaAnalizar);
                } catch (IOException ex) {
                    System.out.println("Error al analizar las fotografías y carpetas");
                }
            }
        });
        /**
         * Listener para el botón de filtrar
         * Este boton incluye un cuadro de diálogo para seleccionar el filtro a aplicar
         * y otro cuadro de diálogo para seleccionar el valor del filtro en el caso de
         * iso
         */
        BotonFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object[] opciones = { "Fecha Ascendente", "Fecha Descendente", "ISO" };
                int seleccion = JOptionPane.showOptionDialog(null, "Seleccione el filtro a aplicar", "Filtro",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opciones, opciones[0]);

                if (seleccion == 0) {
                    Collections.sort(imagenesAnalizadas, new Comparator<Imagen>() {
                        @Override
                        public int compare(Imagen img1, Imagen img2) {
                            return img1.getFecha().compareTo(img2.getFecha());
                        }
                    });

                    // Lista con las imagenes ordenadas
                    List<Imagen> imagenesOrdenadas = new ArrayList<>(imagenesAnalizadas);
                    FrameFiltro frameFiltro = new FrameFiltro(imagenesOrdenadas);
                    frameFiltro.setTitle("Filtro por fecha ascendente");
                    frameFiltro.setSize(750, 400);
                    frameFiltro.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frameFiltro.setVisible(true);

                }
                if (seleccion == 1) {
                    Collections.sort(imagenesAnalizadas, new Comparator<Imagen>() {
                        @Override
                        public int compare(Imagen img1, Imagen img2) {
                            return img2.getFecha().compareTo(img1.getFecha());
                        }
                    });
                    List<Imagen> imagenesOrdenadas = new ArrayList<>(imagenesAnalizadas);

                    FrameFiltro frameFiltro = new FrameFiltro(imagenesOrdenadas);
                    frameFiltro.setTitle("Filtro por fecha descendente");
                    frameFiltro.setSize(750, 400);
                    frameFiltro.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frameFiltro.setVisible(true);
                }
                if (seleccion == 2) {
                    Object[] opcionesISO = { "100", "1600", "12800" };
                    int seleccionISO = JOptionPane.showOptionDialog(null, "Seleccione la opción ISO", "ISO",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opcionesISO,
                            opcionesISO[0]);

                    double isoSeleccionado = Double.parseDouble((String) opcionesISO[seleccionISO]);
                    // Crear una nueva lista para las imágenes filtradas
                    List<Imagen> imagenesOrdenadas = new ArrayList<>();
                    // Filtrar las imágenes por ISO
                    for (Imagen imagen : imagenesAnalizadas) {
                        if (imagen.getISO() == isoSeleccionado) {
                            imagenesOrdenadas.add(imagen);
                        }
                    }

                    FrameFiltro frameFiltro = new FrameFiltro(imagenesOrdenadas);
                    frameFiltro.setTitle("Filtro por ISO " + isoSeleccionado);
                    frameFiltro.setSize(750, 400);
                    frameFiltro.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    frameFiltro.setVisible(true);
                }
            }
        });

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(BotonSeleccionCarpeta);
        panelSuperior.add(BotonCrearImagenyCarpetas);
        panelSuperior.add(BotonAnadirMetadatos);
        panelSuperior.add(BotonAnalizarFotosCarpetas);
        panelSuperior.add(BotonFiltrar);
        add(panelSuperior, BorderLayout.NORTH);

        /**
         * Listener para el botón de crear imágenes y carpetas
         */
        BotonCrearImagenyCarpetas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Solicitar los parámetros a través de cuadros de diálogo de entrada
                int startYear = Integer.parseInt(JOptionPane.showInputDialog("Introduce el año de inicio:"));
                int endYear = Integer.parseInt(JOptionPane.showInputDialog("Introduce el año de fin:"));
                int maxFoldersPerYear = Integer
                        .parseInt(JOptionPane.showInputDialog("Introduce el número máximo de carpetas por año:"));
                int minDays = Integer.parseInt(JOptionPane.showInputDialog("Introduce el número mínimo de días:"));
                int maxDays = Integer.parseInt(JOptionPane.showInputDialog("Introduce el número máximo de días:"));
                nombreImagenes = JOptionPane
                        .showInputDialog("Introduce el nombre por defecto de las imagenes: ");

                
                HelperCarpetasImagenes.createRandomFolders(selectedFolderPath + "/", startYear, endYear,
                        maxFoldersPerYear,
                        minDays, maxDays);
                HelperCarpetasImagenes.fillDirectoriesWithImage(selectedFolderPath, nombreImagenes);
                System.out.println("Selected folder path: " + selectedFolderPath);

                // Actualizar el JTree
                DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode(selectedFolderPath);
                addFilesToNode(newRoot, new File(selectedFolderPath));
                jTree1.setModel(new DefaultTreeModel(newRoot));
            }
        });
        /**
         * Listener para el botón de analizar fotografías y carpetas
         */
        BotonAnalizarFotosCarpetas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Llamar al método analizadorCarpetasImagenes
                try {
                    helper.analizadorCarpetasImagenes(selectedFolderPath);
                    System.out.println("Se han analizado las fotografías y carpetas");
                } catch (IOException ex) {
                    System.out.println("Error al analizar las fotografías y carpetas");
                }

            }
        });
    }

    /**
     * Funcion para añadir archivos a un nodo del JTree recursivamente a partir de
     * un directorio
     * 
     * @param node
     * @param folder
     */
    private void addFilesToNode(DefaultMutableTreeNode node, File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file.getName());
                node.add(childNode);
                if (file.isDirectory()) {
                    addFilesToNode(childNode, file);
                }
            }
        }
    }
}
