/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import yeeterapp.entity.Post;

/**
 *
 * @author jugr9
 */
@Named(value = "postBean")
@RequestScoped
public class PostBean {

    @Inject YeeterSessionBean sessionBean;
    Post post;
    
    public PostBean() {
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
    
    public String choosePost(Post post){
        this.setPost(post);
        return "post";
    }
    
    public boolean isPublic(){
        return this.post.getIdGrupo() == null;
    }
}
