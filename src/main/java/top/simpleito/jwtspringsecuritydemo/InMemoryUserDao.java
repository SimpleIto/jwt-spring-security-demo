package top.simpleito.jwtspringsecuritydemo;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDao {

    private static Map<String,User> users;

    static {
        users = new HashMap<>();
        insertUser("simpleito","admin");
        insertUser("jwtuser","jwtuser");
    }

    private static void insertUser(String username, String password){
        String salt = BCrypt.gensalt();
        password = BCrypt.hashpw(password,salt);

        User newUser = new User(users.size(),username, password);
        newUser.salt = salt;
        users.put(newUser.getUsername(), newUser);
    }

    public static User getUser(String username){
        return users.get(username);
    }

    public static class User{
        private Integer id;
        private String username;
        private String password;
        private String salt;

        public User(){}
        public User(Integer id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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
    }
}
