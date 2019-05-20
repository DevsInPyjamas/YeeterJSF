/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Usuario;

/**
 *
 * @author alec
 */
@Named(value = "yeeterLoginBean")
@RequestScoped
public class YeeterLoginBean {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    private String email;
    private String password;
    private String error;
    private String messageRegister;
    
    
    
    /**
     * Creates a new instance of YeeterLoginBean
     */
    public YeeterLoginBean() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessageRegister() {
        return messageRegister;
    }

    public void setMessageRegister(String messageRegister) {
        this.messageRegister = messageRegister;
    }
    
    
    public String doLogin() {
        if(email == null || email.isEmpty()) {
            this.error = "Email vacío";
            return "login";
        }
        if(password == null || password.isEmpty()){
            this.error = "Contraseña vacía";
            return "login";
        }
        Usuario tryingToLog = this.usuarioFacade.queryUserByEmail(email);
        if(tryingToLog == null || !tryingToLog.getPassword().equals(password)) {
            this.error = "Combinación de email o contraseña errónea";
            return "login";
        }
        
        return "welcomepage";
    }
    
    public boolean isEmailError() {
        return error != null && error.contains("Email");
    }
    
    public boolean isPasswordError() {
        return error != null && error.contains("Contraseña");
    }
    
}
