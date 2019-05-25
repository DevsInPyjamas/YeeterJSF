/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import yeeterapp.entity.Usuario;

/**
 *
 * @author alec
 */
@Named(value = "yeeterMembersListBean")
@RequestScoped
public class yeeterMembersListBean {

    
    private List<Usuario> listMembers;
    /**
     * Creates a new instance of yeeterMembersListBean
     */
    public yeeterMembersListBean() {
    }
    
    @PostConstruct
    public void init() {
        
    }
    
}
