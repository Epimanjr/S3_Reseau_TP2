/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TCP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blaise
 */
public class ServeurTCP {
    int port;
    String file;
    
    public ServeurTCP(String port, String file) {
        this.port = new Integer(port);
        this.file = file;
        
        this.ecoute();
    }
    
    public void ecoute() {
        try {
            ServerSocket ss = new ServerSocket(port);
            Socket s = ss.accept();
            
            InputStream is = s.getInputStream();
            
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[100000];
            int l;
            
            do {
                l = (is.read(buffer));
                if (l > 0) {
                    out.write(buffer, 0, l);
                }
            } while (l > 0);
            byte[] data = out.toByteArray();
          
            
            FileOutputStream fos = new FileOutputStream(new File(file));
            fos.write(data);
            System.out.println("Fichier "+file+" généré avec succès.");
            
        } catch (IOException ex) {
            Logger.getLogger(ServeurTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void main(String[] args) {
        System.out.println("Serveur TCP : ");
        if(args.length<2) {
            System.out.println("Erreur : Nombre d'arguments incorrect");
        }
        else {
            new ServeurTCP(args[0], args[1]);
        }
    }
}
