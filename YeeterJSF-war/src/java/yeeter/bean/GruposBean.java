/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import yeeterapp.ejb.GrupoFacade;
import yeeterapp.entity.Grupo;
import yeeterapp.entity.Post;

/**
 *
 * @author jugr9
 */
@Named(value = "gruposBean")
@RequestScoped
public class GruposBean implements Serializable {

    @EJB
    private GrupoFacade grupoFacade;

    @Inject YeeterSessionBean sessionBean;
    @Inject PostViewBean postViewBeen;
    List<Grupo> listaGrupos;
    Grupo grupoSeleccionado;
    /**
     * Creates a new instance of GruposBean
     */
    public GruposBean() {
    }

    public List<Grupo> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<Grupo> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public Grupo getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    public void setGrupoSeleccionado(Grupo grupoSeleccionado) {
        this.grupoSeleccionado = grupoSeleccionado;
    }

    @PostConstruct
    public void init(){
        listaGrupos = sessionBean.getLoggedUserObject().getGrupoList();
    }

    public String chooseGroup(Grupo grupo){
        this.setGrupoSeleccionado(grupo);
        return "grupo";
    }

    public String choosePost(Post post){
        this.postViewBeen.setPost(post);
        return "post";
    }

    
}
