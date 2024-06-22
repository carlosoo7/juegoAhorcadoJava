package com.mycompany.juego_ahorcado;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
/**
@author Carlos Fernando Calderon
 */


//Inicio Codigo
public class Juego_Ahorcado {
    //Se llama al Marco Principal en el main para su ejecucion
    public static void main(String[] args) {     
        MarcoAhorcado Juego = new MarcoAhorcado();
        Juego.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
   
}
 //Creacion y configuracion de marco segun tamaño de la pantalla
class MarcoAhorcado extends JFrame {
    public MarcoAhorcado(){     
        Toolkit Tool=Toolkit.getDefaultToolkit();
        Dimension Tamaño=Tool.getScreenSize();             
        setSize(Tamaño.width/2, Tamaño.height/2);
        setLocationRelativeTo(null);
        FrameMenu Frame_Menu = new FrameMenu(this);
        setTitle("Ahorcado");
        Image IMG = Tool.getImage(getClass().getResource("/Ahorcado 9.jpg"));
        setIconImage(IMG);
        add(Frame_Menu);
        setVisible(true);        
    }
    //Metodo para cambiar el Panel o Lamina del marco que recibe un Jpanel a la vez Resetea el juego cada vez
 public void Cambiar(JPanel p){
     getContentPane().removeAll();
     getContentPane().add(p);
     revalidate();
     repaint();
 }

    
}
//Clase JPanel para el menu unicial
 class FrameMenu extends JPanel implements ActionListener {
   //Declaracion variables y elementos necesarios
    private final JButton inicio = new JButton();
    private final JButton salir = new JButton();
    private final JLabel label = new JLabel();
    private final JPanel PanelMenu=new JPanel();
    private final MarcoAhorcado Frame;
//Constructor de la clase
    public FrameMenu(MarcoAhorcado Frame) {
        setBackground(Color.WHITE);//Color de fondo Blanco
        this.Frame=Frame;
        //Poner texto Botones y agregar Listeners
        inicio.setText("Iniciar Juego");
        salir.setText("Salir Del Juego");
        label.setText("Bienvenido al ahorcado!");
        inicio.addActionListener(this);
        salir.addActionListener(this);       
        //crear Panel        
        setLayout(new BorderLayout());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 30));
        add(label, BorderLayout.NORTH);
        
        //Agregar imagen al centro de ahorcado
        Toolkit Tool = Toolkit.getDefaultToolkit();
        JLabel ImgMenu = new JLabel(new ImageIcon(Tool.getImage(getClass().getResource("/Ahorcado 9.jpg"))));        
        add(ImgMenu, BorderLayout.CENTER);
        //Agregar los botones
        PanelMenu.setBackground(Color.WHITE);
        PanelMenu.add(inicio);
        PanelMenu.add(salir);
        add(PanelMenu, BorderLayout.SOUTH);
        
    }
    //Manejo de eventos Salir del programa y Cambiar el frame en el marco por el metodo Cambiar()
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==inicio){
        try {Frame.Cambiar(new FrameJuego(Frame));} catch (IOException ex){JOptionPane.showMessageDialog
        (null, "No se escuentra el archivo Palabras_Ahorcado.txt", "Error", JOptionPane.ERROR_MESSAGE);System.exit(0);}
        }
        if(e.getSource()==salir){
          System.exit(0);
        }
  }
 }
//Clase del Panel Del juego
class FrameJuego extends JPanel implements ActionListener,KeyListener{ 
    //Declaracion Variables Necesarias Con alagunas configuraciones
    private final MarcoAhorcado Frame2;
    private int Intentos=10;
    private int IntentosR=0;
    private JLabel Respuestal = new JLabel("Escribe una letra aqui Abajo!! o si ya sabes la Palabra escribela completa!!");
    private JButton Provar=new JButton("Provar");private JButton Salir=new JButton("Volver Al Menu"); //Botones
    private JTextField Respuesta =new JTextField(18);//Respuestas del usuario en letras
    private JTextField[] sout = new JTextField[18]; // Texfields para mostrar letras que se rellenan
    private char[] letras = new char[20];//Almacen de letras de la palabra
    private Boolean[] Comprovacion= new Boolean[20];//Boolean de comprovacion de letras
    private JPanel[] Paneles=new JPanel[7];// Array de Paneles para uso en layouts y elementos
    private JLabel Tletras=new JLabel();//total de letras de la palabra
    private JLabel ImgAhorcado;// Jlable que contiene la imagen cambiante
    private JLabel GoP=new JLabel(""); //JLabel de ganaste o perdiste
    private JPanel ImgAhorcadoP=new JPanel();//JPanel que contendra la imagen cambiante
    
    
    //Declaraciones necesarias para llenar el array con un archivo txt de palabras    
    ArrayList<String> Palabras=new ArrayList();
    String Linea;
    BufferedReader lea;
    FileReader archivo;
    Random azar=new Random();
    String Palabra;
    
