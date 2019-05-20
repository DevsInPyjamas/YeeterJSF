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
import yeeterapp.ejb.GrupoFacade;
import yeeterapp.ejb.PostFacade;
import yeeterapp.entity.Grupo;
import yeeterapp.entity.Post;

/**
 *
 * @author jesus
 */
@Named(value = "postBean")
@RequestScoped
public class PostBean {

    @EJB
    private GrupoFacade grupoFacade;
    
    @EJB
    private PostFacade postFacade;
    
    @Inject
    private YeeterSessionBean sessionBean;
    
    protected List<Grupo> grupos;
    protected int selectedGrupo;
    protected Post post;

    /**
     * Creates a new instance of PostBean
     */
    public PostBean() {
    }
    
    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public int getSelectedGrupo() {
        return selectedGrupo;
    }

    public void setSelectedGrupo(int selectedGrupo) {
        this.selectedGrupo = selectedGrupo;
    }
    
    @PostConstruct
    public void init() {
        grupos = this.sessionBean.getLoggedUserObject().getGrupoList1();
        post = new Post();
    }
    
    public String doNewPost() {
        if (selectedGrupo != -1){
            Grupo grupo = this.grupoFacade.find(this.selectedGrupo);
            this.post.setIdGrupo(grupo);
        }
        this.post.setIdAutor(this.sessionBean.getLoggedUserObject());
        Date date = new java.util.Date(System.currentTimeMillis());
        post.setFechaPublicacion(date);
        this.post.setFechaPublicacion(date);
        this.postFacade.create(post);
        return "welcomepage";
    }
}
