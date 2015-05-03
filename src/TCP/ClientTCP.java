/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TCP;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blaise
 */
public class ClientTCP {

    String ip;
    int port;
    String file;

    public ClientTCP(String ip, String port, String file) {
        this.ip = ip;
        this.port = new Integer(port);
        this.file = file;

        if (this.send()) {
            System.out.println("Done.");
        }
    }

    public boolean send() {
        System.out.println("Envoi du fichier à @" + ip + " port : " + port + " ...");
        try {
            Socket s = new Socket(ip, port);
            FileInputStream fis = new FileInputStream(new File(file));

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
            
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ClientTCP.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("Client TCP : ");
        if (args.length < 3) {
            System.out.println("Erreur : Nombre d'arguments incorrect");
        } else {
            new ClientTCP(args[0], args[1], args[2]);
        }
    }
}
