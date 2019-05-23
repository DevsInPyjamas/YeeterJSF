/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Usuario;

/**
 *
 * @author jesus
 */
@Named(value = "profileBean")
@SessionScoped
public class ProfileBean implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    @Inject
    private YeeterSessionBean sessionBean;
    
    protected boolean isLoggedUser;
    protected boolean editar = false;
    protected Usuario usuarioSeleccionado;
    protected String message;

    /**
     * Creates a new instance of ProfileBean
     */
    public ProfileBean() {
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public boolean isIsLoggedUser() {
        return isLoggedUser;
    }

    public void setIsLoggedUser(boolean isLoggedUser) {
        this.isLoggedUser = isLoggedUser;
    }
    
    public void setIsLoggedUser() {
        this.isLoggedUser = this.usuarioSeleccionado.equals(this.sessionBean.getLoggedUserObject());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isEditar() {
        return editar;
    }

    public void setEditar(boolean editar) {
        this.editar = editar;
    }
 
    @PostConstruct
    public void init(){
        this.usuarioSeleccionado = this.sessionBean.getLoggedUserObject();
        this.setIsLoggedUser();
    }
    
    public String doModificar(){
        editar = true;
        return "profilePanel";
    }
    
    public String doGuardar() {
        this.usuarioFacade.edit(usuarioSeleccionado);
        this.init();
        this.message = "Se ha modificado correctamente el perfil";
        this.editar = false;
        return "welcomepage";
    }
    
}
