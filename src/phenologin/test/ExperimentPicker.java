/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phenologin.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JEditorPane;
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
    private static final int maxExperiments = -1; //-1 indicates no limit
    private JTable expTable = null;
    
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
                new Thread( () -> {
                    PhenoLoginUI plui = new PhenoLoginUI( ExperimentPicker.this, true );
                    plui.setLocationRelativeTo(ExperimentPicker.this );
                    plui.setVisible(true);
                    setTitle( "return value: " + plui.getReturnValue() );
                    plui.getServer().addListener( (s,i) -> setTitle( s ) );
                    List< Experiment > expList = new ArrayList();
                    try {
                        for( int id : plui.getServer().getExperimentIds() ){
                            try{
                                expList.add(  plui.getServer().getExperiment( id ) );
                            }catch( Exception ex ){
                                ex.printStackTrace();
                                continue;
                            }
                            updateDisplay( expList );
                            if( maxExperiments != -1 && expList.size() >= maxExperiments )
                                break;
                        }
                    } catch (Exception ex) {
                        Logger.getLogger(ExperimentPicker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }).start();
            });
        }});
        setContentPane( jp );
    }
    
    private void updateDisplay( List< Experiment > expList ){
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
        if( expTable != null ){
            int i = expTable.getSelectedRow();
            if( i != -1 )
                table.setRowSelectionInterval( i,i );
        }
        expTable = table;
//        table.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                int row = table.rowAtPoint(evt.getPoint());
//                int col = table.columnAtPoint(evt.getPoint());
//                if (row >= 0 && col >= 0) 
//                    launchExpDetails( expList.get( row ) );
//            }
//        });
        
        JPanel jp = new JPanel();
        jp.setLayout( new BoxLayout( jp, BoxLayout.Y_AXIS ) );
        jp.add( new JScrollPane( table ) );
        JPanel bottomPane = new JPanel();
        bottomPane.setLayout( new BoxLayout( bottomPane, BoxLayout.X_AXIS ) );
        bottomPane.add( new JButton( "Show Full Details" ){{addActionListener(e->{
            int i = table.getSelectedRow();
            if( i > -1 && i < expList.size() )
                launchExpDetails( expList.get( i ) );
        });}});
        bottomPane.add( new JButton( "Show Selection Details" ){{addActionListener(e->{
            int i = table.getSelectedRow();
            if( i > -1 && i < expList.size() )
                launchSelectionDetails( expList.get( i ) );
        });}});
        jp.add( bottomPane );
        setContentPane( jp );
        validate();
    }
    
    private void launchHtmlDisplay( String content ){
        JFrame jf = new JFrame();
        JEditorPane jep = new JEditorPane( "text/html", "" );
        jep.setText( content );
        jf.setContentPane( new JScrollPane( jep ) );
        jf.setSize( 500, 500 );
        jf.setLocationRelativeTo( null );
        jf.setVisible( true );
    }
    
    private void launchSelectionDetails( Experiment exp ){
        StringBuilder sb = new StringBuilder();
        
        sb.append( "<html><table><tr><th>Name</th><th>Location</th></tr>" );
        for( Map< String, Object > job : exp.arrMap.get( "experiment_jobs" ) ){
            for( Map< String, Object > jobjob : (List<Map<String,Object>>)job.get( "job_job_seeds" ) ){
                Map<String,Object> m = (Map<String,Object>)jobjob.get( "seed" );
                sb.append( "<tr><td>" + m.get( "allele_name" ) + "</td><td>" + m.get( "location" ) + "</td></tr>" );
            }                    
        }
        sb.append( "</table></html>" );
        
        launchHtmlDisplay( sb.toString() );
    }
    
    private void launchExpDetails( Experiment exp ){
        StringBuilder sb = new StringBuilder();
        sb.append( "<html><style>td{border:1px solid black;}th{border:2px solid black;}</style>" );
        
        sb.append( "<table><tr><th colspan=\"2\">STRINGS</th></tr><tr><th>key</th><th>value</th></tr>" );
        for( Entry< String, String > e : exp.stringMap.entrySet() )
            sb.append( "<tr><td>" + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>" );
        sb.append( "</table>" );
        
        sb.append( "<table><tr><th colspan=\"2\">NUMBERS</th></tr><tr><th>key</th><th>value</th></tr>" );
        for( Entry< String, Integer > e : exp.intMap.entrySet() )
            sb.append( "<tr><td>" + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>" );
        sb.append( "</table>" );
        
        sb.append( "<table><tr><th colspan=\"2\">BOOLEANS</th></tr><tr><th>key</th><th>value</th></tr>" );
        for( Entry< String, Boolean > e : exp.boolMap.entrySet() )
            sb.append( "<tr><td>" + e.getKey() + "</td><td>" + e.getValue() + "</td></tr>" );
        sb.append( "</table>" );
        
        sb.append( "<table><tr><th colspan=\"2\">LISTS</th></tr><tr><th>key</th><th>value</th></tr>" );
        for( Entry< String, List< Map< String, Object > > > e : exp.arrMap.entrySet() )
            sb.append( "<tr><td>" + e.getKey() + "</td><td>" + toHTML( e.getValue() ) + "</td></tr>" );
        sb.append( "</table>" );
        
        sb.append( "</html>" );
        launchHtmlDisplay( sb.toString() );
    }
    
    private String toHTML( List< Map< String, Object > > list ){
        StringBuilder sb = new StringBuilder();
        sb.append( "<ul>" );
        for( Map< String, Object > map : list )
            sb.append( "<li>" + toHTML( map ) + "</li>" );
        sb.append( "</ul>" );
        return sb.toString();
    }
    
    private String toHTML( Map< String, Object > map ){
        StringBuilder sb = new StringBuilder();
        sb.append( "<table><tr><th>key</th><th>value</th></tr>" );
        for( Entry< String, Object > e : map.entrySet() ){
            Object val = e.getValue();
            String s;
            if( val == null ){
                s = "null";
            }else{
                if( Map.class.isAssignableFrom( val.getClass() ) ){
                    s = toHTML( (Map<String,Object>)val );
                }else if( List.class.isAssignableFrom( val.getClass() ) ){
                    s = toHTML( (List<Map<String,Object>>)val );
                }else{
                    s = val.toString();
                }
            }
            sb.append( "<tr><td>" + e.getKey() + "</td><td>" + s + "</td></tr>" );
        }
        sb.append( "</table>" );
        return sb.toString();
    }
}
