package chapter7;

import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class C7P204_MemoryCache extends ResponseCache {
    public static void main(String[] args) {
        ResponseCache.setDefault(new C7P204_MemoryCache());
    }

    private final Map<URI, C7P203_SimpleCacheResponse> responses = new ConcurrentHashMap<>();
    private final int maxEntries;

    public C7P204_MemoryCache() {
        this(100);
    }

    public C7P204_MemoryCache(int maxEntries) {
        this.maxEntries = maxEntries;
    }


    @Override
    public CacheResponse get(URI uri, String rqstMethod, Map<String, List<String>> rqstHeaders) throws IOException {
        if ("GET".equalsIgnoreCase(rqstMethod)) {
            C7P203_SimpleCacheResponse response = responses.get(uri);
            if (response != null && response.isExpired()) {
                responses.remove(uri);
                response = null;
            }
            return response;
        }

        return null;
    }

    @Override
    public CacheRequest put(URI uri, URLConnection conn) throws IOException {
        if (responses.size() >= maxEntries) {
            return null;
        }
        C7P199_CacheControl cacheControl = new C7P199_CacheControl(conn.getHeaderField("Cache-Control"));
        if (cacheControl.isNoStore()) {
            return null;
        } else if (!conn.getHeaderField(0).startsWith("GET")) {
            // 只缓存GET方法
            return null;
        }

        C7P202_SimpleCacheRequest request = new C7P202_SimpleCacheRequest();
        C7P203_SimpleCacheResponse response = new C7P203_SimpleCacheResponse(request, conn, cacheControl);
        responses.put(uri, response);
        return request;
    }
}
