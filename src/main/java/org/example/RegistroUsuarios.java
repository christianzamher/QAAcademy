package org.example;

public class RegistroUsuarios {
    private String name;
    private String phone;
    private String email;
    private String discord;
    private String ign;
    private int    level;
    private String mainRol;
    private String secRol;
    private String country;


    public RegistroUsuarios(String name, String phone, String email, String discord , String ign , int level , String mainRol , String secRol , String country) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.discord = discord;
        this.ign = ign;
        this.level = level;
        this.mainRol = mainRol;
        this.secRol = secRol;
        this.country = country;

    }

    //Name:
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    //Phone:
    public String getPhone(){
        return phone;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    //Email:
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    //Discord:
    public String getDiscord(){
        return discord;
    }
    public void setDiscord(String discord){
        this.discord = discord;
    }

    //IGN
    public String getIgn(){
        return ign;
    }
    public void setIgn(String ign){
        this.ign = ign;
    }

    //Level:
    public int getLevel(){
        return level;
    }
    public void setLevel(int level){
        this.level = level;
    }

    //Main Rol:
    public String getMainRol(){
        return mainRol;
    }
    public void setMainRol(String mainRol){
        this.mainRol = mainRol;
    }

    //secRol:
    public String getSecRol(){
        return secRol;
    }
    public void setSecRol( String secRol){
        this.secRol = secRol;
    }

    //Country:
    public String getCountry(){
        return country;
    }
    public void setCountry(String country){
        this.country = country;
    }


}
