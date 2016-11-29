
package javatodatabase;

import java.io.IOException;
import java.sql.*;
import java.util.Date;
import java.util.*;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;


public class NewJFrame  extends javax.swing.JFrame {
    
    
    
    private ConnectDATA D = null;
    private Date myDate  = null;
    Webserver server1  = null;
    private String[]  AllinViolation;
    private Vector<String> LeftList = new  Vector<String>();
    private Vector<String> RightList = new  Vector<String>();
    private Report Rp = null;

    
    public NewJFrame() throws IOException 
    {
        initComponents();
        D = new ConnectDATA(jTextArea1);
        D.connect();
        Rp = new Report(D);
        myDate = new Date();
        server1 = new Webserver(80, "",jTextArea1,D);
      TimeRefresh(myDate ,jLabel4);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        Exit = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel4 = new javax.swing.JLabel();
        Connect = new javax.swing.JButton();
        eventA = new javax.swing.JButton();
        eventB = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        eventA1 = new javax.swing.JButton();
        eventA2 = new javax.swing.JButton();
        eventA3 = new javax.swing.JButton();

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Exit.setText("Exit");
        Exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitActionPerformed(evt);
            }
        });

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setAutoscrolls(false);
        jTextArea1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextArea1.setMaximumSize(new java.awt.Dimension(4, 25));
        jTextArea1.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(jTextArea1);
        jTextArea1.getAccessibleContext().setAccessibleDescription("");

        jLabel4.setFont(new java.awt.Font("Consolas", 0, 18)); // NOI18N
        jLabel4.setText("Time");

        Connect.setText("Connect");
        Connect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConnectActionPerformed(evt);
            }
        });

        eventA.setText("A");
        eventA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventAActionPerformed(evt);
            }
        });

        eventB.setText("B");
        eventB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventBActionPerformed(evt);
            }
        });

        jList1.setName("JList1"); // NOI18N
        jScrollPane3.setViewportView(jList1);

        jList2.setName("JList2"); // NOI18N
        jScrollPane2.setViewportView(jList2);

        jButton1.setLabel(">>");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setLabel("<<");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("製成報表");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("刷新清單");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        eventA1.setText("C");
        eventA1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventA1ActionPerformed(evt);
            }
        });

        eventA2.setText("E");
        eventA2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventA2ActionPerformed(evt);
            }
        });

        eventA3.setText("D");
        eventA3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eventA3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(eventA, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(eventA1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(eventA2, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(eventB, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(eventA3, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(39, 39, 39)
                                .addComponent(jButton3))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton2)
                                    .addComponent(jButton1))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(4, 4, 4)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Connect, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Exit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(jButton1)
                                .addGap(27, 27, 27)
                                .addComponent(jButton2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(52, 52, 52)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton4)
                            .addComponent(jButton3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(eventA)
                            .addComponent(eventA1)
                            .addComponent(eventA2))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventB)
                    .addComponent(eventA3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Connect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(Exit)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ExitActionPerformed
if(D.is_connected())  
{ 
    D.close_conn();
}
     //J.dispose();
     System.exit(0);
    }//GEN-LAST:event_ExitActionPerformed

    private void ConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConnectActionPerformed
       if(!D.is_connected()) D.connect();
       else D.displayTOtextarea("Already connected.\n");
    }//GEN-LAST:event_ConnectActionPerformed

    private void eventAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventAActionPerformed
        server1.setATRUE();
        System.out.println("A True");

    }//GEN-LAST:event_eventAActionPerformed

    private void eventBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventBActionPerformed
        server1.setBTRUE();
        System.out.println("B True");
    }//GEN-LAST:event_eventBActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
 if(D.is_connected())
 {  RightList.clear();
 LeftList.clear();
 
        try {
            D.searchvio();
            
            int Row = D.getresultcount();
            System.out.println(Row);
            AllinViolation = new String[Row];
            for(int i=0;i<Row;i++)
                AllinViolation[i] = new String("");
            
            D.DisplayList(AllinViolation);
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
       
         
        for(int i=0;i<AllinViolation.length;i++)
           LeftList.add(AllinViolation[i]);
       
      
   jList1.setListData(LeftList);
    jList2.setListData(RightList);
 }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            Rp.makereport(RightList);
        } catch (Exception ex) {
           System.out.println(ex.toString());
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     List<String> selctedList=  jList1.getSelectedValuesList(); //抓jlist1選取的
   
     String[] stringArray = selctedList.toArray(new String[0]);
    for(int i =0;i<stringArray.length;i++)
    {
        RightList.add(stringArray[i]);
        LeftList.remove(stringArray[i]);
    }
    jList1.setListData(LeftList);
    jList2.setListData(RightList);
  //   for(int i =0;i<stringArray.length;i++)       
  //    System.out.println(stringArray[i]);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
           List<String> selctedList=  jList2.getSelectedValuesList(); //抓jlist2選取的
   
     String[] stringArray = selctedList.toArray(new String[0]);
    for(int i =0;i<stringArray.length;i++)
    {
        RightList.remove(stringArray[i]);
        LeftList.add(stringArray[i]);
    }
    jList1.setListData(LeftList);
    jList2.setListData(RightList);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void eventA1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventA1ActionPerformed
   server1.setCTRUE();
        System.out.println("C True");
    }//GEN-LAST:event_eventA1ActionPerformed

    private void eventA2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventA2ActionPerformed
      server1.setETRUE();
        System.out.println("E True");        // TODO add your handling code here:
    }//GEN-LAST:event_eventA2ActionPerformed

    private void eventA3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eventA3ActionPerformed
       server1.setDTRUE();
        System.out.println("D True");
    }//GEN-LAST:event_eventA3ActionPerformed

    /**
     *
     * @param args
     * @throws IOException
     */
    public static void main(String args[]) throws IOException 
    {   
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
        NewJFrame J = new NewJFrame();
 
        J.setLocationRelativeTo(null);
        J.setVisible(true);
         
        ThreadTime T = new ThreadTime(J.myDate,J.jLabel4);
        T.start();
        J.server1.run();
        
        
       /* java.awt.EventQueue.invokeLater(new Runnable() 
        {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
        */

    
    }
  public void TimeRefresh( Date NewmyDate , JLabel displayLabel)
  {
        SimpleDateFormat formatter =new SimpleDateFormat("yyyy/MM/dd  HH:mm:ss");
        NewmyDate.setTime(System.currentTimeMillis());
        displayLabel.setText(formatter.format(NewmyDate));
  }
