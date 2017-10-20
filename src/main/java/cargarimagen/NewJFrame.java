/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cargarimagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Roberto
 */
public class NewJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public NewJFrame() {
        initComponents();
        BufferedImage bmp = null;
        try {
            bmp
                    = ImageIO.read(new File("D:\\Documentos\\Videojuegos\\Netbeans\\VisionComputacional\\src\\main\\resources\\1.jpg"));
        } catch (Exception e) {
        }
        if (bmp != null) {
            imagenActual = bmp;
        }

        inversionVertical.setSelected(true);
        grupoOpcion.add(inversionVertical);
        grupoOpcion.add(inversionHorizontal);
        grupoOpcion.add(negativo);
        grupoOpcion.add(medianaTrunca);
        grupoOpcion.add(espejo);
        grupoOpcion.add(moda);
        grupoOpcion.add(cambioDeRadiante);

        //abrirImagen();
        jLabel2.setIcon(new ImageIcon(escalaGrises()));
        imagen2 = imagenActual;
        jLabel1.setIcon(new ImageIcon(imagen2));
    }

    /*------------------------------*/
    private BufferedImage imagenActual;
    private BufferedImage imagen2;

    //Método que devuelve una imagen abierta desde archivo
    //Retorna un objeto BufferedImagen
    public BufferedImage abrirImagen() {
        //Creamos la variable que será devuelta (la creamos como null)
        BufferedImage bmp = null;
        //Creamos un nuevo cuadro de diálogo para seleccionar imagen
        JFileChooser selector = new JFileChooser();
        //Le damos un título
        selector.setDialogTitle("Seleccione una imagen");
        //Filtramos los tipos de archivos
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP", "jpg", "gif", "bmp");
        selector.setFileFilter(filtroImagen);
        //Abrimos el cuadro de diálog
        int flag = selector.showOpenDialog(null);
        //Comprobamos que pulse en aceptar
        if (flag == JFileChooser.APPROVE_OPTION) {
            try {
                //Devuelve el fichero seleccionado
                File imagenSeleccionada = selector.getSelectedFile();
                String asd = imagenSeleccionada.getCanonicalPath();
                //Asignamos a la variable bmp la imagen leida
                bmp = ImageIO.read(imagenSeleccionada);
            } catch (Exception e) {
            }

        }
        //Asignamos la imagen cargada a la propiedad imagenActual
        imagenActual = bmp;
        //Retornamos el valor
        return bmp;
    }

    private int posicionActual = 0;

    public BufferedImage Rotar90(BufferedImage aux) {
        //Variables que almacenarán los píxeles
        int mediaPixel, colorSRGB;
        Color colorAux;

        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < imagen2.getWidth(); i++) {
            for (int j = 0; j < imagen2.getHeight(); j++) {
                //Almacenamos el color del píxel
                colorAux = new Color(this.imagen2.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);

                //Cambiamos a formato sRGB
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage

                aux.setRGB(imagen2.getHeight() - 1 - j, i, colorSRGB);

            }
        }
        posicionActual = (posicionActual + 90) % 360;
        imagen2 = aux;

        // TODO add your handling code here:
        aux = deepCopy(imagen2);
        jLabel1.setIcon(new ImageIcon(brillo(aux)));

        //Retornamos la imagen
        return aux;

    }

    public BufferedImage inversion(BufferedImage aux) {
        //Variables que almacenarán los píxeles
        int mediaPixel, colorSRGB;
        Color colorAux;

        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < imagen2.getWidth(); i++) {
            for (int j = 0; j < imagen2.getHeight(); j++) {
                //Almacenamos el color del píxel
                colorAux = new Color(this.imagen2.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                //Cambiamos a formato sRGB
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                if (inversionHorizontal.isSelected()) {
                    //Asignamos el nuevo valor al BufferedImage
                    aux.setRGB(aux.getWidth() - 1 - i, j, colorSRGB);
                } else if (inversionVertical.isSelected()) {
                    //Asignamos el nuevo valor al BufferedImage
                    aux.setRGB(i, aux.getHeight() - 1 - j, colorSRGB);
                } else if (espejo.isSelected()) {
                    //Asignamos el nuevo valor al BufferedImage 
                    aux.setRGB(i, j, colorSRGB);
                    aux.setRGB(aux.getWidth() - 1 - i, j, colorSRGB);

                }

            }
        }
        //Retornamos la imagen
        imagen2 = aux;
        return aux;
    }

    public BufferedImage negativo(BufferedImage aux) {
        //Variables que almacenarán los píxeles
        int mediaPixel, colorSRGB;
        Color colorAux;

        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < imagen2.getWidth(); i++) {
            for (int j = 0; j < imagen2.getHeight(); j++) {
                //Almacenamos el color del píxel
                colorAux = new Color(this.imagen2.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                mediaPixel = 255 - mediaPixel;
                //Cambiamos a formato sRGB
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage
                aux.setRGB(i, j, colorSRGB);
            }
        }
        imagen2 = aux;
        return aux;
    }

    public BufferedImage brillo(BufferedImage aux) {
        //Variables que almacenarán los píxeles
        int mediaPixel, colorSRGB;
        Color colorAux;

        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < aux.getWidth(); i++) {
            for (int j = 0; j < aux.getHeight(); j++) {
                //Almacenamos el color del píxel
                colorAux = new Color(this.imagen2.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                int suma = mediaPixel + sliderBrillo.getValue();
                if (suma > 255) {
                    mediaPixel = 255;
                } else if (suma < 0) {
                    mediaPixel = 0;
                } else {
                    mediaPixel = suma;
                }

                //Cambiamos a formato sRGB
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;

                aux.setRGB(i, j, colorSRGB);

            }

        }
        return aux;
    }

    public BufferedImage umbralBinario(BufferedImage aux) {
        //Variables que almacenarán los píxeles
        int mediaPixel, colorSRGB;
        Color colorAux;

        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < aux.getWidth(); i++) {
            for (int j = 0; j < aux.getHeight(); j++) {
                //Almacenamos el color del píxel
                colorAux = new Color(this.imagen2.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                if (mediaPixel < sliderUmbralBinario.getValue()) {
                    mediaPixel = 0;
                } else {
                    mediaPixel = 255;
                }

                //Cambiamos a formato sRGB
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;

                aux.setRGB(i, j, colorSRGB);

            }

        }
        return aux;
    }

    public BufferedImage filtroMedianaTrunca(BufferedImage aux) {
        //Variables que almacenarán los píxeles
        int mediaPixel, colorSRGB;

        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < imagen2.getWidth(); i++) {
            for (int j = 0; j < imagen2.getHeight(); j++) {
                // mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                mediaPixel = tomarDatos(i, j);
                //Cambiamos a formato sRGB
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage
                aux.setRGB(i, j, colorSRGB);
            }
        }
        return aux;
    }

    private int tomarDatos(int x, int y) {

        Color colorAux;
        int mediaPixel = 0;

        ArrayList<Integer> mediaPixelAux = new ArrayList<Integer>();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                //Almacenamos el color del píxel
                if (esValidaPosicion(i, j)) {
                    colorAux = new Color(this.imagen2.getRGB(i, j));
                    mediaPixelAux.add((int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3));
                }
            }
        }

        Collections.sort(mediaPixelAux);

        if (medianaTrunca.isSelected()) {
            if ((mediaPixelAux.size() % 2) == 0) {
                mediaPixel = (mediaPixelAux.get(mediaPixelAux.size() / 2) + mediaPixelAux.get(mediaPixelAux.size() / 2 - 1)) / 2;
            } else {
                mediaPixel = mediaPixelAux.get(mediaPixelAux.size() / 2);
            }
        } else if (moda.isSelected()) {
            int cont = 1, cantMax = 0, moda = -1, valAnt = -1;
            for (int i = 0; i < mediaPixelAux.size() - 1; i++) {
                if (valAnt == mediaPixelAux.get(i)) {
                    cont++;
                } else {
                    if (cont >= cantMax) {
                        moda = mediaPixelAux.get(i);
                        cantMax = cont;
                        valAnt = moda;
                    }
                    cont = 1;
                }
            }
            if (cantMax == 1) {
                colorAux = new Color(this.imagen2.getRGB(x, y));
                moda = ((int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3));
            }

            mediaPixel = moda;
        }

        return mediaPixel;
    }

    private boolean esValidaPosicion(int i, int j) {
        return !(i < 0 || i >= imagen2.getWidth() || j < 0 || j >= imagen2.getHeight());
    }

    public BufferedImage escalaGrises() {
        //Variables que almacenarán los píxeles
        int mediaPixel, colorSRGB;
        Color colorAux;

        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < imagenActual.getWidth(); i++) {
            for (int j = 0; j < imagenActual.getHeight(); j++) {
                //Almacenamos el color del píxel
                colorAux = new Color(this.imagenActual.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);

                //Cambiamos a formato sRGB
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage
                imagenActual.setRGB(i, j, colorSRGB);
            }
        }
        //Retornamos la imagen
        return imagenActual;
    }

    public BufferedImage funCambioRadiante(BufferedImage aux) {
        //Variables que almacenarán los píxeles
        int mediaPixel, colorSRGB;

        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < imagen2.getWidth(); i++) {
            for (int j = 0; j < imagen2.getHeight(); j++) {
                // mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                mediaPixel = procesarRadiant(i, j);
                //Cambiamos a formato sRGB
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage
                aux.setRGB(i, j, colorSRGB);
            }
        }
        return aux;
    }

    private int procesarRadiant(int x, int y) {

        Color colorAux;
        int grisI, grisD, G;
        int mediaPixel = 0;
        
        ArrayList<Integer> mediaPixelAux = new ArrayList<Integer>();

        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                //Almacenamos el color del píxel
                if (esValidaPosicion(i-1, j) && esValidaPosicion(i+1, j) && esValidaPosicion(i, j+1) && esValidaPosicion(i, j-1)) {
                    colorAux = new Color(this.imagen2.getRGB(i, j));
                    grisI = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                    colorAux = new Color(this.imagen2.getRGB(i, j));
                    grisD = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                    
                    G = (int) Math.sqrt(Math.pow(grisI, 2)+Math.pow(grisD, 2));
                    //Double ang = (double) 
                }
            }
        }      

        return mediaPixel;
    }
    
    
    /**
     * *************************************************
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        grupoOpcion = new javax.swing.ButtonGroup();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlOpciones = new javax.swing.JPanel();
        ejecutar = new javax.swing.JButton();
        filtros = new javax.swing.JPanel();
        medianaTrunca = new javax.swing.JRadioButton();
        moda = new javax.swing.JRadioButton();
        cambioDeRadiante = new javax.swing.JRadioButton();
        pnlOperadores = new javax.swing.JPanel();
        inversionVertical = new javax.swing.JRadioButton();
        inversionHorizontal = new javax.swing.JRadioButton();
        negativo = new javax.swing.JRadioButton();
        espejo = new javax.swing.JRadioButton();
        sliderBrillo = new javax.swing.JSlider();
        sliderUmbralBinario = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Etiquetas.jpg"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Etiquetas.jpg"))); // NOI18N

        ejecutar.setText("Ejecutar");
        ejecutar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ejecutarActionPerformed(evt);
            }
        });

        filtros.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtros\n"));

        medianaTrunca.setText("Mediana Trunca");
        medianaTrunca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                medianaTruncaActionPerformed(evt);
            }
        });

        moda.setText("Moda");
        moda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modaActionPerformed(evt);
            }
        });

        cambioDeRadiante.setText("Cambio de Radiante");
        cambioDeRadiante.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambioDeRadianteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout filtrosLayout = new javax.swing.GroupLayout(filtros);
        filtros.setLayout(filtrosLayout);
        filtrosLayout.setHorizontalGroup(
            filtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filtrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(medianaTrunca)
                    .addComponent(moda)
                    .addComponent(cambioDeRadiante))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        filtrosLayout.setVerticalGroup(
            filtrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filtrosLayout.createSequentialGroup()
                .addComponent(medianaTrunca)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(moda)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cambioDeRadiante)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pnlOperadores.setBorder(javax.swing.BorderFactory.createTitledBorder("Operadores\n"));

        inversionVertical.setText("Inversion Vertical");

        inversionHorizontal.setText("Inversion Horizontal");

        negativo.setText("Negativo");
        negativo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                negativoActionPerformed(evt);
            }
        });

        espejo.setText("Espejo");
        espejo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                espejoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlOperadoresLayout = new javax.swing.GroupLayout(pnlOperadores);
        pnlOperadores.setLayout(pnlOperadoresLayout);
        pnlOperadoresLayout.setHorizontalGroup(
            pnlOperadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperadoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOperadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(negativo)
                    .addComponent(inversionVertical)
                    .addComponent(inversionHorizontal)
                    .addComponent(espejo, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlOperadoresLayout.setVerticalGroup(
            pnlOperadoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOperadoresLayout.createSequentialGroup()
                .addComponent(inversionVertical)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inversionHorizontal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 3, Short.MAX_VALUE)
                .addComponent(espejo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(negativo)
                .addContainerGap())
        );

        sliderBrillo.setMaximum(255);
        sliderBrillo.setMinimum(-255);
        sliderBrillo.setValue(0);
        sliderBrillo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderBrilloStateChanged(evt);
            }
        });

        sliderUmbralBinario.setMaximum(255);
        sliderUmbralBinario.setToolTipText("");
        sliderUmbralBinario.setValue(128);
        sliderUmbralBinario.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                sliderUmbralBinarioStateChanged(evt);
            }
        });

        javax.swing.GroupLayout pnlOpcionesLayout = new javax.swing.GroupLayout(pnlOpciones);
        pnlOpciones.setLayout(pnlOpcionesLayout);
        pnlOpcionesLayout.setHorizontalGroup(
            pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlOperadores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filtros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlOpcionesLayout.createSequentialGroup()
                        .addComponent(ejecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 657, Short.MAX_VALUE))
                    .addComponent(sliderBrillo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(sliderUmbralBinario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlOpcionesLayout.setVerticalGroup(
            pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOpcionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlOpcionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOpcionesLayout.createSequentialGroup()
                        .addComponent(sliderBrillo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(sliderUmbralBinario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ejecutar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlOperadores, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOpcionesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filtros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlOpciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(79, 79, 79)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        jSplitPane2.setLeftComponent(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1296, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(900, 900, 900))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sliderBrilloStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderBrilloStateChanged
        eventoBrillo();
    }//GEN-LAST:event_sliderBrilloStateChanged

    private void eventoBrillo() {
        // TODO add your handling code here:
        BufferedImage aux = deepCopy(imagen2);
        jLabel1.setIcon(new ImageIcon(brillo(aux)));
    }

    private void negativoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_negativoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_negativoActionPerformed

    private void medianaTruncaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_medianaTruncaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_medianaTruncaActionPerformed

    private void ejecutarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ejecutarActionPerformed
        if (inversionHorizontal.isSelected() || inversionVertical.isSelected()) {
            BufferedImage aux = new BufferedImage(imagen2.getWidth(), imagen2.getHeight(), BufferedImage.TYPE_INT_RGB);
            jLabel1.setIcon(new ImageIcon(inversion(aux)));        // TODO add your handling code here:
        } else if (negativo.isSelected()) {
            BufferedImage aux = new BufferedImage(imagen2.getWidth(), imagen2.getHeight(), BufferedImage.TYPE_INT_RGB);
            jLabel1.setIcon(new ImageIcon(negativo(aux)));        // TODO add your handling code here:
        } else if (medianaTrunca.isSelected()) {
            BufferedImage aux = deepCopy(imagenActual);
            jLabel1.setIcon(new ImageIcon(filtroMedianaTrunca(imagen2)));
        } else if (espejo.isSelected()) {
            BufferedImage aux = new BufferedImage(imagen2.getWidth() * 2, imagen2.getHeight(), BufferedImage.TYPE_INT_RGB);
            jLabel1.setIcon(new ImageIcon(inversion(aux)));
            eventoBrillo();
        } else if (moda.isSelected()) {
            BufferedImage aux = deepCopy(imagenActual);
            jLabel1.setIcon(new ImageIcon(filtroMedianaTrunca(imagen2)));
        } else if (cambioDeRadiante.isSelected()) {
            BufferedImage aux = deepCopy(imagenActual);
            jLabel1.setIcon(new ImageIcon(funCambioRadiante(imagen2)));
        }
    }//GEN-LAST:event_ejecutarActionPerformed

    private void espejoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_espejoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_espejoActionPerformed

    private void modaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modaActionPerformed

    private void sliderUmbralBinarioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_sliderUmbralBinarioStateChanged
        // TODO add your handling code here:
        BufferedImage aux = deepCopy(imagen2);
        jLabel1.setIcon(new ImageIcon(umbralBinario(aux)));     // TODO add your handling code here:
    }//GEN-LAST:event_sliderUmbralBinarioStateChanged

    private void cambioDeRadianteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cambioDeRadianteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cambioDeRadianteActionPerformed

    static BufferedImage deepCopy(BufferedImage bi) {
        ColorModel cm = bi.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = bi.copyData(null);
        return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton cambioDeRadiante;
    private javax.swing.JButton ejecutar;
    private javax.swing.JRadioButton espejo;
    private javax.swing.JPanel filtros;
    private javax.swing.ButtonGroup grupoOpcion;
    private javax.swing.JRadioButton inversionHorizontal;
    private javax.swing.JRadioButton inversionVertical;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JRadioButton medianaTrunca;
    private javax.swing.JRadioButton moda;
    private javax.swing.JRadioButton negativo;
    private javax.swing.JPanel pnlOpciones;
    private javax.swing.JPanel pnlOperadores;
    private javax.swing.JSlider sliderBrillo;
    private javax.swing.JSlider sliderUmbralBinario;
    // End of variables declaration//GEN-END:variables
}
