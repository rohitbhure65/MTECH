package com.example.util;

/** Tiny helper so every servlet prints the same page layout. */
public final class Html {
    private Html() { }

    /** Escape user text before printing it (prevents XSS). */
    public static String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    public static String page(String title, String body) {
        return "<!DOCTYPE html><html><head><meta charset=\"UTF-8\"><title>" + esc(title) + "</title>"
             + "<style>body{font-family:Arial;margin:30px;max-width:720px}"
             + "input,select{padding:6px;margin:4px 0}table{border-collapse:collapse}"
             + "td,th{border:1px solid #999;padding:6px 10px}.err{color:#b00020}.ok{color:#0a7d2c}</style>"
             + "</head><body><h2>" + esc(title) + "</h2>" + body + "</body></html>";
    }
}
