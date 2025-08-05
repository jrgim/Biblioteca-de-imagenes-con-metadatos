package gui;

import javax.swing.*;

import imagenes.Imagen;

import java.awt.*;
import java.util.List;

public class FrameFiltro extends JFrame {
    /**
     * Frame usado al pulsar el botón de filtrar para mostrar las imágenes filtradas
     * 
     * @param imagenes
     */
    public FrameFiltro(List<Imagen> imagenes) {
        setLayout(new BorderLayout());

        JPanel panelImagenes = new JPanel();
        panelImagenes.setLayout(new GridLayout(0, 1)); // 1 columna, filas las q sean necesarias

        for (Imagen imagen : imagenes) {
            // Panel para cada imagen
            JPanel panel = new JPanel();
            panel.setLayout(new BorderLayout());

            // Label para cada imagen
            JLabel label = new JLabel(new ImageIcon(imagen.getRuta()));
            panel.add(label, BorderLayout.CENTER);

            JPanel panelMetadatos = new JPanel();
            panelMetadatos.setLayout(new GridLayout(3, 1)); // Configuracion del layout

            JLabel labelNombre = new JLabel("Nombre: " + imagen.getNombre());
            panelMetadatos.add(labelNombre);
            JLabel labelRuta = new JLabel("Ruta: " + imagen.getRuta());
            panelMetadatos.add(labelRuta);
            JLabel labelFecha = new JLabel("Fecha: " + imagen.getFecha());
            panelMetadatos.add(labelFecha);
            JLabel labelISO = new JLabel("ISO: " + imagen.getISO());
            panelMetadatos.add(labelISO);
            panel.add(panelMetadatos, BorderLayout.SOUTH);

            panelImagenes.add(panel);
        }

        JScrollPane scrollPane = new JScrollPane(panelImagenes);
        scrollPane.setPreferredSize(new Dimension(800, 600)); // Tamaño scroll
        add(scrollPane, BorderLayout.CENTER);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}