    //Almacen de iconos usados en metodos posteriores
    ImageIcon[] IMG =new ImageIcon[11];
    //Toolkit para obtener imagenes
    Toolkit Tool = Toolkit.getDefaultToolkit();
    
    //Constructor para el panel
    public FrameJuego(MarcoAhorcado Frame) throws IOException{        
     Frame2=Frame;
     setBackground(Color.WHITE); //Color de fondo Blanco
     //Insertando Palabras del archivo al arraylist palabras 
     InputStream inputStream = getClass().getResourceAsStream("/Palabras_Ahorcado.txt");
     if (inputStream == null) {
    throw new FileNotFoundException("No se encuentra el archivo Palabras_Ahorcado.txt");}
     lea = new BufferedReader(new InputStreamReader(inputStream));   
     while((Linea=lea.readLine())!=null){
     Palabras.add(Linea.trim().toLowerCase());
     }
     lea.close();
     Palabra=Palabras.get(azar.nextInt(486));
     // Inicializar booleans en true para comprovacion
     for(int a=0;a<Palabra.length();a++){
         Comprovacion[a]=true;
     }
   
       // System.out.println(Palabra); //un sistem out print para que en consola veamos que palabra se genero en caso de necesitar.
       
       //Añadir Listeners
        Provar.addActionListener(this);
        Salir.addActionListener(this);
        Respuesta.addKeyListener(this);
        
        
        setLayout(new BorderLayout());
        //Paneles Botones y TextFields Letras
        Paneles[0]=new JPanel(new GridLayout(1, 2));
        Paneles[0].setBackground(Color.WHITE);
        Paneles[0].add(Provar);Paneles[0].add(Salir);

        
        Paneles[1]=new JPanel();
        Paneles[1].setBackground(Color.WHITE);
        //For que agrega TextFields para cada letra de la palabra
        for(int a=0;a<Palabra.length();a++){        
         Paneles[1].add(sout[a]=new JTextField(4));
         sout[a].setHorizontalAlignment(JTextField.CENTER);
         sout[a].setEditable(false);
         letras[a]=Palabra.charAt(a);
        }
        
        //JPanel de informacion de Palabra y Letras
        Paneles[2]=new JPanel();
        Paneles[2].setBackground(Color.WHITE);
        Tletras.setText("La Palabra tiene " + Palabra.length()+ " letras y tienes " + Intentos + " Intentos." );
        Tletras.setHorizontalAlignment(SwingConstants.CENTER);
        Tletras.setVerticalAlignment(SwingConstants.CENTER);
        Paneles[2].add(Tletras);      
        
        Paneles[3]=new JPanel();
        Paneles[3].setBackground(Color.WHITE);
        Paneles[3].add(Respuesta);
        
        Paneles[4]=new JPanel();
        Paneles[4].setBackground(Color.WHITE);
        Respuesta.setHorizontalAlignment(JTextField.CENTER);
        Paneles[4].add(Respuestal);
        //Fin Botones y Letras
        
        //Se añaden los anteriores paneles a un JPanel con gridlayout y se agregan al panel principal en la zona Sur        
        Paneles[5]=new JPanel(new GridLayout(5,1));
        Paneles[5].setBackground(Color.WHITE);
        Paneles[5].add(Paneles[4]);
        Paneles[5].add(Paneles[3]);
        Paneles[5].add(Paneles[2]);
        Paneles[5].add(Paneles[1]);
        Paneles[5].add(Paneles[0]);
        add(Paneles[5],BorderLayout.SOUTH);
        
        GoP.setFont(new Font("Arial", Font.PLAIN, 30));
        add(GoP, BorderLayout.NORTH);
        //Almacenar Imagenes
        for (int t = 0; t < 11; t++) {
            IMG[t] = new ImageIcon(Tool.getImage(getClass().getResource("/Ahorcado " + t + ".jpg")));
        }

        //Imagen Inicial
        ImgAhorcado = new JLabel(IMG[0]);
        ImgAhorcadoP.add(ImgAhorcado);
        add(ImgAhorcado, BorderLayout.CENTER); 
    }
    //metodo que cambia Imagen
    
