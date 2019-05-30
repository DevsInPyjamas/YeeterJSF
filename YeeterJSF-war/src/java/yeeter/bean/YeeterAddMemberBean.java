/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.util.ArrayList;
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
 * @author alec
 */
@Named(value = "yeeterAddMemberBean")
@RequestScoped
public class YeeterAddMemberBean {

    @EJB
    private UsuarioFacade usuarioFacade;

    @EJB
    private GrupoFacade grupoFacade;
    
    @Inject YeeterSessionBean sessionBean;
    @Inject GruposBean groupsBean;
    
    
    
    private List<Usuario> friendsNotInGroup;

    /**
     * Creates a new instance of YeeterAddMemberBean
     */
    public YeeterAddMemberBean() {
    }

    public List<Usuario> getFriendsNotInGroup() {
        return friendsNotInGroup;
    }

    public void setFriendsNotInGroup(List<Usuario> friendsNotInGroup) {
        this.friendsNotInGroup = friendsNotInGroup;
    }
    
    
    
    @PostConstruct
    public void init() {
        this.friendsNotInGroup = new ArrayList<>();
        this.sessionBean.getLoggedUserObject().getUsuarioList().forEach(amigo -> {
            if(!groupsBean.getGrupoSeleccionado().getUsuarioList().contains(amigo)) friendsNotInGroup.add(amigo);
        });
    }
    
    public String doAddMember(Usuario user) {
        Grupo g = this.groupsBean.getGrupoSeleccionado();
        g.getUsuarioList().add(user);
        user.getGrupoList().add(g);
        this.usuarioFacade.edit(user);
        this.grupoFacade.edit(g);
        this.init();
        return null;
    }
    
}
