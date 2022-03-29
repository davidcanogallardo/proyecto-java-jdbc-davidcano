package model;

import java.io.IOException;
import java.util.HashMap;

public interface Persistable <T> {
    public abstract T add(T obj);
    public abstract T delete(T id);
    public abstract T get(Integer id);
    public abstract HashMap<Integer, T> getMap();
    public abstract void load() throws IOException;
    public abstract void save() throws IOException;
}
