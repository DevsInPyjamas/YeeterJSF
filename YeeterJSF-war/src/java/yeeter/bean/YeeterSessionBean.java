/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Usuario;

/**
 *
 * @author alec
 */
@Named(value = "yeeterSessionBean")
@SessionScoped
public class YeeterSessionBean implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    
    
    private Integer idLoggedUser;

    /**
     * Creates a new instance of YeeterSessionBean
     */
    public YeeterSessionBean() {
    }

    public Integer getIdLoggedUser() {
        return idLoggedUser;
    }

    public void setIdLoggedUser(Integer idLoggedUser) {
        this.idLoggedUser = idLoggedUser;
    }
    
    public Usuario getLoggedUserObject() {
        return (idLoggedUser == null) ? null : this.usuarioFacade.find(idLoggedUser);
    }
    
}
