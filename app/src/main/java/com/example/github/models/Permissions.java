
package com.example.github.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Permissions {

    @SerializedName("admin")
    @Expose
    private Boolean admin;
    @SerializedName("push")
    @Expose
    private Boolean push;
    @SerializedName("pull")
    @Expose
    private Boolean pull;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Permissions() {
    }

    /**
     * 
     * @param pull
     * @param admin
     * @param push
     */
    public Permissions(Boolean admin, Boolean push, Boolean pull) {
        super();
        this.admin = admin;
        this.push = push;
        this.pull = pull;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getPush() {
        return push;
    }

    public void setPush(Boolean push) {
        this.push = push;
    }

    public Boolean getPull() {
        return pull;
    }

    public void setPull(Boolean pull) {
        this.pull = pull;
    }

}
