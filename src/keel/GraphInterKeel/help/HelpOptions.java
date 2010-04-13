package keel.GraphInterKeel.help;

import keel.GraphInterKeel.help.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.Vector;


public class HelpOptions extends JPanel {
  BorderLayout borderLayout1 = new BorderLayout();
  JTabbedPane jTabbedPane1 = new JTabbedPane();
  JPanel contenido = new JPanel();
  JPanel indice = new JPanel();
  BorderLayout borderLayout2 = new BorderLayout();
  JScrollPane jScrollPane1 = new JScrollPane();
  JList lista = new JList();
  JPanel jPanel1 = new JPanel();
  BorderLayout borderLayout3 = new BorderLayout();
  JLabel jLabel1 = new JLabel();
  JTextField texto = new JTextField();
  JScrollPane jScrollPane2;
  BorderLayout borderLayout4 = new BorderLayout();
  JTree arbol;
  DefaultMutableTreeNode top;
  HelpFrame padre;
  JButton busca = new JButton();

  public HelpOptions(HelpFrame v) {
    try {
      padre = v;
      jbInit();
    }
    catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  void jbInit() throws Exception {
    this.setLayout(borderLayout1);
    indice.setLayout(borderLayout2);
    jPanel1.setLayout(borderLayout3);
    jLabel1.setFont(new java.awt.Font("Arial", 0, 11));
    jLabel1.setText("Search for:");
    texto.setFont(new java.awt.Font("Arial", 0, 11));
    texto.setText("");
    texto.addActionListener(new OpcionesAyuda_texto_actionAdapter(this));
    contenido.setLayout(borderLayout4);
    borderLayout2.setHgap(0);
    borderLayout2.setVgap(10);
    borderLayout3.setHgap(3);
    borderLayout3.setVgap(10);
    top = new DefaultMutableTreeNode("Help");
    arbol = new JTree(top);
    arbol.addTreeSelectionListener(new OpcionesAyuda_arbol_treeSelectionAdapter(this));
    busca.setText("");
    busca.setFont(new java.awt.Font("Arial", 0, 11));
    busca.setMaximumSize(new Dimension(24, 24));
    busca.setMinimumSize(new Dimension(24, 24));
    busca.setPreferredSize(new Dimension(24, 24));
    busca.setIcon(new ImageIcon(this.getClass().getResource("/keel/GraphInterKeel/resources/ico/help/busca.gif")));
    busca.addActionListener(new OpcionesAyuda_busca_actionAdapter(this));
    lista.addListSelectionListener(new OpcionesAyuda_lista_listSelectionAdapter(this));
    lista.setFont(new java.awt.Font("Arial", 0, 11));
    lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    arbol.setFont(new java.awt.Font("Arial", 0, 11));
    jTabbedPane1.setFont(new java.awt.Font("Arial", 0, 11));
    this.setFont(new java.awt.Font("Arial", 0, 11));
    contenido.setFont(new java.awt.Font("Arial", 0, 11));

    indice.setFont(new java.awt.Font("Arial", 0, 11));
    jScrollPane1.setFont(new java.awt.Font("Arial", 0, 11));
    jPanel1.setFont(new java.awt.Font("Arial", 0, 11));
    this.add(jTabbedPane1, BorderLayout.CENTER);
    jTabbedPane1.add(contenido,  "Content");
    jScrollPane2 = new JScrollPane(arbol);
    jScrollPane2.setFont(new java.awt.Font("Arial", 0, 11));
    contenido.add(jScrollPane2,  BorderLayout.CENTER);
    jScrollPane2.getViewport().add(arbol, null);
    jTabbedPane1.add(indice, "Index");
    indice.add(jScrollPane1, BorderLayout.CENTER);
    jScrollPane1.getViewport().add(lista, null);
    indice.add(jPanel1, BorderLayout.NORTH);
    jPanel1.add(jLabel1,  BorderLayout.NORTH);
    jPanel1.add(texto, BorderLayout.CENTER);
    jPanel1.add(busca,  BorderLayout.EAST);

    // Tree options
    DefaultTreeCellRenderer renderer1 = new DefaultTreeCellRenderer();
    renderer1.setClosedIcon(new ImageIcon(this.getClass().getResource("/keel/GraphInterKeel/resources/ico/help/libro1.gif")));
    renderer1.setOpenIcon(new ImageIcon(this.getClass().getResource("/keel/GraphInterKeel/resources/ico/help/libro2.gif")));
    renderer1.setLeafIcon(new ImageIcon(this.getClass().getResource("/keel/GraphInterKeel/resources/ico/help/fich_ayuda.gif")));
    arbol.setCellRenderer(renderer1);    
    arbol.expandRow(0);
    
  }

  void arbol_valueChanged(TreeSelectionEvent e) {
    // Show help file associated to the item
    DefaultMutableTreeNode nodo = (DefaultMutableTreeNode)arbol.getLastSelectedPathComponent();
    if (nodo != null){
      Object o = nodo.getUserObject();
      if (nodo.isLeaf()) {
        HelpSheet h = (HelpSheet)o;
        padre.contenido.muestraURL(h.direccion);
        // Clear selection
        lista.clearSelection();
      }
    }
  }

  void busca_actionPerformed(ActionEvent e) {
    // Show help themes
    if(texto.getText().length() == 0){
      JOptionPane.showMessageDialog(padre,"There are nothing to search","Info", 1);
    }
    else{
      Vector v = new Vector();
      for (int i = 0; i < padre.temas.size(); i++) {
        HelpSheet a = (HelpSheet)padre.temas.elementAt(i);
        if (a.toString().toLowerCase().indexOf(texto.getText().toLowerCase()) != -1){
          v.addElement(padre.temas.elementAt(i));
        }
      }
      lista.setListData(v);
    }
  }

  void lista_valueChanged(ListSelectionEvent e) {
    // Show help file
    if(lista.getSelectedIndex() != -1){
      HelpSheet h = (HelpSheet)lista.getSelectedValue();
      padre.contenido.muestraURL(h.direccion);
      // Clear tree selection
      arbol.setSelectionPath(null);
    }
  }

  void texto_actionPerformed(ActionEvent e) {
    // Show help themes
    if(texto.getText().length() == 0){
      JOptionPane.showMessageDialog(padre,"There are nothing to search","Info", 1);
    }
    else{
      Vector v = new Vector();
      for (int i = 0; i < padre.temas.size(); i++) {
        HelpSheet a = (HelpSheet)padre.temas.elementAt(i);
        if (a.toString().toLowerCase().indexOf(texto.getText().toLowerCase()) != -1){
          v.addElement(padre.temas.elementAt(i));
        }
      }
      lista.setListData(v);
    }
  }
}
class OpcionesAyuda_arbol_treeSelectionAdapter implements javax.swing.event.TreeSelectionListener {
  HelpOptions adaptee;

  OpcionesAyuda_arbol_treeSelectionAdapter(HelpOptions adaptee) {
    this.adaptee = adaptee;
  }
  public void valueChanged(TreeSelectionEvent e) {
    adaptee.arbol_valueChanged(e);
  }
}

class OpcionesAyuda_busca_actionAdapter implements java.awt.event.ActionListener {
  HelpOptions adaptee;

  OpcionesAyuda_busca_actionAdapter(HelpOptions adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.busca_actionPerformed(e);
  }
}

class OpcionesAyuda_lista_listSelectionAdapter implements javax.swing.event.ListSelectionListener {
  HelpOptions adaptee;

  OpcionesAyuda_lista_listSelectionAdapter(HelpOptions adaptee) {
    this.adaptee = adaptee;
  }
  public void valueChanged(ListSelectionEvent e) {
    adaptee.lista_valueChanged(e);
  }
}

class OpcionesAyuda_texto_actionAdapter implements java.awt.event.ActionListener {
  HelpOptions adaptee;

  OpcionesAyuda_texto_actionAdapter(HelpOptions adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.texto_actionPerformed(e);
  }
}