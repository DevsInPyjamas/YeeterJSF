/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import yeeterapp.ejb.UsuarioFacade;
import yeeterapp.entity.Usuario;

/**
 *
 * @author alec
 */
@Named(value = "yeeterRegisterBean")
@RequestScoped
public class YeeterRegisterBean {

    @EJB
    private UsuarioFacade usuarioFacade;
    
    @Inject private YeeterLoginBean loginBean;
    
    private String lastname, name, bio, email, errorMessage, password1, password2, username;
    
    private Date birth;
    
    private boolean passwordError, usernameError, emailError;
    

    /**
     * Creates a new instance of YeeterRegisterBean
     */
    public YeeterRegisterBean() {
        passwordError = usernameError = emailError = false;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String beforeErrorLastname) {
        this.lastname = beforeErrorLastname;
    }

    public String getName() {
        return name;
    }

    public void setName(String beforeErrorName) {
        this.name = beforeErrorName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String beforeErrorBio) {
        this.bio = beforeErrorBio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String beforeErrorEmail) {
        this.email = beforeErrorEmail;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public boolean isPasswordError() {
        return passwordError;
    }

    public void setPasswordError(boolean passwordError) {
        this.passwordError = passwordError;
    }

    public boolean isUsernameError() {
        return usernameError;
    }

    public void setUsernameError(boolean usernameError) {
        this.usernameError = usernameError;
    }

    public boolean isEmailError() {
        return emailError;
    }

    public void setEmailError(boolean emailError) {
        this.emailError = emailError;
    }
    
    
    public String doRegister() {
        if(this.usuarioFacade.queryUserByUsername(username) != null) {
            errorMessage = "Username already in use";
            usernameError = true;
            return null;
        }
        if(checkIfUsernameIsInvalid(username)) {
            errorMessage = "User contains less than 6 words or invalid characters";
            usernameError = true;
            return null;
        }
        if(!emailIsAvailable(email)) {
            errorMessage = "Email already in use";
            emailError = true;
            return null;
        }
        if(!password1.equals(password2)) {
            errorMessage = "Passwords doesn't match";
            passwordError = true;
            return null;
        }
        if(password1.length() < 6) {
            errorMessage = "Passwords too short";
            passwordError = true;
            return null;
        }
        
        Usuario user = new Usuario();
        
        
        user.setUsername(username);
        user.setPassword(password1);
        user.setBiografia(bio);
        user.setNombre(name);
        user.setFechaNacimiento(birth);
        user.setApellidos(lastname);
        user.setCorreo(email);
        
        this.usuarioFacade.create(user);
        this.loginBean.setMessageRegister("Register complete");
        return "login";
    }
    
    private boolean checkIfUsernameIsInvalid(String username) {
        Pattern pattern = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(username);
        return username.length() < 5 || matcher.find();
    }
    
    private boolean emailIsAvailable(String email) {
        return usuarioFacade.queryUserByEmail(email) == null;
    }
    
}
