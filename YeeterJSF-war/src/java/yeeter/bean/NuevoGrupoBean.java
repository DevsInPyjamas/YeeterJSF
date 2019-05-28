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
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Grupo;
import yeeterapp.entity.Usuario;

/**
 *
 * @author jesus
 */
@Named(value = "nuevoGrupoBean")
@RequestScoped
public class NuevoGrupoBean {
    @EJB
    private GrupoFacade grupoFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    
    @Inject YeeterSessionBean sessionBean;
    @Inject GruposBean gruposBean;
    protected Usuario loggedUser;
    protected Grupo nuevoGrupo;
    /**
     * Creates a new instance of NuevoGrupoBean
     */
    public NuevoGrupoBean() {
    }
    
    @PostConstruct
    public void init(){
        loggedUser = sessionBean.getLoggedUserObject();
        nuevoGrupo = new Grupo();
    }

    public Grupo getNuevoGrupo() {
        return nuevoGrupo;
    }

    public void setNuevoGrupo(Grupo nuevoGrupo) {
        this.nuevoGrupo = nuevoGrupo;
    }

    public Usuario getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Usuario loggedUser) {
        this.loggedUser = loggedUser;
    }
    
    public String doCrearGrupo() {
        this.nuevoGrupo.setIdCreador(loggedUser);
        Date date = new java.util.Date(System.currentTimeMillis());
        this.nuevoGrupo.setFechaCreacion(date);
        this.grupoFacade.create(nuevoGrupo);
        List<Grupo> grupos = this.loggedUser.getGrupoList();
        grupos.add(nuevoGrupo);
        this.loggedUser.setGrupoList(grupos);
        this.usuarioFacade.edit(loggedUser);
        this.gruposBean.init();     
        return "listagrupos";
    }
}
