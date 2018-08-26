package ru.mikaev.auth;

import org.apache.commons.lang3.StringUtils;
import ru.mikaev.auth.domain.User;
import ru.mikaev.auth.ejb.AuthManager;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.io.IOException;
import java.io.Serializable;

@Named
@SessionScoped
public class AuthBean implements Serializable {
    private User.Role role;

    private String username;
    private String password;

    private String requestedPage;

    public String getRequestedPage() {
        return requestedPage;
    }

    public void setRequestedPage(String requestedPage) {
        this.requestedPage = requestedPage;
    }

    @EJB
    AuthManager authManager;

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void doLogin() throws IOException {
        role = authManager.doLogin(username, password);
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        if(role != null){
            externalContext.redirect(requestedPage);
        }
    }
}
