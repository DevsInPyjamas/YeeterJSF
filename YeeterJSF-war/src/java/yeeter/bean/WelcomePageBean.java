/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import yeeterapp.entity.Usuario;

/**
 *
 * @author alec
 */
@Named(value = "welcomePageBean")
@RequestScoped
public class WelcomePageBean {
    
    @Inject YeeterSessionBean sessionBean;
    @Inject ProfileBean profileBean;
    Usuario loggedUser;

    /**
     * Creates a new instance of WelcomePageBean
     */
    public WelcomePageBean() {
    }
    
    @PostConstruct
    public void init() {
        this.loggedUser = sessionBean.getLoggedUserObject();
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }
    
    public String doRedirectToProfile() {
        profileBean.setUsuarioSeleccionado(loggedUser);
        this.profileBean.updateData();
        return "profilePanel";
    }
    
}
