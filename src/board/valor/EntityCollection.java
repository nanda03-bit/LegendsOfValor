/**
 * Filename: EntityCollection.java
 * Author: Nandana Shashi
 * Date: 2025-Dec
 * Description: Collection class for board entities that implements Iterable for easy traversal.
 */

package board.valor;

import board.common.BoardEntity;
import board.common.BoardIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityCollection implements Iterable<BoardEntity> {
    private final List<BoardEntity> entities;
    
    public EntityCollection() {
        this.entities = new ArrayList<>();
    }
    
    public void add(BoardEntity entity) {
        entities.add(entity);
    }
    
    public void remove(BoardEntity entity) {
        entities.remove(entity);
    }
    
    public List<BoardEntity> getEntities() {
        return new ArrayList<>(entities);
    }
    
    public List<BoardEntity> getAliveEntities() {
        List<BoardEntity> alive = new ArrayList<>();
        for (BoardEntity entity : entities) {
            if (entity.isAlive()) {
                alive.add(entity);
            }
        }
        return alive;
    }
    
    public int size() {
        return entities.size();
    }
    
    public boolean isEmpty() {
        return entities.isEmpty();
    }
    
    public void clear() {
        entities.clear();
    }
    
    @Override
    public Iterator<BoardEntity> iterator() {
        return new BoardIterator(entities);
    }
}

