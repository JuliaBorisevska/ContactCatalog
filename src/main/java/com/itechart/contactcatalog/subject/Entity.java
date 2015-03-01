package com.itechart.contactcatalog.subject;

import java.io.Serializable;

public abstract class Entity implements Serializable, Cloneable {
    private int id;
    public Entity() {
    }
    public Entity(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        if (id != entity.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
