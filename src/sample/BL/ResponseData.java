package sample.BL;

import java.util.ArrayList;

public class ResponseData {
    private boolean status;
    private float code;
    private String msg;
    private String domain;
    Response ResponseObject;


    // Getter Methods

    public boolean getStatus() {
        return status;
    }

    public float getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getDomain() {
        return domain;
    }

    public Response getResponse() {
        return ResponseObject;
    }

    // Setter Methods

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCode(float code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setResponse(Response responseObject) {
        this.ResponseObject = responseObject;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "status=" + status +
                ", code=" + code +
                ", msg='" + msg + '\'' +
                ", domain='" + domain + '\'' +
                ", ResponseObject=" + ResponseObject +
                '}';
    }
}

class Response {
    private String title;
    private String thumbnail;

    public ArrayList<Object> getLinks() {
        return links;
    }

    public void setLinks(ArrayList<Object> links) {
        this.links = links;
    }

    private ArrayList<Object> links = new ArrayList<Object>();


    // Getter Methods

    public String getTitle() {
        return title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    // Setter Methods

    public void setTitle(String title) {
        this.title = title;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Response{" +
                "title='" + title + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", links=" + links +
                '}';
    }
}