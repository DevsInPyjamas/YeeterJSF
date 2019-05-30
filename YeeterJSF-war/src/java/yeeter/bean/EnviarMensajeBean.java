/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import yeeterapp.ejb.MensajeFacade;
import yeeterapp.ejb.NotificacionesFacade;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Usuario;

/**
 *
 * @author pedro
 */
@Named(value = "enviarMensajeBean")
@Dependent
public class EnviarMensajeBean {

    @EJB
    private NotificacionesFacade notificacionesFacade;

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private MensajeFacade mensajeFacade;

    
    @Inject YeeterSessionBean sessionBean;
    private Usuario loggedUser;
    /**
     * Creates a new instance of EnviarMensajeBean
     */
    public EnviarMensajeBean() {
    }
    @PostConstruct
    public void init() {
        this.loggedUser = sessionBean.getLoggedUserObject();
        
    }
}
