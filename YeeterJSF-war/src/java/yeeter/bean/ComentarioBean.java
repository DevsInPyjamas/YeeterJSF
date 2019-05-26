/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import yeeterapp.ejb.ComentarioFacade;
import yeeterapp.entity.Comentario;

/**
 *
 * @author jugr9
 */
@Named(value = "comentarioBean")
@RequestScoped
public class ComentarioBean {
    
    @EJB
    private ComentarioFacade comentarioFacade;
    
    @Inject private YeeterSessionBean sessionBean;
    @Inject private PostViewBean postViewBean;
    Comentario com;
    /**
     * Creates a new instance of ComentarioBean
     */
    public ComentarioBean() {
    }

    public Comentario getCom() {
        return com;
    }

    public void setCom(Comentario com) {
        this.com = com;
    }
    
    @PostConstruct
    public void init(){
        com = new Comentario();
    }
    
    public String createComment(){
        this.com.setAutor(sessionBean.getLoggedUserObject());
        this.com.setFechaPublicacion(new java.util.Date(System.currentTimeMillis()));
        this.com.setPost(postViewBean.post);
        comentarioFacade.create(com);
        
        return "post";
    }
}
