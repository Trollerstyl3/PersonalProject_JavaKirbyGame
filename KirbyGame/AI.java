/**
 * AI
 */
public class AI {
     
    public void movement(Enemy enemy, Player player){
        if((Math.abs(enemy.getX()-player.getX()) < 100 || Math.abs(player.getX()-enemy.getX()) < 100) && enemy.states.contains(CharacterState.GROUNDED) && enemy.getY() == player.getY()){
        
            enemy.attack= true;

        }else if(enemy.getX() < player.getX()){
            enemy.attack = false;
            enemy.moveRight = true;
            enemy.moveLeft = false;
        }else if(enemy.getX() > player.getX()){
            enemy.moveLeft = true;
            enemy.moveRight = false;
            enemy.attack = false;
        }

        if(Math.abs(enemy.getX()-player.getX()) < 30 && enemy.getY() < player.getY() && !enemy.states.contains(CharacterState.GROUNDED)){
        
            enemy.moveDown = true;
            enemy.moveUp = false;

        }

        if(enemy.getY() > player.getY()+200 && enemy.states.contains(CharacterState.GROUNDED)){
            enemy.moveUp = true;
            enemy.moveDown = false;
        }else if(enemy.states.contains(CharacterState.GROUNDED)){
            enemy.moveUp = false;
            enemy.moveDown = false;
        }
    }
}