    public void CambiaImagen(int inten){
       ImgAhorcado.setIcon(IMG[inten]);     
    }
    public void AccionProvar(){
        char o='a'; // se inicializa con cualquier valor, no es importante el valor
        //Evento boton Provar
        
         
         try{
         boolean letraCorrecta = false; //Boolean para comprovar si se quita o no una vida
         o=Respuesta.getText().trim().toLowerCase().charAt(0);
           for(int u=0;u<Palabra.length();u++){
            if(Respuesta.getText().trim().length()<2){
             if(letras[u]==o){
                 letraCorrecta=true;
                 if(Comprovacion[u]){
                     sout[u].setText(""+o);
                     Comprovacion[u]=false;                         
                     //Gracias al array boolean de comprovacion evitamos el uso doble de la misma letra y da un aviso 
                 }else{JOptionPane.showMessageDialog(null, "Ya usaste esa letra, Tranquilo no se te quita intentos", "Error", JOptionPane.WARNING_MESSAGE);break;}
               }
                 }else{//Aqui se comprueba si una palabra entera introducida es correcta o incorrecta                                  
                  if(Palabra.equalsIgnoreCase(Respuesta.getText())){
                   JOptionPane.showMessageDialog(null, "Felicidades!! Has ganado el juego, Vuelve al menu principal para jugar de nuevo!", "Ganaste!!", JOptionPane.INFORMATION_MESSAGE);
                   Respuesta.setEditable(false);                      
                   GoP.setText("Ganaste!!");
                   Provar.setVisible(false);
                   CambiaImagen(9);
                   Respuestal.setText("Bien Hecho!!");
                   break;
                  }else{
                   JOptionPane.showMessageDialog(null, "La Palabra Introducida es Incorrecta!", "Incorrecto!!", JOptionPane.INFORMATION_MESSAGE); 
                   }break;
                   }
             }
           //aqui se cuentan o no los intentos
         if(!letraCorrecta){
            Intentos--;
            IntentosR++;
            CambiaImagen(IntentosR);
         }
            //actualizacion de intentos y vaciado del label de respuesta
            Tletras.setText("La Palabra tiene " + Palabra.length()+ " letras y tienes " + Intentos + " Intentos." );
            Respuesta.setText("");
         }catch(StringIndexOutOfBoundsException b){JOptionPane.showMessageDialog(null, "No has puesto nada en el recuadro", "Error", JOptionPane.WARNING_MESSAGE);}
         //If cuando se acaban los intentos se termina el juego e informa que palabra era la correcta
         if(Intentos==0){
            JOptionPane.showMessageDialog(null, "Perdiste! la palabra era " + Palabra +" pero puedes volderlo a intentar volviendo al menu!!", "Perdiste", JOptionPane.INFORMATION_MESSAGE);
            Respuesta.setEditable(false);            
            Provar.setVisible(false);
            GoP.setText("Perdiste!!");
            Respuestal.setText("Lo Lamento!!");
         }
         
         //for e if para comprobar si el texto en los labels esta completo para generar un ganaste! 
         String PalabraCompleta="";
         try{
             for(int g=0;g<Palabra.length();g++){
                 PalabraCompleta+=sout[g].getText();                
                 if(Palabra.equalsIgnoreCase(PalabraCompleta)){
                     JOptionPane.showMessageDialog(null, "Felicidades!! Has ganado el juego, Vuelve al menu principal para jugar de nuevo!", "Ganaste!!", JOptionPane.INFORMATION_MESSAGE);
                      Respuesta.setEditable(false);                      
                      GoP.setText("Ganaste!!");
                      Respuestal.setText("Bien Hecho!!");
                      Provar.setVisible(false);
                      CambiaImagen(9);
                      
                 }
             }
         
         }catch(StringIndexOutOfBoundsException c){}
         //Rellenar labels con la palabra
         if(GoP.getText().equalsIgnoreCase("Ganaste!!")){
              CambiaImagen(9);
              for(int h=0;h<Palabra.length();h++){
                 sout[h].setText(""+Palabra.charAt(h));
              }
         }
          
    }
    public void actionPerformed(ActionEvent s) {
        //Evento boton Salir
        if(s.getSource()==Salir){
            Frame2.Cambiar(new FrameMenu(Frame2));
        }
        if (s.getSource()==Provar){
           AccionProvar();
        }
        }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
       if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            AccionProvar();
        }
    }
    }

//Fin Codigo
