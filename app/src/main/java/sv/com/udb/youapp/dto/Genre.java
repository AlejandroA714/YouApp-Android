package sv.com.udb.youapp.dto;

public class Genre {

   private Integer    id;
   private String     title;


   public Genre(Integer id, String title) {
      this.id = id;
      this.title = title;
   }

   public Genre(String title) {
      this.title = title;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }
}
