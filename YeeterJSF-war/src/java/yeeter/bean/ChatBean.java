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
import javax.enterprise.context.RequestScoped;
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
@Named(value = "chatBean")
@SessionScoped
public class ChatBean implements Serializable {

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
    
    @PostConstruct
    public void init(){
        this.usuario = this.sessionBean.getLoggedUserObject();
    }
    
    public String doCrearChat(Usuario amigo) {
        this.setAmigoChat(amigo);
        this.mensajes = this.mensajeFacade.queryMensajesAmigos(usuario.getId(), amigoChat.getId());
        return "chat";
    }
}
