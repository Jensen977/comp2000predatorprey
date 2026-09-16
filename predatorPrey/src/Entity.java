public class Entity {
   private int x;
   private int y; 
   private final boolean isFood;

   public Entity(int x, int y, boolean isFood) {
      this.x = x;
      this.y = y;
      this.isFood = isFood;
   }

   public int getX(){
      return x;
   }

   public int getY(){
      return y;
   }

   public boolean isFood(){
      return isFood;
   }

   public void setX(int x){
      this.x = x;
   }

   public void setY(int y){
      this.y = y;
   }

   public double distanceTo(Entity other) {
      return Math.hypot(x - other.x, y - other.y);
   }

   public void keepInside(int width, int height) {
      if (width < 1 || height < 1) {
         throw new IllegalArgumentException("World dimensions must be positive"); 
      }
      x = Math.max(0, Math.min(width - 1, x));
      y = Math.max(0, Math.min(height - 1, y));
   }
}