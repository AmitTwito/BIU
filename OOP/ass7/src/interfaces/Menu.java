package interfaces;

import biuoop.DrawSurface;

public interface Menu<T> extends Animation {
    void addSelection(String key, String message, T returnVal);

    T getStatus();
}
