package ipcamera;

public class IPMutex {

    private boolean open;
    
    IPMutex() {
        open = false;
    }
    
    public synchronized boolean isOpen() {
        return open;
    }

    public synchronized void close() {
        open = false;
    }

    public synchronized void open() {
        open = true;
    }
}
