package co.ichongwu.vidser.common.entity;

import java.util.Date;

import javax.persistence.Column;

public abstract class AbstractEntity {

    @Column(updatable = false)
    protected Long createUser = 0L;

    @Column(updatable = false)
    protected Date createTime;

    protected Long updateUser = 0L;

    protected Date updateTime;

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    
    public void init(Long user, Date time) {
        createUser = updateUser = user;
        createTime = updateTime = time;
    }
    
    public void update(Long user, Date time) {
        updateUser = user;
        updateTime = time;
    }

}
