
package calculatrice;
import java.awt.BorderLayout;
import java.awt.Color ;
import java.awt.Dimension ;
import java.awt.Font ;
import java.awt.event.ActionEvent ;
import java.awt.event.ActionListener ; 
import javax.swing.BorderFactory ;
import javax.swing.JButton ;
import javax.swing.JFrame ;
import javax.swing.JLabel ;
import javax.swing.JPanel ;

public class Calculatrice extends JFrame {

      private JPanel container = new JPanel() ;
  //tableau stockant les éléments à afficher dans la calculatrice
  String[] tab_string = {"1" , " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", " 9 ", " 0 ", " . ", " = ", " c ", "+ ", " -" , "* ", " / "} ;
  //un bouton par élément à afficher
  JButton[] tab_button = new JButton[tab_string.length] ;
  private JLabel ecran = new JLabel() ;
  private Dimension dim = new Dimension(50, 40) ;
  private Dimension dim2 = new Dimension(50, 31) ;
  private double chiffre1 ;
  private boolean clicoperateur = false, update = false ;
  private String operateur = "";

  public Calculatrice(){
    this.setSize(240, 260);
    this.setTitle(" calculette ") ;
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE) ;
    this.setLocationRelativeTo(null) ;
    this.setResizable(false) ;
    //on initialise le conteneur avec tous les composants
      initcomposant() ;
      //on ajoute le conteneur
    this.setContentPane(container) ;
    this.setVisible(true) ;
  }

  private void initcomposant(){
    //on définit la police d’écriture à utiliser
    Font police = new Font(" arial ", Font.BOLD, 20) ;
    ecran = new JLabel("0") ;
    ecran.setFont(police) ;
    //on aligne les informations à droite dans le jlabel
    ecran.setHorizontalAlignment(JLabel.RIGHT) ;
    ecran.setPreferredSize(new Dimension(220, 20)) ;
    JPanel operateur = new JPanel() ;      
    operateur.setPreferredSize(new Dimension(55, 225)) ;
    JPanel chiffre = new JPanel() ;
    chiffre.setPreferredSize(new Dimension(165, 225)) ;
    JPanel panecran = new JPanel() ;
    panecran.setPreferredSize(new Dimension(220, 30)) ;

    //on parcourt le tableau initialisé
    //afin de créer nos boutons
    for(int i = 0 ; i < tab_string.length ; i++){
        //reccupere la valeur 
      tab_button[i] = new JButton(tab_string[i]) ;
      tab_button[i].setPreferredSize(dim) ;
      switch(i){
        //pour chaque élément situé à la fin du tableau
        //et qui n’est pas un chiffre
        //on définit le comportement à avoir grâce à un listener
        case 11 :
          tab_button[i].addActionListener(new egallistener()) ;
          chiffre.add(tab_button[i]) ;
          break ;
        case 12 :
          tab_button[i].setForeground(Color.red) ;
          tab_button[i].addActionListener(new ResetListener()) ;
          operateur.add(tab_button[i]) ;
          break ;
        case 13 :
          tab_button[i].addActionListener(new pluslistener()) ;
          tab_button[i].setPreferredSize(dim2) ;
          operateur.add(tab_button[i]) ;
          break ;
        case 14 :
          tab_button[i].addActionListener(new Moinslistener()) ;
          tab_button[i].setPreferredSize(dim2) ;
          operateur.add(tab_button[i]) ;
          break ;    
        case 15 :   
          tab_button[i].addActionListener(new MultiListener()) ;
          tab_button[i].setPreferredSize(dim2) ;
          operateur.add(tab_button[i]) ;
          break ;
        case 16 :
          tab_button[i].addActionListener(new DivListener()) ;
          tab_button[i].setPreferredSize(dim2) ;
          operateur.add(tab_button[i]) ;
          break ;
        default :
          //par défaut, ce sont les premiers éléments du tableau
          //donc des chiffres, on affecte alors le bon listener
          chiffre.add(tab_button[i]) ;
          tab_button[i].addActionListener(new chiffrelistener()) ;
          break ;
      }
    }
    panecran.add(ecran) ;
    panecran.setBorder(BorderFactory.createLineBorder(Color.black)) ;
    container.add(panecran, BorderLayout.NORTH);
    container.add(chiffre, BorderLayout.CENTER);
    container.add(operateur, BorderLayout.EAST);
  }

  //méthode permettant d’effectuer un calcul selon l’opérateur sélectionné
  private void calcul(){
    if(operateur.equals("+")){
      chiffre1 = chiffre1 + Double.valueOf(ecran.getText()).doubleValue() ;
      ecran.setText(String.valueOf(chiffre1)) ;
    }
    if(operateur.equals( "-" )){
      chiffre1 = chiffre1 - Double.valueOf(ecran.getText()).doubleValue();
      ecran.setText(String.valueOf(chiffre1)) ;
    }          
    if(operateur.equals("*")){
      chiffre1 = chiffre1 * Double.valueOf(ecran.getText()).doubleValue() ;
      ecran.setText(String.valueOf(chiffre1)) ;
    }     
    if(operateur.equals("/")){
      try{
        chiffre1 = chiffre1 / Double.valueOf(ecran.getText()).doubleValue() ;
        ecran.setText(String.valueOf(chiffre1)) ;
      } catch(ArithmeticException e) {
        ecran.setText("0") ;
      }
    }
  }
  
  //listener utilisé pour les chiffres
  //permet de stocker les chiffres et de les afficher
  class chiffrelistener implements ActionListener {
    public void actionPerformed(ActionEvent e){
      //on affiche le chiffre additionnel dans le label
      String str = ((JButton)e.getSource()).getText() ;
      if(update){
        update = false ;
      }
      else{
        if( !ecran.getText().equals("0"))
          str = ecran.getText() + str ;
      }
      ecran.setText(str) ;
    }

        
  }

  //listener affecté au bouton =
  class egallistener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      calcul() ;
      update = true ;
      clicoperateur = false ;
    }
  }

  //listener affecté au bouton +
  class pluslistener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      if(clicoperateur){
        calcul() ;
        ecran.setText(String.valueOf(chiffre1)) ;
      }
      else{
       // chiffre1 = Double.valueOf(ecran.getText()).doubleValue() ;
        clicoperateur = true ;
      }
      operateur = "+";
      update = true ;
    }

  }

  //listener affecté au bouton –
  class Moinslistener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      if(clicoperateur){
        calcul() ;
        ecran.setText(String.valueOf(chiffre1)) ;
      }
      else{
       // chiffre1 = Double.valueOf(ecran.getText()).doubleValue();
        clicoperateur = true ;
      }
      operateur = "-" ;
      update = true ;
    }
        private void doublevalue() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
  }

  //Listener affecté au bouton *
  class MultiListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      if(clicoperateur){
        calcul();
        ecran.setText(String.valueOf(chiffre1)) ;
      }
      else{
      //  chiffre1 = Double.valueOf(ecran.getText()).doubleValue() ;
        clicoperateur = true ;
      }
      operateur = "*";
      update = true ;
    }
  }

  //Listener affecté au bouton /
  class DivListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      if(clicoperateur){
        calcul();
        ecran.setText(String.valueOf(chiffre1)) ;
      }
      else{
       // chiffre1 = Double.valueOf(ecran.getText()).doubleValue() ;
        clicoperateur = true ;
      }
      operateur = " / " ;
      update = true ;
    }
  }

  //Listener affecté au bouton de remise à zéro
  class ResetListener implements ActionListener {
    public void actionPerformed(ActionEvent arg0){
      clicoperateur = false ;
      update = true ;
      chiffre1 = 0 ;
      operateur = "";
      ecran.setText("") ;
    }
  }      

    public static void main(String[] args) {
       Calculatrice calculette = new Calculatrice() ;
    }
    
}
