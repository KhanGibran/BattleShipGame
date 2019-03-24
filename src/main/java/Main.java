import com.thoughtworks.game.Game;

public class Main
{
    public static void main(String[] args){
       try{
           Game game = new Game();
           game.startGame();
       }
       catch(Exception e)
       {
           System.out.println(e.getMessage());
           e.printStackTrace();
       }
    }
}
