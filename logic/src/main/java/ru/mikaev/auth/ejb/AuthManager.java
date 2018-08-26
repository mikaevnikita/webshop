package ru.mikaev.auth.ejb;


import org.apache.commons.lang3.StringUtils;
import ru.mikaev.auth.domain.Credentials;
import ru.mikaev.auth.domain.User;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class AuthManager {
    @PersistenceContext(unitName = "webshopPU")
    private EntityManager entityManager;

    public User.Role doLogin(String username, String password){
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return null;
        }

        Credentials credentials = entityManager.find(Credentials.class, username);
        if(credentials == null){
            return null;
        }

        if(!password.equals(credentials.getPassword())){
            return null;
        }

        User user = credentials.getUser();
        if(user == null){
            return null;
        }

        return user.getRole();
    }
}
