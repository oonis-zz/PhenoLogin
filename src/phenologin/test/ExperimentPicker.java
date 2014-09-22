/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phenologin.test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import phenologin.PhenoLoginUI;
import phenologin.data.Experiment;

/**
 *
 * @author Oliver
 */
public class ExperimentPicker extends JFrame{
    public static void main( String[] args ) throws Exception{
        ExperimentPicker t = new ExperimentPicker();
        t.setSize( 600, 500 );
        t.setLocationRelativeTo( null );
        t.setDefaultCloseOperation( EXIT_ON_CLOSE );
        t.setVisible( true );
    }
    
    private ExperimentPicker(){
        JPanel jp = new JPanel();
        jp.setLayout( new BoxLayout( jp, BoxLayout.Y_AXIS ) );
        jp.add(new JButton( "Test" ){{
            addActionListener(e -> {
                PhenoLoginUI plui = new PhenoLoginUI( ExperimentPicker.this, true );
                plui.setLocationRelativeTo(ExperimentPicker.this );
                plui.setVisible(true);
                setTitle( "return value: " + plui.getReturnValue() );
                plui.getServer().addListener( (s,i) -> setTitle( s ) );
                List< Experiment > expList = new ArrayList();
                try {
                    for( int id : plui.getServer().getExperimentIds() ){
                        expList.add(  plui.getServer().getExperiment( id ) );
                    }
                } catch (Exception ex) {
                    Logger.getLogger(ExperimentPicker.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                TableModel dataModel = new AbstractTableModel() {
                    public int getColumnCount() { return 2; }
                    public int getRowCount() { return expList.size();}
                    public String getColumnName( int col ){ return colNames[col]; }
                    final String[] colNames = { "id", "name" };
                    public Object getValueAt(int row, int col) { 
                        if( row < 0 || row >= expList.size() )
                            throw new Error( "unexpected row index: " + row );
                       Experiment e = expList.get( row );
                        if( col == 0 )
                            return e.intMap.get( "id" );
                        else if( col == 1 )
                            return e.stringMap.get( "name" );
                        else
                            throw new Error( "unexpected column index: " + col );
                    }
                };
                JTable table = new JTable(dataModel);
                
                jp.removeAll();
                jp.add( new JScrollPane( table ) );
            });
        }});
        
        setContentPane( jp );
    }
}
