package ohtu.services;

import ohtu.domain.User;
import ohtu.data_access.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationService {

    private UserDao userDao;

    @Autowired
    public AuthenticationService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean logIn(String username, String password) {
        for (User user : userDao.listAll()) {
            if (check(user, username, password)) {
                return true;
            }
        }

        return false;
    }
    
    private boolean check(User user, String name, String password) {
        return user.getUsername().equals(name) && user.getPassword().equals(password);
    }

    public boolean createUser(String username, String password) {
        if (userDao.findByName(username) != null) {
            return false;
        }

        if (invalid(username, password)) {
            return false;
        }

        userDao.add(new User(username, password));

        return true;
    }

    private boolean invalid(String username, String password) {
        // validity check of username and password
        if (username.length() < 3 || password.length() < 8) {
            return true;
        }
        
        return !username.matches("[a-zA-Z]+") || password.matches("[a-zA-Z]+");
    }
}
