package models;

public class PostFoto {
    long id;
    String uri;

    public PostFoto(long id, String uri, int rendi) {
        this.id = id;
        this.uri = uri;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
