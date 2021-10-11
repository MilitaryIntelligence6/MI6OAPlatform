package cn.misection.oaplatform.approval.entity;

/**
 * @author Military Intelligence 6 root
 * @version 1.0.0
 * @ClassName TeacherUser
 * @Description TODO
 * @CreateTime 2021年10月08日 19:40:00
 */
public class TeacherUser {

    private String username;

    private String password;

    public TeacherUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public TeacherUser() {
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
