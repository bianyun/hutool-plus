package plus.hutool.extra.test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 用于单元测试的 测试用户
 *
 * @author bianyun
 * @date 2022/12/15
 */
@SuppressWarnings("JavadocDeclaration")
public class TestUser {
    private Long id;
    private String name;
    private LocalDate birthday;
    private Date createdTime;
    private LocalDateTime lastLoginTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }
}
