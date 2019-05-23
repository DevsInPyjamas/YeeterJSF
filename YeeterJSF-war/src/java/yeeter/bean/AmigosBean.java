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
    protected List<Usuario> listaAmigos;
    protected List<Usuario> listaCoincidencias;
    protected Usuario usuarioSeleccionado;
    protected String busqueda;

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
    
    public List<Usuario> getListaAmigos() {
        return listaAmigos;
    }

    public void setListaAmigos(List<Usuario> listaAmigos) {
        this.listaAmigos = listaAmigos;
    }

    public Usuario getUsuarioSeleccionado() {
        return usuarioSeleccionado;
    }

    public void setUsuarioSeleccionado(Usuario usuarioSeleccionado) {
        this.usuarioSeleccionado = usuarioSeleccionado;
    }

    public List<Usuario> getListaCoincidencias() {
        return listaCoincidencias;
    }

    public void setListaCoincidencias(List<Usuario> listaCoincidencias) {
        this.listaCoincidencias = listaCoincidencias;
    }
    
    @PostConstruct
    public void init(){
        this.listaAmigos = sessionBean.getLoggedUserObject().getUsuarioList();
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
        
        return "buscaramigo";
    }
    
    public boolean noAmigo(Usuario usuario1, Usuario usuario2){
        return !usuario1.getUsuarioList().contains(usuario2);
    }
}
