package chapter7;

import java.util.Date;
import java.util.Locale;

public class C7P199_CacheControl {
     private Date maxAge = null;
    private Date sMaxAge = null;
    private boolean mustRevalidate = false;
    private boolean noCache = false;
    private boolean noStore = false;
    private boolean proxyRevalidate = false;
    private boolean publicCache = false;
    private boolean privateCache = false;

    public C7P199_CacheControl(String s) {
        if (s == null) {
            return;
        }

        String[] components = s.split(",");

        Date now = new Date();
        for (String component : components) {
            component = component.trim().toLowerCase(Locale.CHINA);
            if (component.startsWith("max-age=")) {
                int sec = Integer.parseInt(component.substring(8));
                // getTime() 返回的是毫秒数
                maxAge = new Date(now.getTime() + 1000 * sec);
            } else if (component.startsWith("s-maxage=")) {
                int sec = Integer.parseInt(component.substring(8));
                sMaxAge = new Date(now.getTime() + 1000 * sec);
            } else if (component.equalsIgnoreCase("must-revalidate")) {
                mustRevalidate = true;
            } else if (component.equalsIgnoreCase("proxy-revalidate")) {
                proxyRevalidate = true;
            } else if (component.equalsIgnoreCase("private")) {
                privateCache = true;
            } else if (component.equalsIgnoreCase("public")) {
                publicCache = true;
            } else if (component.equalsIgnoreCase("no-cache")) {
                noCache = true;
            } else if (component.equalsIgnoreCase("no-store")) {
                noStore = true;
            }
        }
    }

    public Date getMaxAge() {
        return maxAge;
    }

    public Date getSharedMaxAge() {
        return sMaxAge;
    }

    public boolean isMustRevalidate() {
        return mustRevalidate;
    }

    public boolean isProxyRevalidate() {
        return proxyRevalidate;
    }

    public boolean isNoCache() {
        return noCache;
    }

    public boolean isNoStore() {
        return noStore;
    }

    public boolean isPrivateCache() {
        return privateCache;
    }

    public boolean isPublicCache() {
        return publicCache;
    }

}
