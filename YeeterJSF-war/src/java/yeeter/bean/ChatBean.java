/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
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
@Named(value = "chatBean")
@RequestScoped
public class ChatBean {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private NotificacionesFacade notificacionesFacade;
    @EJB
    private MensajeFacade mensajeFacade;
    
    
    
    
    @Inject
    private YeeterSessionBean sessionBean;
    
    protected List<Mensaje> mensajes;     
    protected Usuario usuario;
    protected Usuario amigoChat;
    protected String message;
    protected Mensaje mensaje;
    protected Notificaciones notificaciones;
    
    /**
     * Creates a new instance of ChatBean
     */
    public ChatBean() {
    }

    public List<Mensaje> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<Mensaje> mensajes) {
        this.mensajes = mensajes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Usuario getAmigoChat() {
        return amigoChat;
    }

    public void setAmigoChat(Usuario amigoChat) {
        this.amigoChat = amigoChat;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mensaje getMensaje() {
        return mensaje;
    }

    public void setMensaje(Mensaje mensaje) {
        this.mensaje = mensaje;
    }
    
    @PostConstruct
    public void init(){
        this.usuario = this.sessionBean.getLoggedUserObject();
        this.mensaje = new Mensaje();
        this.notificaciones = new Notificaciones();
    }
    
    public String doCrearChat(Usuario amigo) {
        this.setAmigoChat(amigo);
        this.mensajes = this.mensajeFacade.queryMensajesAmigos(usuario.getId(), amigoChat.getId());
        return "chat";
    }
    
    public String doEnviarMensaje(){
        mensaje.setIdEmisor(usuario);
        mensaje.setIdReceptor(amigoChat);
        Date fecha = new Date(System.currentTimeMillis());
        mensaje.setFecha(fecha);
       notificaciones = new Notificaciones();
         notificaciones.setContenido("El usuario " + usuario.getUsername() + " te ha enviado un mensaje");     
        notificaciones.setLink("");
        notificaciones.setIdUsuario(amigoChat);
        
       List<Notificaciones> l = amigoChat.getNotificacionesList();
        
        notificacionesFacade.create(notificaciones);
        l.add(notificaciones);
        usuarioFacade.edit(amigoChat);
        
        mensajeFacade.create(mensaje);
        
        return "chat";
    }
}
