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
import yeeterapp.ejb.ComentarioFacade;
import yeeterapp.ejb.PostFacade;
import yeeterapp.entity.Comentario;
import yeeterapp.entity.Post;

/**
 *
 * @author jugr9
 */
@Named(value = "comentarioBean")
@RequestScoped
public class ComentarioBean {

    @EJB
    private PostFacade postFacade;
    
    @EJB
    private ComentarioFacade comentarioFacade;
    
    @Inject private YeeterSessionBean sessionBean;
    @Inject private PostViewBean postViewBean;
    private Post postElegido;
    private Comentario com;
   
    /**
     * Creates a new instance of ComentarioBean
     */
    public ComentarioBean() {
    }

    public YeeterSessionBean getSessionBean() {
        return sessionBean;
    }

    public void setSessionBean(YeeterSessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public PostViewBean getPostViewBean() {
        return postViewBean;
    }

    public void setPostViewBean(PostViewBean postViewBean) {
        this.postViewBean = postViewBean;
    }
    
    public Comentario getCom() {
        return com;
    }

    public void setCom(Comentario com) {
        this.com = com;
    }

    public Post getPostElegido() {
        return postElegido;
    }

    public void setPostElegido(Post postElegido) {
        this.postElegido = postElegido;
    }
    
    @PostConstruct
    public void init(){
        postElegido = this.getPostViewBean().getPost();
        com = new Comentario();
    }
    
    public String createComment(){
        this.com.setAutor(sessionBean.getLoggedUserObject());
        this.com.setFechaPublicacion(new java.util.Date(System.currentTimeMillis()));
        this.com.setPost(this.getPostElegido());
        this.postElegido.getComentarioList().add(com);
        comentarioFacade.create(com);
        List<Comentario> comments = postViewBean.getPost().getComentarioList();
        comments.add(com);
        postViewBean.getPost().setComentarioList(comments);
        this.postFacade.edit(postViewBean.getPost());
        this.postViewBean.init();
        return "post";
    }
}
