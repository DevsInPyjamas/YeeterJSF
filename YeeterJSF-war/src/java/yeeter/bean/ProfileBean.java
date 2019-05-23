/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    protected boolean isLoggedUser;
    protected boolean editar = false;
    protected Usuario usuarioSeleccionado;
    protected String message;
    protected String fecha;

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
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
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
        this.fecha = format.format(usuarioSeleccionado.getFechaNacimiento());
    }
    
    public String doModificar(){
        editar = true;
        return "profilePanel";
    }
    
    public String doGuardar() throws ParseException {
        this.usuarioSeleccionado.setFechaNacimiento(format.parse(fecha));
        this.usuarioFacade.edit(usuarioSeleccionado);
        this.init();
        this.message = "Se ha modificado correctamente el perfil";
        this.editar = false;
        return "profilePanel";
    }
    
}
