package com.example.havkavalera;

public class ConnectionInfo {
    public final static String HOST = "havka.sona-studio.com";
    public final static int PORT = 3000;

    public static String getHttpHostAddress() {
        return "http://" + HOST + (PORT != 0 ? ":" + PORT : "") + "/";
    }

    public static String getSecureHttpHostAddress() {
        return "https://" + HOST + (PORT != 0 ? ":" + PORT : "") + "/";
    }
}
