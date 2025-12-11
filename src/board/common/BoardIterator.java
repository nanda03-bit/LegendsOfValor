package board.common;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BoardIterator implements Iterator<BoardEntity> {
    private final List<BoardEntity> entities;
    private int currentIndex;
    
    public BoardIterator(List<BoardEntity> entities) {
        this.entities = entities;
        this.currentIndex = 0;
    }
    
    @Override
    public boolean hasNext() {
        while (currentIndex < entities.size()) {
            if (entities.get(currentIndex).isAlive()) {
                return true;
            }
            currentIndex++;
        }
        return false;
    }
    
    @Override
    public BoardEntity next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more entities");
        }
        return entities.get(currentIndex++);         
    }
                   
    @Override        
    public void remove() {
        throw new UnsupportedOperationException("Remove operation not supported");
    }
}       

                                     