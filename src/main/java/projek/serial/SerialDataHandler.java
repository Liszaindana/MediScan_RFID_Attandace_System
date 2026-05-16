package projek.serial;

public interface SerialDataHandler<T> {
    void onDataReceived(T data);
}
