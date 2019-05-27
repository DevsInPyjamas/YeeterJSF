/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import yeeterapp.entity.Usuario;

/**
 *
 * @author alec
 */
@Named(value = "yeeterAddMemberBean")
@RequestScoped
public class YeeterAddMemberBean {
    
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
            if(!groupsBean.grupoSeleccionado.getUsuarioList().contains(amigo)) friendsNotInGroup.add(amigo);
        });
    }
    
}
