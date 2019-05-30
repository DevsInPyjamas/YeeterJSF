/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import yeeterapp.ejb.MensajeFacade;
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
    private MensajeFacade mensajeFacade;

    @Inject
    private YeeterSessionBean sessionBean;
    private Usuario loggedUser;

    private List<Mensaje> listaMensajesSiendoEmisor;
    private List<Mensaje> listaMensajesSiendoReceptor;
    private List<Usuario> listaConversaciones;

    /**
     * Creates a new instance of ConversacionesBean
     */
    public ConversacionesBean() {
    }
    
    @PostConstruct
    public void init() {
        this.listaConversaciones = new ArrayList<>();
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

    public List<Usuario> getListaConversaciones() {
        for (Mensaje mn : listaMensajesSiendoEmisor) {
            if(!listaConversaciones.contains(mn.getIdReceptor()))
                this.listaConversaciones.add(mn.getIdReceptor());
        }
        for (Mensaje mn : listaMensajesSiendoReceptor) {
            if(!listaConversaciones.contains(mn.getIdEmisor()))
                this.listaConversaciones.add(mn.getIdEmisor());
        }
        return listaConversaciones;
    }

    public void setListaConversaciones(List<Usuario> listaConversaciones) {
        this.listaConversaciones = listaConversaciones;
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }
}
