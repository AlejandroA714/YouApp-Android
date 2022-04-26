package sv.com.udb.youapp.dto;

import java.io.Serializable;

import sv.com.udb.youapp.dto.enums.IOAuthRegistrationType;

public class OAuthRegistrationType implements Serializable {
   private Integer                id;

   private IOAuthRegistrationType name;

   public OAuthRegistrationType(IOAuthRegistrationType name) {
      this.name = name;
   }

   public OAuthRegistrationType(Integer id, IOAuthRegistrationType name) {
      this.id = id;
      this.name = name;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public IOAuthRegistrationType getName() {
      return name;
   }

   public void setName(IOAuthRegistrationType name) {
      this.name = name;
   }
}

