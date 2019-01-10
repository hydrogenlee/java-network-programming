package chapter5;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class C5P156_LocalProxySelector extends ProxySelector {
    public static void main(String[] args) {
        C5P156_LocalProxySelector selector = new C5P156_LocalProxySelector();
        ProxySelector.setDefault(selector);
    }

    private List<URI> failed = new ArrayList<>();

    @Override
    public List<Proxy> select(URI uri) {
        List<Proxy> result = new ArrayList<>();
        // 如果代理失败或者uri的类型不是http，则不经过代理，直接执行
        if (failed.contains(uri) || !"http".equalsIgnoreCase(uri.getScheme())) {
            result.add(Proxy.NO_PROXY);
        } else {
            SocketAddress proxyAddress = new InetSocketAddress("proxy.example.com", 80);
            Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddress);
            result.add(proxy);
        }

        return result;
    }

    @Override
    public void connectFailed(URI uri, SocketAddress sa, IOException ioe) {
        // 这是一个回调方法，用于警告程序这个代理服务器实际上没有建立连接
        failed.add(uri);
    }
}
