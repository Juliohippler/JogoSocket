/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo.cliente;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

/**
 *
 * @author julio
 */

// AQUI É O SERVIDOR, ACABEI DEIXANDO COMO CONTAINER MAS PODE SER SÓ SERVER MESMO
public class ContainerDeJanelasServer extends JFrame{
    
     
    List<PrintWriter> escritores = new ArrayList<>();
    
    public ContainerDeJanelasServer() throws IOException {
        
        //CHATSERVER
              
        ServerSocket server;
        Scanner scan;
        while(true){
            try{   
                server = new ServerSocket(5000);
                while(true){
                    Socket socket = server.accept();
                    new Thread(new EscutaCliente(socket)).start();
                    PrintWriter p = new PrintWriter(socket.getOutputStream());
                    escritores.add(p);
                    //scan = new Scanner(s.getInputStream());
                    //System.out.println(scan.nextLine());
                    
                }
              }catch (IOException e){}
        }      
   }
    
    private void encaminharParaTodos(String texto){
        for (PrintWriter w : escritores) {
            try{
                w.println(texto);
                w.flush();
            }catch(Exception e ){}
                        
        }
    }
    
    private class EscutaCliente implements Runnable{
        
        Scanner leitor;
        public EscutaCliente(Socket socket){
            try{
                leitor = new Scanner(socket.getInputStream());
            }catch (Exception e){}
        }

        @Override
        public void run() {
            try{          
            
                String texto;
                while ((texto = leitor.nextLine()) != null) {
                    System.out.println(texto);
                    encaminharParaTodos(texto);
            }
        }catch(Exception x){}
        
    }
    }
    
    public static void main(String[] args) throws IOException{
        new ContainerDeJanelasServer();
        
    }
   

}
