/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import yeeterapp.ejb.MensajeFacade;
import yeeterapp.entity.Mensaje;
import yeeterapp.entity.Usuario;

/**
 *
 * @author jesus
 */
@Named(value = "chatBean")
@RequestScoped
public class ChatBean {
    @EJB
    private MensajeFacade mensajeFacade;
    
    @Inject
    private YeeterSessionBean sessionBean;
    
    protected List<Mensaje> mensajes;     
    protected Usuario usuario;
    protected Usuario amigoChat;
    protected String message;
    protected Mensaje mensaje;
    
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
    }
    
    public String doCrearChat(Usuario amigo) {
        this.setAmigoChat(amigo);
        this.mensajes = this.mensajeFacade.queryMensajesAmigos(usuario.getId(), amigoChat.getId());
        return "chat";
    }
    
    public String doEnviarMensaje(){
        return "chat";
    }
}
