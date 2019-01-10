package chapter7;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CacheResponse;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class C7P203_SimpleCacheResponse extends CacheResponse {
    private final Map<String, List<String>> headers;
    private final C7P202_SimpleCacheRequest request;
    private final Date expires;
    private final C7P199_CacheControl control;

    public C7P203_SimpleCacheResponse(C7P202_SimpleCacheRequest request, URLConnection connection,
                                      C7P199_CacheControl control) throws IOException{
        this.request = request;
        this.control = control;
        this.expires = new Date(connection.getExpiration());
        this.headers = connection.getHeaderFields();
    }


    @Override
    public Map<String, List<String>> getHeaders() throws IOException {
        return headers;
    }

    @Override
    public InputStream getBody() throws IOException {
        return new ByteArrayInputStream(request.getData());
    }

    public C7P199_CacheControl getControl() {
        return control;
    }

    public boolean isExpired() {
        Date now = new Date();
        if (control.getMaxAge().before(now)) {
            return true;
        } else if (expires != null && control.getMaxAge() != null) {
            return expires.before(now);
        } else {
            return false;
        }
    }
}
