
package com.example.github.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PullRequest {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("html_url")
    @Expose
    private String htmlUrl;
    @SerializedName("diff_url")
    @Expose
    private String diffUrl;
    @SerializedName("patch_url")
    @Expose
    private String patchUrl;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PullRequest() {
    }

    /**
     * 
     * @param patchUrl
     * @param diffUrl
     * @param htmlUrl
     * @param url
     */
    public PullRequest(String url, String htmlUrl, String diffUrl, String patchUrl) {
        super();
        this.url = url;
        this.htmlUrl = htmlUrl;
        this.diffUrl = diffUrl;
        this.patchUrl = patchUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getDiffUrl() {
        return diffUrl;
    }

    public void setDiffUrl(String diffUrl) {
        this.diffUrl = diffUrl;
    }

    public String getPatchUrl() {
        return patchUrl;
    }

    public void setPatchUrl(String patchUrl) {
        this.patchUrl = patchUrl;
    }

}
