/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import yeeterapp.entity.Usuario;

/**
 *
 * @author jesus
 */
@Named(value = "chatBean")
@RequestScoped
public class ChatBean {
    @Inject
    private YeeterSessionBean sessionBean;
    
    private Usuario usuario;
    /**
     * Creates a new instance of ChatBean
     */
    public ChatBean() {
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
}
