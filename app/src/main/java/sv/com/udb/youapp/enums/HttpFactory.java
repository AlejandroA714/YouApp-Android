package sv.com.udb.youapp.enums;

public enum HttpFactory {

    AUTHENTICATION{
        @Override
        public String getHost() {
            return AUTHENTICATION.format(BASE_URL,AUTHENTICATION_PORT);
        }
    },STORAGE{
        @Override
        public String getHost() {
            return STORAGE.format(BASE_URL,STORAGE_PORT);
        }
    };

    private static final String BASE_URL = "http://192.168.101.17";
    private static final String AUTHENTICATION_PORT = "8083";
    private static final String STORAGE_PORT = "8085";
    public abstract String getHost();

    private String format(String baseUrl,String port){
        return String.format("%s:%s/",baseUrl,port);
    }
}
