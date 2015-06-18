package de.orangestar.game.gameobjects;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import de.orangestar.engine.GameObject;
import de.orangestar.engine.debug.DebugManager;
import de.orangestar.engine.logic.component.UnitLogicComponent;
import de.orangestar.engine.physics.component.UnitPhysicsComponent;
import de.orangestar.engine.tools.pathfinding.AStarSearch;
import de.orangestar.engine.tools.pathfinding.Pathfinding;
import de.orangestar.engine.utils.Pair;
import de.orangestar.engine.values.Vector3f;
import de.orangestar.game.gameobjects.map.MapChunk;

public class PlayerLogicComponent extends UnitLogicComponent {

    public static final float MOVE_SPEED = 20f;
    
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                               Public                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    
    public PlayerLogicComponent(GameObject obj, UnitPhysicsComponent physics) {
		super(obj);
		_physics = physics;

        _pathFinder = new AStarSearch();
	}

	@Override
    public void onUpdate() {
        super.onUpdate();
        
        handleAIMovement();
    }
	
	/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    /*                              Private                               */
    /*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
	
	private void handleAIMovement() {
        Vector3f position = getGameObject().getLocalTransform().position;

        int x = (int) position.x / MapChunk.TILE_SIZE + 16;
        int y = (int) position.y / MapChunk.TILE_SIZE + 16;
        
        if (_currentPath == null || _currentPath.isEmpty()) {
            // Constantly generate test data
            boolean[][] world = new boolean[MapChunk.CHUNK_SIZE][MapChunk.CHUNK_SIZE];
            for(int i = 0; i < world.length; i++) {
                for(int p = 0; p < world[0].length; p++) {
                    world[i][p] = true;
                }
            }
            
            Random rnd = new Random();
            _currentPath = new LinkedList<>(_pathFinder.findPath(world, x, y, rnd.nextInt(32), rnd.nextInt(32)));
        }
        
        // Check if we arrived our current target location
        if (_currentTarget != null && x == _currentTarget.x && y == _currentTarget.y) {
            DebugManager.Get().debug(PlayerLogicComponent.class, "Player AI reached target location (" + x + "/" + y + ")");
            _currentTarget = null;
        }
        
        // Try to get a new target location if we haven't one
        if (_currentTarget == null && !_currentPath.isEmpty()) {
            _currentTarget = _currentPath.poll();
            DebugManager.Get().debug(PlayerLogicComponent.class, "Player AI new target location (" + _currentTarget.x + "/" + _currentTarget.y + ")");
        }
        
        // Do movement into the direction of the current target
        if (_currentTarget != null) {
            if (x < _currentTarget.x) {
                _physics._velocityX += MOVE_SPEED;
            }
            
            if (x > _currentTarget.x) {
                _physics._velocityX -= MOVE_SPEED;
            }
            
            if (y < _currentTarget.y) {
                _physics._velocityY += MOVE_SPEED;
            }
            
            if (y > _currentTarget.y) {
                _physics._velocityY -= MOVE_SPEED;
            }
        }
	}

    private Pathfinding                    _pathFinder;
	private Pair<Integer,Integer>          _currentTarget;
	private Queue<Pair<Integer,Integer>>   _currentPath;

	private UnitPhysicsComponent _physics;
}
