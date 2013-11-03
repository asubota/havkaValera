package com.havkavalera.app;

public class ConnectionInfo {
    public final static String HOST = "havka.sona-studio.com";
    public final static int PORT = 0;

    public static String getHttpHostAddress() {
        return "http://" + HOST + (PORT != 0 ? ":" + PORT : "");
    }
}
