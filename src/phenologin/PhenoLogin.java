/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phenologin;

import java.awt.Component;
import java.awt.HeadlessException;
import javax.swing.JPanel;

/**
 * Note: To use this library you need to have the "Java EE 7 API Library" in
 * your project.
 *
 * @author Sam <willsc8forwings@gmail.com>
 */
public class PhenoLogin extends JPanel{

    /*
     * ================================================ STATIC VARIABLES ================================================
     */

    /**
     * The user has been authenticated correctly
     */
    
    public static final int APPROVE_OPTION = 1;

    /**
     * The user chose to not login, but instead use the program locally
     */
    public static final int OFFLINE_OPTION = 2;

    /**
     * The user canceled out of the login interface
     */
    public static final int CANCEL_OPTION = 0;

    /**
     * The user entered the wrong authentication
     */
    public static final int ERROR_OPTION = -1;

    /*
     * ================================================ MEMBER VARIABLES ================================================
     */
    private PhenoLoginUI jDialog = null;
    private PhenoServer jServer = null;
    private PhenoUser jUser = null;


    /*
     * ================================================== CONSTRUCTORS ==================================================
     */
    
    /**
     * Default constructor for LoginScreen.
     */
    public PhenoLogin() {
        
    }

    /*
     * ================================================ PRIMARY FUNCTIONS ===============================================
     */
    /**
     * Shows the login dialogue. Pauses the given parent until it the user
     * either logs in or cancels the operation.
     *
     * @param parent <code>Component</code>: The parent of this dialogue. This
     * parent will be paused until the user is finished with the login screen.
     * @return <code>int</code>:
     * <ul>
     * <li><code>PhenoLogin.APPROVE_OPTION</code> if the user successfully
     * logged in.
     * <li><code>PhenoLogin.OFFLINE_OPTION</code> if the user selected offline
     * mode.
     * <li><code>PhenoLogin.CANCEL_OPTION</code> if the user canceled the log in
     * procedure.
     * <li><code>PhenoLogin.ERROR_OPTION</code> if something went horribly
     * wrong.
     * </ul>
     * @throws java.awt.HeadlessException If called in an environment that does
     * not support mouse, keyboard, or display.
     */
    public int showLoginDialog(Component parent) throws HeadlessException {
        int val = showDialog(parent);
        if (val == PhenoLogin.APPROVE_OPTION) {
            System.out.println(jServer.getToken());
        }
        return val;
    }

    /**
     * Logs the user out.
     */
    public void logout() {
        
    }

    /*
     * ================================================ GETTERS & SETTERS ===============================================
     */
    /**
     *
     * @return <code>PhenoUser</code>:
     */
    public PhenoUser getUser() {
        return jUser;
    }

    public void setUser(PhenoUser user) {
        jUser = user;
    }

    /**
     *
     * @return <code>PhenoServer</code>:
     */
    public PhenoServer getServer() {
        return jServer;
    }

    /**
     *
     * @param server
     */
    public void setServer(PhenoServer server) {
        jServer = server;
    }

    /*
     * ================================================ PRIVATE FUNCTIONS ===============================================
     */
    private int showDialog(Component parent) throws HeadlessException {
        // Prevent to show second instance of dialog if the previous one still exists.
        if (jDialog != null) {
            return PhenoLogin.ERROR_OPTION;
        }

        jDialog = createDialog(parent);
        jDialog.setVisible(true);

        int retunVal = jDialog.getReturnValue();
        if (retunVal == PhenoLogin.APPROVE_OPTION) {
            jServer = jDialog.getServer();
            jUser = jDialog.getUser();
        }
        return retunVal;
    }

    private PhenoLoginUI createDialog(Component parent) {
        PhenoLoginUI dialog = new PhenoLoginUI(new javax.swing.JFrame(), true);
        return dialog;
    }

}
