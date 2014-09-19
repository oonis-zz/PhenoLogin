/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phenologin.test;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import phenologin.ExperimentInfo;
import phenologin.PhenoLoginUI;

/**
 *
 * @author Oliver
 */
public class TestUI extends JFrame{
    public static void main( String[] args ) throws Exception{
        TestUI t = new TestUI();
        t.setSize( 300, 80 );
        t.setLocationRelativeTo( null );
        t.setVisible( true );
    }
    
    private TestUI(){
        JPanel jp = new JPanel();
        jp.setLayout( new BoxLayout( jp, BoxLayout.Y_AXIS ) );
        JTextArea display = new JTextArea();
        jp.add( new JButton( "Test" ){{
            addActionListener( e -> {
                PhenoLoginUI plui = new PhenoLoginUI( TestUI.this, true );
                plui.setLocationRelativeTo( TestUI.this );
                plui.setVisible(true);
                setTitle( "return value: " + plui.getReturnValue() );
                StringBuilder sb = new StringBuilder();
                sb.append( "Experiments:" );
                try {
                    for( ExperimentInfo ei : plui.getServer().getExperiments() )
                        sb.append( "\n\tname: " + ei.getName() + " id: " + ei.getID() );
                } catch (Exception ex) {
                    Logger.getLogger(TestUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                display.setText( sb.toString() );
            });
        }});
        jp.add( new JScrollPane( display ) );
        
        setContentPane( jp );
    }
}
