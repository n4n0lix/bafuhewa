package de.orangestar.game;

import de.orangestar.engine.World;
import de.orangestar.engine.input.InputManager;
import de.orangestar.engine.input.Key.KeyState;
import de.orangestar.engine.input.Keyboard;
import de.orangestar.engine.logic.GameState;
import de.orangestar.game.gameobjects.Player;
import de.orangestar.game.gameobjects.WorldMap;

public class MainGameStateDummy extends GameState {
    
    @Override
    public void onStateStart() {
        // Gameobjects
    	
    	//worldMap.addChild(player);
    	
        //World.Get().addGameObject(player);
        World.Get().addGameObject(worldMap);
    }

    @Override
    public void onUpdate() {

    }

    @Override
    public void onStateEnd() {

    }
        
    
    private Player player = new Player();
    private WorldMap worldMap = new WorldMap();
}
