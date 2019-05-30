/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import yeeterapp.ejb.MensajeFacade;
import yeeterapp.ejb.NotificacionesFacade;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Mensaje;
import yeeterapp.entity.Notificaciones;
import yeeterapp.entity.Usuario;

/**
 *
 * @author jesus
 */
@Named(value = "mensajeBean")
@SessionScoped
public class MensajeBean implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private MensajeFacade mensajeFacade;

    @EJB
    private NotificacionesFacade notificacionesFacade;
    
    @Inject YeeterSessionBean sessionBean;
    @Inject ChatBean chatBean;
    protected Usuario amigo, loggedUser;
    protected Mensaje mensaje;
    
    /**
     * Creates a new instance of MensajeBean
     */
    public MensajeBean() {
    }
    
    @PostConstruct
    public void init() {
        this.loggedUser = this.sessionBean.getLoggedUserObject();
        this.amigo = this.chatBean.getAmigoChat();
        this.mensaje = new Mensaje();
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }

    public Usuario getAmigo() {
        return amigo;
    }

    public void setAmigo(Usuario amigo) {
        this.amigo = amigo;
    }
    
    public String doEnviarMensaje(){
        mensaje.setIdEmisor(loggedUser);
        mensaje.setIdReceptor(amigo);
        Date fecha = new Date(System.currentTimeMillis());
        mensaje.setFecha(fecha);
        
        Notificaciones notificacion = new Notificaciones();
        notificacion.setContenido("El usuario " + loggedUser.getUsername() + " te ha enviado un mensaje");     
        notificacion.setLink("chat");
        notificacion.setIdUsuario(amigo);
        notificacion.setNotificacionLeida(false);
        notificacionesFacade.create(notificacion);
        
        List<Notificaciones> l = amigo.getNotificacionesList();
        l.add(notificacion);
        usuarioFacade.edit(amigo);

        mensajeFacade.create(mensaje);
        chatBean.doCrearChat(amigo);
        mensaje = new Mensaje();
        return "chat";
    }
}
