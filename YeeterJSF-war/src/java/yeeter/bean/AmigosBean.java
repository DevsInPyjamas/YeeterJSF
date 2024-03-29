/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.inject.Named;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import yeeterapp.ejb.PeticionAmistadFacade;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.PeticionAmistad;
import yeeterapp.entity.PeticionAmistadPK;
import yeeterapp.entity.Usuario;

/**
 *
 * @author jugr9
 */
@Named(value = "amigosBean")
@RequestScoped
public class AmigosBean implements Serializable {
    
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private PeticionAmistadFacade peticionFacade;
    
    @Inject YeeterSessionBean sessionBean;
    @Inject ProfileBean profileBean;
    private List<Usuario> listaCoincidencias;
    private String busqueda;
    private String message;
    private Usuario usuarioSeleccionado;

    /**
     * Creates a new instance of AmigosBean
     */
    public AmigosBean() {
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<Usuario> getListaCoincidencias() {
        return listaCoincidencias;
    }

    public void setListaCoincidencias(List<Usuario> listaCoincidencias) {
        this.listaCoincidencias = listaCoincidencias;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }
    
    @PostConstruct
    public void init(){
        this.listaCoincidencias = usuarioFacade.findAll();
    }
    
    public String searchCoincidences(String busqueda){
        this.listaCoincidencias = usuarioFacade.queryUserByUsernameOrName(busqueda);
        
        return "buscaramigo";
    }
    
    public String doAddFriend(Usuario user){
        PeticionAmistadPK petPK = new PeticionAmistadPK(sessionBean.getIdLoggedUser(),user.getId());
        PeticionAmistad pet = new PeticionAmistad();
        pet.setPeticionAmistadPK(petPK);
        pet.setUsuario(sessionBean.getLoggedUserObject());
        pet.setUsuario1(user);
        pet.setMensaje("El usuario "+ sessionBean.getLoggedUserObject().getUsername() + " quiere añadirte como amigo.");
        peticionFacade.create(pet);
        
        this.message = "La solicitud de amistad se ha enviado con exito";
        this.init();
        
        return "buscaramigo";
    }
    
    public boolean noAmigo(Usuario usuario1, Usuario usuario2){
        return !usuario1.getUsuarioList().contains(usuario2);
    }
    
    public String doRedirectToUserProfile(Usuario user){
        this.setUsuarioSeleccionado(user);
        profileBean.init();
        return "profilePanel";
    }
    
    public void nombreChangeListener() {
        if(this.busqueda != null && !this.busqueda.isEmpty()){
            this.listaCoincidencias = this.usuarioFacade.queryUserByUsernameOrName(busqueda);
        }
    } 
}
