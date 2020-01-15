package top.simpleito.jwtspringsecuritydemo;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
        password = BCrypt.hashpw(password, BCrypt.gensalt(10));

        User newUser = new User(users.size(),username, password);
        users.put(newUser.getUsername(), newUser);
    }

    public static User getUser(String username){
        System.out.println("查询了一次用户数据库");
        return users.get(username);
    }
    public static void loginSuccess(String username){
        System.out.println(username+"成功登录一次");
        users.get(username).loginTimes++;
    }

    public static class User{
        private Integer id;
        private String username;
        private String password;
        private int loginTimes;

        public User(){}
        public User(Integer id, String username, String password) {
            this.id = id;
            this.username = username;
            this.password = password;
            loginTimes = 0;
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

        public int getLoginTimes() {
            return loginTimes;
        }

        public void setLoginTimes(int loginTimes) {
            this.loginTimes = loginTimes;
        }
    }
}
