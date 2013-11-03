package com.havkavalera.app;

public class ConnectionInfo {
    public final static String HOST = "10.1.11.102";
    public final static int PORT = 3000;

    public static String getHttpHostAddress() {
        return "http://" + HOST + (PORT != 0 ? ":" + PORT : "");
    }
}
