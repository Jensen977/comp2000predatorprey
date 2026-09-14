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
}