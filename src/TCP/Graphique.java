/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author blaise
 */
public class Graphique extends JFrame {

    JPanel panPrincipal = new JPanel();

    JPanel panHaut = new JPanel();
    JTextField jip = new JTextField(15);
    JTextField jport = new JTextField(15);
    JLabel label = new JLabel("En attente ...");
    JPanel panCenter = new JPanel();
    JButton choisirFichier = new JButton("Choisir");

    public Graphique(String[] args) {
        if (args.length > 0) {
            jip.setText(args[0]);
        }
        if (args.length > 1) {
            jport.setText(args[1]);
        }

        panPrincipal.setLayout(new BorderLayout());
        panHaut.setLayout(new GridLayout(2,2));
        panHaut.add(new JLabel("Adresse ip : "));
        panHaut.add(jip);
        panHaut.add(new JLabel("Port : "));
        panHaut.add(jport);
        panPrincipal.add(panHaut, BorderLayout.NORTH);

        panCenter.add(new JLabel("Choisir un fichier : "));
        choisirFichier.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser jfc = new JFileChooser();
                String monFichier = "";
                String approve = "Envoyer par TCP";
                int resultatEnregistrer = jfc.showDialog(jfc, approve);
                if (resultatEnregistrer == JFileChooser.APPROVE_OPTION) {
                    monFichier = jfc.getSelectedFile().toString();
                    
                }
                try {
                    try (FileInputStream fis = new FileInputStream(new File(monFichier))) {
                        Socket s = new Socket(jip.getText(), new Integer(jport.getText()));

                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        byte[] buffer = new byte[1024];
                        int l;

                        do {
                            l = (fis.read(buffer));
                            if (l > 0) {
                                out.write(buffer, 0, l);
                            }
                        } while (l > 0);
                        byte[] data = out.toByteArray();

                        /*System.out.println(data.length);
                         System.out.println(new String(data));*/
                        OutputStream os = s.getOutputStream();
                        os.write(data);
                        
                        label.setText("Envoyé !!");
                        System.exit(1);
                    }
                } catch (IOException ex) {

                }
            }
        });
        panCenter.add(choisirFichier);

        panPrincipal.add(panCenter, BorderLayout.CENTER);
        panPrincipal.add(label, BorderLayout.SOUTH);
        this.setContentPane(panPrincipal);
        this.setLocation(300, 300);
        this.setResizable(false);
        this.setTitle("Client TCP");
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new Graphique(args);
    }

}
