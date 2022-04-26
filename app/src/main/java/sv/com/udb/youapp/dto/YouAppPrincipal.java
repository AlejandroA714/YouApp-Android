package sv.com.udb.youapp.dto;

import java.time.LocalDate;
import java.sql.Date;

public class YouAppPrincipal {
   private static final long     serialVersionUID = 7389128175350348769L;
   private String                id;
   private String                nombres;
   private String                apellidos;
   private String                email;
   private String                username;
   //private Date                  birthday;
   //private Date                  registration;
   private Boolean               isActive;
   private OAuthRegistrationType registrationType;

   public YouAppPrincipal(String id, String nombres, String apellidos, String email, String username, Boolean isActive, OAuthRegistrationType registrationType) {
      this.id = id;
      this.nombres = nombres;
      this.apellidos = apellidos;
      this.email = email;
      this.username = username;
      //this.birthday = birthday;
      //this.registration = registration;
      this.isActive = isActive;
      this.registrationType = registrationType;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getNombres() {
      return nombres;
   }

   public void setNombres(String nombres) {
      this.nombres = nombres;
   }

   public String getApellidos() {
      return apellidos;
   }

   public void setApellidos(String apellidos) {
      this.apellidos = apellidos;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

//   public Date getBirthday() {
//      return birthday;
//   }
//
//   public void setBirthday(Date
//                                   birthday) {
//      this.birthday = birthday;
//   }
//
//   public Date getRegistration() {
//      return registration;
//   }
//
//   public void setRegistration(Date registration) {
//      this.registration = registration;
//   }

   public Boolean getActive() {
      return isActive;
   }

   public void setActive(Boolean active) {
      isActive = active;
   }

   public OAuthRegistrationType getRegistrationType() {
      return registrationType;
   }

   public void setRegistrationType(OAuthRegistrationType registrationType) {
      this.registrationType = registrationType;
   }
}
