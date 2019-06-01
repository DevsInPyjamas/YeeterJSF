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
import javax.enterprise.context.RequestScoped;
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
@RequestScoped
public class MensajeBean implements Serializable {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private MensajeFacade mensajeFacade;

    @EJB
    private NotificacionesFacade notificacionesFacade;
    
    @Inject private YeeterSessionBean sessionBean;
    @Inject private ChatBean chatBean;
    private Usuario amigo, loggedUser;
    private Mensaje mensaje;
    
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
        mensaje.setIdEmisor(sessionBean.getLoggedUserObject());
        mensaje.setIdReceptor(chatBean.getAmigoChat());
        Date fecha = new Date(System.currentTimeMillis());
        mensaje.setFecha(fecha);
        
        Notificaciones notificacion = new Notificaciones();
        notificacion.setContenido("El usuario " + sessionBean.getLoggedUserObject().getUsername() + " te ha enviado un mensaje");     
        notificacion.setLink(sessionBean.getLoggedUserObject().getId().toString());
        notificacion.setIdUsuario(chatBean.getAmigoChat());
        notificacion.setNotificacionLeida(false);
        notificacionesFacade.create(notificacion);
        
        List<Notificaciones> l = chatBean.getAmigoChat().getNotificacionesList();
        l.add(notificacion);
        usuarioFacade.edit(chatBean.getAmigoChat());

        mensajeFacade.create(mensaje);
        chatBean.init();
        chatBean.doCrearChat(chatBean.getAmigoChat());
        mensaje = new Mensaje();
        return "chat";
        // en el link de la notificación añadir el id mío y luego hacer
        // una acción en notificacion para que reciba el id del usuario
        // y redireccionar a el chat con el usuario.
    }
}
