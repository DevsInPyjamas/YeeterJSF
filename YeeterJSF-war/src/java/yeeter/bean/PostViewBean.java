/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import yeeterapp.entity.Post;

/**
 *
 * @author jugr9
 */
@Named(value = "postViewBean")
@RequestScoped
public class PostViewBean {
    
    protected Post post;
    
    /**
     * Creates a new instance of PostViewBean
     */
    public PostViewBean() {
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    
    public String choosePost(Post p){
        this.setPost(p);
        return "post";
    }

    public boolean isPublic(){
        return this.post.getIdGrupo() == null;
    }
    
    public String distinguir() {
        String res;
         if (post.getIdGrupo() != null){
             res="Posted in group" + post.getIdGrupo().getNombre()+ "by @" + post.getIdAutor().getUsername();
         }else{
             res="@"+ post.getIdAutor().getUsername(); 
         }
         return res;
    }
}
