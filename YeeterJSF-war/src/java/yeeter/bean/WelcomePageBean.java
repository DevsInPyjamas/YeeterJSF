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
import yeeterapp.ejb.PostFacade;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Post;
import yeeterapp.entity.Usuario;

/**
 *
 * @author alec
 */
@Named(value = "welcomePageBean")
@RequestScoped
public class WelcomePageBean {

    @EJB
    private PostFacade postFacade;

    @EJB
    private UsuarioFacade usuarioFacade;
    
    
    
    @Inject YeeterSessionBean sessionBean;
    @Inject ProfileBean profileBean;
    Usuario loggedUser;
    List<Post> listaPost;

    /**
     * Creates a new instance of WelcomePageBean
     */
    public WelcomePageBean() {
    }
    
    @PostConstruct
    public void init() {
        this.loggedUser = sessionBean.getLoggedUserObject();
        this.listaPost = this.usuarioFacade.queryUserFeed(loggedUser.getId());
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }

    public List<Post> getListaPost() {
        return listaPost;
    }

    public void setListaPost(List<Post> listaPost) {
        this.listaPost = listaPost;
    }

    public String distinguir(Post p) {
        String res;
         if (p.getIdGrupo() != null){
             res="Posted in group" + p.getIdGrupo().getNombre()+ "by @" + p.getIdAutor().getUsername();
         }else{
             res="@"+ p.getIdAutor().getUsername(); 
         }
         return res;
    }
    
   
    
    public String doRedirectToProfile() {
        profileBean.setUsuarioSeleccionado(loggedUser);
        this.profileBean.updateData();
        return "profilePanel";
    }
    
}
