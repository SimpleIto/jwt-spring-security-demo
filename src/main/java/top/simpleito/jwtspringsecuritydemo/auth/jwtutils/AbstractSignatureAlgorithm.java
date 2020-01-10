package top.simpleito.jwtspringsecuritydemo.auth.jwtutils;

public abstract class AbstractSignatureAlgorithm implements SignatureAlgorithm {
    protected String jwtAlgName;

    protected AbstractSignatureAlgorithm(String jwtAlgName){
        this.jwtAlgName = jwtAlgName;
    }

    @Override
    public String getJwtAlgName() {
        return jwtAlgName;
    }

}
