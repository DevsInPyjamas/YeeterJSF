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
import yeeterapp.entity.PeticionAmistad;
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
    
    @Inject
    private AmigosBean amigosBean;
    
    private SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    private boolean isLoggedUser;
    private boolean editar = false;
    private Usuario usuarioSeleccionado;
    private String message, error;
    private String fecha;
    private String oldPass, newPass, newPassEq;

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

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getNewPassEq() {
        return newPassEq;
    }

    public void setNewPassEq(String newPassEq) {
        this.newPassEq = newPassEq;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
 
    @PostConstruct
    public void init(){
        this.usuarioSeleccionado = (this.amigosBean.getUsuarioSeleccionado() == null) ? this.sessionBean.getLoggedUserObject() : this.amigosBean.getUsuarioSeleccionado();
        this.updateData();
        message = error = null;
    }
    
    public String doModificar(){
        this.setEditar(true);
        return "profilePanel";
    }
    
    public boolean existePeticionYa(){
        return this.sessionBean.getLoggedUserObject().getPeticionAmistadList1().contains(new PeticionAmistad(usuarioSeleccionado.getId(), sessionBean.getIdLoggedUser()));
    }
    
    public void updateData() {
        this.setIsLoggedUser();
        this.fecha = format.format(usuarioSeleccionado.getFechaNacimiento());
    }
    
    public String doGuardar() throws ParseException {
        this.usuarioSeleccionado.setFechaNacimiento(format.parse(fecha));
        this.usuarioFacade.edit(usuarioSeleccionado);
        this.init();
        this.message = "Se ha modificado correctamente el perfil";
        this.editar = false;
        return "profilePanel";
    }
    
    public String doGuardarPass() {
        if(oldPass.equals(usuarioSeleccionado.getPassword())){
            if(newPass.equals(newPassEq)){
                this.usuarioSeleccionado.setPassword(newPass);
                this.usuarioFacade.edit(usuarioSeleccionado);
                message = "Contraseña modificada correctamente";
                error = null;
            } else {
                error = "Las contraseñas no coinciden";
                message = null;
                return "modificarContrasena";
            }
        } else {
            error = "Contraseña antigua incorrecta";
            message = null;
            return "modificarContrasena";
        }
        return "profilePanel";
    }
}
