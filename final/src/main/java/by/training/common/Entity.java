package by.training.common;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    protected long id;

    public Entity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