public boolean checkCarID(String searchWord)
{
                for(int j =0;j < searchWord.length();j++)
                    {
                     if((searchWord.charAt(j)>='0'&& searchWord.charAt(j)<='9')||
                       (searchWord.charAt(j)>='A'&& searchWord.charAt(j)<='Z')||
                       (searchWord.charAt(j)>='a'&& searchWord.charAt(j)<='z')||
                       (searchWord.charAt(j)== '-')){}
                     else 
                        {
                             D.displayTOtextarea("Cannnot contain not digit or character.\n");
                            return false;  
                        }
                    }
                return true;

}
public boolean checkSpaceOrEmpty(String searchWord)
{
     if (searchWord.isEmpty())     //是否為空字串
       {
          D.displayTOtextarea("Textfield cannnot be empty.\n");
          return false;
       }
       else if(searchWord.contains(" ")) //是否有空白
        {
          D.displayTOtextarea("Textfield cannnot have space.\n");
          return false;    
        }
     return true;
}
public boolean checkonlyNumber(String searchWord)
{
    for(int j =0 ;j < searchWord.length();j++)
                    {
                    if((searchWord.charAt(j)>='0'&&searchWord.charAt(j)<='9')||
                            searchWord.charAt(j) =='.'){}
                    else 
                        {
                             D.displayTOtextarea("Cannnot contain not digit or point.\n");
                            return false;  
                        }
                    }
                return true;
}

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Connect;
    private javax.swing.JButton Exit;
    private javax.swing.JButton eventA;
    private javax.swing.JButton eventA1;
    private javax.swing.JButton eventA2;
    private javax.swing.JButton eventA3;
    private javax.swing.JButton eventB;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables

}


