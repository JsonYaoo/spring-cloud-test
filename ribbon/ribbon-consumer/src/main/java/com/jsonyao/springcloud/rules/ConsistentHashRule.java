package com.jsonyao.springcloud.rules;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 自定义IRule: 一致性Hash
 */
@NoArgsConstructor
public class ConsistentHashRule extends AbstractLoadBalancerRule implements IRule {

    /**
     * 初始化配置
     * @param clientConfig
     */
    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    /**
     * 负载均衡
     * @param key
     * @return
     */
    @Override
    public Server choose(Object key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String uri = request.getServletPath() + "?" + request.getQueryString();
        return route(uri.hashCode(), getLoadBalancer().getAllServers());
    }

    /**
     * 负载均衡实现: 虚化结点可能导致不平均, 还可以改进到绝对公平的情况
     * @param hashId
     * @param addressList
     * @return
     */
    public Server route(int hashId, List<Server> addressList){
        if(CollectionUtils.isEmpty(addressList)){
            return null;
        }

        // 虚化Server结点, 这里每个Server拥有8个虚化结点
        TreeMap<Long, Server> address = new TreeMap<>();
        addressList.stream().forEach(server -> {
            // 虚化Server结点到环上
            for(int i = 0; i < 8; i++){
                // 加入变数i
                long hash = hash(server.getId() + i);
                address.put(hash, server);
            }
        });

        // tailMap取比当前hash大的且离他最近的一个结点 => 顺时针方向: tailMap有序, 会拿到所有比他大的结点
        long hash = hash(String.valueOf(hashId));
        SortedMap<Long, Server> lastSevers = address.tailMap(hash);

        // 如果拿不到比他大的结点, 说明我们的hash到了末尾, 需要取最小的结点, 从而虚化成一个环
        if(lastSevers.isEmpty()){
            return address.firstEntry().getValue();
        }

        // 如果拿得到比他大的结点, 则取大集中的最小值
        return lastSevers.get(lastSevers.firstKey());
    }

    public long hash(String key){
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        byte[] keyBytes = null;
        try {
            keyBytes = key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 根据Key的字节数组生成16字节(128位)的MD5摘要
        md5.update(keyBytes);
        byte[] digest = md5.digest();

        // 计算long长度的hashCode => 这里做测试, 只取低4个字节整成long型数字作为hashCode, 其中高4个字节都取0, 这里与0xFF避免字节本身为1开头时移位带来的问题
        long hashCode = (digest[3] & 0xFF) << 24    // 高16位
                      | (digest[2] & 0xFF) << 16    // 高8位
                      | (digest[1] & 0xFF) << 8     // 低16位
                      | (digest[0] & 0xFF);         // 低8位

        // 只取低8字节作为long型返回, 一个long型数字占8个字节
        return hashCode & 0xFFFFFFFFL;
    }

}
