/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import yeeterapp.ejb.MensajeFacade;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Mensaje;
import yeeterapp.entity.Usuario;

/**
 *
 * @author pedro
 */
@Named(value = "conversacionesBean")
@Dependent
public class ConversacionesBean {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private MensajeFacade mensajeFacade;

    @Inject
    YeeterSessionBean sessionBean;
    Usuario loggedUser;

    List<Mensaje> listaMensajesSiendoEmisor;
    List<Mensaje> listaMensajesSiendoReceptor;
    Set<Usuario> listaConversaciones = new HashSet<>();

    /**
     * Creates a new instance of ConversacionesBean
     */
    public ConversacionesBean() {
    }
    
    @PostConstruct
    public void init() {
        this.loggedUser = sessionBean.getLoggedUserObject();
        this.listaMensajesSiendoEmisor = this.mensajeFacade.queryfindByEmisor(loggedUser);
        this.listaMensajesSiendoReceptor = this.mensajeFacade.queryfindByReceptor(loggedUser);

    }

    public List<Mensaje> getListaMensajesSiendoEmisor() {
        return listaMensajesSiendoEmisor;
    }

    public void setListaMensajesSiendoEmisor(List<Mensaje> listaMensajesSiendoEmisor) {
        this.listaMensajesSiendoEmisor = listaMensajesSiendoEmisor;
    }

    public List<Mensaje> getListaMensajesSiendoReceptor() {
        return listaMensajesSiendoReceptor;
    }

    public void setListaMensajesSiendoReceptor(List<Mensaje> listaMensajesSiendoReceptor) {
        this.listaMensajesSiendoReceptor = listaMensajesSiendoReceptor;
    }

    

    public Set<Usuario> getListaConversaciones() {
        for (Mensaje mn : listaMensajesSiendoEmisor) {
            this.listaConversaciones.add(mn.getIdReceptor());
        }
        for (Mensaje mensa : listaMensajesSiendoReceptor) {
            this.listaConversaciones.add(mensa.getIdEmisor());
        }
        if (listaConversaciones.isEmpty()) {
            listaVacia();
        }
        return listaConversaciones;
    }

    public void setListaConversaciones(Set<Usuario> listaConversaciones) {
        this.listaConversaciones = listaConversaciones;
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }

    public String listaVacia() {
        return " No hay conversaciones disponibles";
    }
}
