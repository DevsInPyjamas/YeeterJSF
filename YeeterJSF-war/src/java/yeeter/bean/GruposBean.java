/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import yeeterapp.entity.Grupo;

/**
 *
 * @author alec
 */
@Named(value = "gruposBean")
@SessionScoped
public class GruposBean implements Serializable {
@Inject YeeterSessionBean sessionBean;
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
    
    public boolean isAdmin() {
        return grupoSeleccionado.getIdCreador().equals(sessionBean.getLoggedUserObject());
    }
    
}
