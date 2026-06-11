/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package UltraP;

import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author PEPEPECAS
 */
public class BatallaUI extends javax.swing.JFrame {

    private String nombreEntrenador;
    private ArrayList<Pokemon> equipo;
    private Pokemon pokemonAliado;
    private Pokemon pokemonRival;
    private boolean terminada;
    private int pokemonActualIndex;

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(BatallaUI.class.getName());

    /**
     * Creates new form BatallaUI
     */
    public BatallaUI() {
        initComponents();
    }

    BatallaUI(String nombre, String poke1, String poke2, String poke3) {
        initComponents();
        this.nombreEntrenador = nombre;
        equipo = new ArrayList<>();
        equipo.add(crearPokemon(poke1));
        equipo.add(crearPokemon(poke2));
        equipo.add(crearPokemon(poke3));
        pokemonActualIndex = 0;
        pokemonAliado = equipo.get(0);
        pokemonRival = crearRivalAleatorio();
        terminada = false;
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(
                new String[]{"Pocion (+30 HP)", "Superpocion (+60 HP)", "Pocion Total (llena HP)"}
        ));
        NombreUsuario.setText(nombreEntrenador);
        conectarBotones();
        actualizarUI();
    }

    private void conectarBotones() {
        LuchaBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lucharActionPerformed();
            }
        });
        CambioBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cambiarPokemonActionPerformed();
            }
        });
        HuirBoton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                System.exit(0);
            }
        });
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usarItemActionPerformed();
            }
        });
    }

    private void lucharActionPerformed() {
        if (terminada) {
            CuadroDeMensajes.setText("|| La batalla ha terminado. Cierra la ventana. ||");
            return;
        }
        if (!pokemonAliado.estaVivo()) {
            CuadroDeMensajes.setText("|| " + pokemonAliado.getNombre() + " no puede luchar. Cambia de Pokemon. ||");
            return;
        }
        double danioBase = (pokemonAliado.getAtaque() * 2.0 / pokemonRival.getDefensa()) * 20;
        double variacion = Math.random() * (1.15 - 0.85) + 0.85;
        int danioJugador = (int) (danioBase * variacion);
        pokemonRival.setHp(Math.max(0, pokemonRival.getHp() - danioJugador));
        String mensaje = (pokemonAliado.getNombre() + " ataca! -> " + danioJugador + " de dano.");
        if (!pokemonRival.estaVivo()) {
            CuadroDeMensajes.setText("|| " + pokemonRival.getNombre() + " se debilito! Tu ganas. ||");
            terminada = true;
            actualizarUI();
            return;
        }
        danioBase = (pokemonRival.getAtaque() * 2.0 / pokemonAliado.getDefensa()) * 20;
        variacion = Math.random() * (1.15 - 0.85) + 0.85;
        int danioRival = (int) (danioBase * variacion);
        pokemonAliado.setHp(Math.max(0, pokemonAliado.getHp() - danioRival));
        mensaje += " || " + pokemonRival.getNombre() + " contraataca -> " + danioRival + " de dano.";
        if (!pokemonAliado.estaVivo()) {
            mensaje += " || " + pokemonAliado.getNombre() + " se debilito!";
            if (todosDebilitados()) {
                CuadroDeMensajes.setText("|| Todos tus Pokemon estan debilitados! Tu pierdes. ||");
                terminada = true;
                actualizarUI();
                return;
            }
            mensaje += " Cambia de Pokemon. ||";
        }
        CuadroDeMensajes.setText(mensaje);
        actualizarUI();
    }

    private void cambiarPokemonActionPerformed() {
        if (terminada) {
            CuadroDeMensajes.setText("|| La batalla ha terminado. ||");
            return;
        }
        int intentos = 0;
        do {
            pokemonActualIndex = (pokemonActualIndex + 1) % equipo.size();
            pokemonAliado = equipo.get(pokemonActualIndex);
            intentos++;
            if (intentos > equipo.size()) {
                break;
            }
        } while (!pokemonAliado.estaVivo());
        if (!pokemonAliado.estaVivo()) {
            CuadroDeMensajes.setText("|| Todos tus Pokemon estan debilitados! Tu pierdes. ||");
            terminada = true;
            return;
        }
        CuadroDeMensajes.setText("|| Cambiaste a " + pokemonAliado.getNombre() + "! ||");
        actualizarUI();
    }

    private void usarItemActionPerformed() {
        if (terminada) {
            CuadroDeMensajes.setText("|| La batalla ha terminado. ||");
            return;
        }
        if (!pokemonAliado.estaVivo()) {
            CuadroDeMensajes.setText("|| " + pokemonAliado.getNombre() + " esta debilitado. Cambialo primero. ||");
            return;
        }
        if (pokemonAliado.getHp() >= pokemonAliado.getHpMax()) {
            CuadroDeMensajes.setText("|| " + pokemonAliado.getNombre() + " ya tiene la vida al maximo. ||");
            return;
        }
        String itemSeleccionado = (String) jComboBox1.getSelectedItem();
        if (itemSeleccionado == null) {
            return;
        }
        int hpAnterior = pokemonAliado.getHp();
        if (itemSeleccionado.equals("Pocion (+30 HP)")) {
            pokemonAliado.setHp(Math.min(pokemonAliado.getHp() + 30, pokemonAliado.getHpMax()));
        } else if (itemSeleccionado.equals("Superpocion (+60 HP)")) {
            pokemonAliado.setHp(Math.min(pokemonAliado.getHp() + 60, pokemonAliado.getHpMax()));
        } else if (itemSeleccionado.equals("Pocion Total (llena HP)")) {
            pokemonAliado.setHp(pokemonAliado.getHpMax());
        }
        int curado = pokemonAliado.getHp() - hpAnterior;
        CuadroDeMensajes.setText("|| Usaste " + itemSeleccionado + " en "
                + pokemonAliado.getNombre() + " -> +" + curado + " HP. ||");
        actualizarUI();
    }

    private boolean todosDebilitados() {
        for (Pokemon p : equipo) {
            if (p.estaVivo()) {
                return false;
            }
        }
        return true;
    }

    private Pokemon crearPokemon(String nombre) {
        if (nombre.equals("Charizard")) {
            return new Pokemon("Charizard", TipoPokemon.FUEGO, 5);
        } else if (nombre.equals("Blaziken")) {
            return new Pokemon("Blaziken", TipoPokemon.TLUCHA, 5);
        } else if (nombre.equals("Piplup")) {
            return new Pokemon("Piplup", TipoPokemon.AGUA, 5);
        } else if (nombre.equals("Snorlax")) {
            return new Pokemon("Snorlax", TipoPokemon.NORMAL, 5);
        } else if (nombre.equals("Vaporeon")) {
            return new Pokemon("Vaporeon", TipoPokemon.AGUA, 5);
        } else if (nombre.equals("Umbreon")) {
            return new Pokemon("Umbreon", TipoPokemon.SINIESTRO, 5);
        } else if (nombre.equals("Skarmory")) {
            return new Pokemon("Skarmory", TipoPokemon.VOLADOR, 5);
        } else if (nombre.equals("Raikou")) {
            return new Pokemon("Raikou", TipoPokemon.ELECTRICO, 5);
        } else if (nombre.equals("Gardevoir")) {
            return new Pokemon("Gardevoir", TipoPokemon.PSIQUICO, 5);
        } else if (nombre.equals("Cacturne")) {
            return new Pokemon("Cacturne", TipoPokemon.PLANTA, 5);
        } else {
            return new Pokemon(nombre, TipoPokemon.NORMAL, 5);
        }
    }

    private Pokemon crearRivalAleatorio() {
        String[] nombres = {"Charizard", "Blaziken", "Piplup", "Snorlax", "Raikou", "Gardevoir", "Cacturne"};
        int indice = (int) (Math.random() * nombres.length);
        return crearPokemon(nombres[indice]);
    }

    private void setImagenPokemon(javax.swing.JLabel label, String nombre, boolean back) {
        String ruta = "/proyectopokemon/Imagenes/" + nombre.toLowerCase() + (back ? "Back" : "") + ".png";
        label.setIcon(new javax.swing.ImageIcon(getClass().getResource(ruta)));
    }

    private void actualizarUI() {
        NombrePokemonElegido.setText(pokemonAliado.getNombre() + " (" + nombreEntrenador + ")");
        BarraVidaPokemonAliado.setMaximum(pokemonAliado.getHpMax());
        BarraVidaPokemonAliado.setValue(Math.max(0, pokemonAliado.getHp()));
        BarraVidaPokemonAliado.setStringPainted(true);
        BarraVidaPokemonAliado.setString(pokemonAliado.getHp() + "/" + pokemonAliado.getHpMax());
        setImagenPokemon(jLabel2, pokemonAliado.getNombre(), false);
        NombrePokemonRival.setText(pokemonRival.getNombre());
        BarraVidaPokemonRival.setMaximum(pokemonRival.getHpMax());
        BarraVidaPokemonRival.setValue(Math.max(0, pokemonRival.getHp()));
        BarraVidaPokemonRival.setStringPainted(true);
        BarraVidaPokemonRival.setString(pokemonRival.getHp() + "/" + pokemonRival.getHpMax());
        setImagenPokemon(jLabel1, pokemonRival.getNombre(), true);
        if (CuadroDeMensajes.getText().isEmpty()) {
            CuadroDeMensajes.setText("|| Comienza la batalla! "
                    + pokemonAliado.getNombre() + " vs " + pokemonRival.getNombre() + " ||");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        NombreUsuario = new javax.swing.JTextField();
        CambioBoton = new javax.swing.JButton();
        HuirBoton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BarraVidaPokemonRival = new javax.swing.JProgressBar();
        BarraVidaPokemonAliado = new javax.swing.JProgressBar();
        NombrePokemonRival = new javax.swing.JTextField();
        NombrePokemonElegido = new javax.swing.JTextField();
        PersonajeIMG = new javax.swing.JLabel();
        CuadroDeMensajes = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        LuchaBoton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        CambioBoton.setText("CAMBIO");

        HuirBoton.setText("HUIR");

        CuadroDeMensajes.setFont(new java.awt.Font("Noto Sans", 0, 24)); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pastel del Heroe", "Tarta de Caracol", "Hot Dog", "Bola de Nieve" }));

        LuchaBoton.setText("LUCHAR");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(199, 199, 199)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(33, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NombrePokemonElegido, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BarraVidaPokemonAliado, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(CuadroDeMensajes, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(LuchaBoton)
                            .addComponent(CambioBoton))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(HuirBoton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(37, 37, 37))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(NombrePokemonRival, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(BarraVidaPokemonRival, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(119, 119, 119)
                                        .addComponent(NombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(66, 66, 66))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(70, 70, 70)
                                .addComponent(PersonajeIMG)
                                .addGap(46, 46, 46))))))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(413, 413, 413)
                    .addComponent(jLabel3)
                    .addContainerGap(487, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 192, Short.MAX_VALUE)
                .addComponent(NombrePokemonElegido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BarraVidaPokemonAliado, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(CuadroDeMensajes, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(NombrePokemonRival, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BarraVidaPokemonRival, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PersonajeIMG)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NombreUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LuchaBoton)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CambioBoton)
                    .addComponent(HuirBoton))
                .addGap(37, 37, 37))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(214, 214, 214)
                    .addComponent(jLabel3)
                    .addContainerGap(286, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(BatallaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(BatallaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(BatallaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(BatallaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new BatallaUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar BarraVidaPokemonAliado;
    private javax.swing.JProgressBar BarraVidaPokemonRival;
    private javax.swing.JButton CambioBoton;
    private javax.swing.JTextField CuadroDeMensajes;
    private javax.swing.JButton HuirBoton;
    private javax.swing.JButton LuchaBoton;
    private javax.swing.JTextField NombrePokemonElegido;
    private javax.swing.JTextField NombrePokemonRival;
    private javax.swing.JTextField NombreUsuario;
    private javax.swing.JLabel PersonajeIMG;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
