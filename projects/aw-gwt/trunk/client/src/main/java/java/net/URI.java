package java.net;

public class URI {
	String uri;
	
	public URI(String uri) {
		this.uri = uri;
	}
	
    public static URI create(String str) {
        return new URI(str);
    }
    
    @Override
    public String toString() {
    	return uri;
    }

}
