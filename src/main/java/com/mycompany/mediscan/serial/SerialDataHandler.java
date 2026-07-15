package com.mycompany.mediscan.serial;


/**
 * @param <T>
 */
public interface SerialDataHandler<T> {
    void onDataReceived(T data);
}
