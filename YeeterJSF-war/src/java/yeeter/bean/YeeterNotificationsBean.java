/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import yeeterapp.ejb.NotificacionesFacade;
import yeeterapp.ejb.PeticionAmistadFacade;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Notificaciones;
import yeeterapp.entity.PeticionAmistad;
import yeeterapp.entity.PeticionAmistadPK;
import yeeterapp.entity.Usuario;

/**
 *
 * @author alec
 */
@Named(value = "yeeterNotificationsBean")
@RequestScoped
public class YeeterNotificationsBean {

    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private PeticionAmistadFacade peticionAmistadFacade;

    @EJB
    private NotificacionesFacade notificacionesFacade;
        
    
    @Inject private YeeterSessionBean yeeterSessionBean;

    private List<Notificaciones> notificationsList;
    private List<PeticionAmistad> peticionesAmistadList;
    private Usuario loggedUser;
    private long notRead;
    private String message;
    
    /**
     * Creates a new instance of YeeterNotificationsBean
     */
    public YeeterNotificationsBean() {
    }

    public List<Notificaciones> getNotificationsList() {
        return notificationsList;
    }

    public void setNotificationsList(List<Notificaciones> notificationsList) {
        this.notificationsList = notificationsList;
    }

    public List<PeticionAmistad> getPeticionesAmistadList() {
        return peticionesAmistadList;
    }

    public void setPeticionesAmistadList(List<PeticionAmistad> peticionesAmistadList) {
        this.peticionesAmistadList = peticionesAmistadList;
    }

    public long getNotRead() {
        return notRead;
    }

    public void setNotRead(long notRead) {
        this.notRead = notRead;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    @PostConstruct
    public void init() {
        this.loggedUser = yeeterSessionBean.getLoggedUserObject();
        this.notificationsList = loggedUser.getNotificacionesList();
        this.peticionesAmistadList = loggedUser.getPeticionAmistadList1();
        this.notRead = notificationsList.stream().filter(notificacion -> 
                !notificacion.getNotificacionLeida()).count() + peticionesAmistadList.size();
        this.message = "XD";
    }
    
    public String doReadAllNotifications() {
        List<Notificaciones> notificaciones = 
                    loggedUser.getNotificacionesList().stream().filter(x -> !x.getNotificacionLeida()).collect(Collectors.toList());
            notificaciones.forEach((not) -> {
                not.setNotificacionLeida(true);
                notificacionesFacade.edit(not);
            });
        this.init();
        return null;
    }

    public String doReadNotification(int id) {
        Notificaciones notificacion = notificacionesFacade.find(id);
        notificacion.setNotificacionLeida(true);
        notificacionesFacade.edit(notificacion);
        this.init();
        return null;
    }
    
    public String doAcceptFriendRequest(PeticionAmistadPK id) {
        PeticionAmistad pa = peticionAmistadFacade.find(id);
        
        Usuario profileUser = usuarioFacade.find(id);
        
        List<Usuario> amigos = loggedUser.getUsuarioList1();
        amigos.add(profileUser);
        usuarioFacade.edit(loggedUser);
        amigos = profileUser.getUsuarioList1();
        amigos.add(loggedUser);
        usuarioFacade.edit(profileUser);
        
        peticionAmistadFacade.remove(pa);
        this.message = "Peticion aceptada";
        this.init();
        return null;
    }
    
    public String doRedirectToSenderProfile(int idUsuario) {
        return "";
    }
}
