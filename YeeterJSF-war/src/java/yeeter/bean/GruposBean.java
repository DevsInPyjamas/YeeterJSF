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
    List<Grupo> listaGrupos;
    Grupo grupoSeleccionado;
    Grupo nuevoGrupo;

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

    public Grupo getNuevoGrupo() {
        return nuevoGrupo;
    }

    public void setNuevoGrupo(Grupo nuevoGrupo) {
        this.nuevoGrupo = nuevoGrupo;
    }
    
    @PostConstruct
    public void init(){
        listaGrupos = sessionBean.getLoggedUserObject().getGrupoList();
        nuevoGrupo = new Grupo();
    }
    
    public String chooseGroup(Grupo grupo){
        this.setGrupoSeleccionado(grupo);
        return "grupo";
    }
    
    public String doCrearGrupo() {
        this.nuevoGrupo.setIdCreador(sessionBean.getLoggedUserObject());
        Date date = new java.util.Date(System.currentTimeMillis());
        this.nuevoGrupo.setFechaCreacion(date);
        this.grupoFacade.create(nuevoGrupo);
        this.init();
        return "listagrupos";
    }
}
