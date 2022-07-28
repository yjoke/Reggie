package com.itheima.reggie.util;

import javax.servlet.http.HttpServletRequest;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * 获取 ip 地址
 * 代码源: https://www.iteye.com/blog/fanshuyao-2436489
 *
 * @author 蕃薯耀
 */

public class IpUtils {

    /**
     * 获取 ip 地址要使用的请求头
     */
    private static final String[] HEADERS = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR",
            "X-Real-IP"
    };

    /**
     * 判断ip是否为空，空返回true
     * @param ip ip 地址
     * @return 空 true
     */
    public static boolean isEmptyIp(final String ip){
        return (ip == null || ip.length() == 0 || ip.trim().equals("") || "unknown".equalsIgnoreCase(ip));
    }


    /**
     * 判断ip是否不为空，不为空返回true
     * @param ip ip 地址
     * @return 空 false
     */
    public static boolean isNotEmptyIp(final String ip){
        return !isEmptyIp(ip);
    }

    /**
     * 获取客户端ip地址(可以穿透代理)
     *
     * @param request HttpServletRequest
     * @return 返回客户端 ip 地址
     */
    public static String getIpAddress(HttpServletRequest request) {
        String ip = "";
        for (String header : HEADERS) {
            ip = request.getHeader(header);
            if(isNotEmptyIp(ip)) {
                break;
            }
        }
        if(isEmptyIp(ip)){
            ip = request.getRemoteAddr();
        }
        if(isNotEmptyIp(ip) && ip.contains(",")){
            ip = ip.split(",")[0];
        }
        if("0:0:0:0:0:0:0:1".equals(ip)){
            ip = "127.0.0.1";
        }
        return ip;
    }


    /**
     * 获取本机的局域网ip地址, 兼容Linux
     *
     * @return String ip 地址
     * @exception SocketException NetworkInterface.getNetworkInterfaces 抛出的
     */
    public String getLocalHostIP() throws SocketException {
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        String localHostAddress = "";
        while(allNetInterfaces.hasMoreElements()){
            NetworkInterface networkInterface = allNetInterfaces.nextElement();
            Enumeration<InetAddress> address = networkInterface.getInetAddresses();
            while(address.hasMoreElements()){
                InetAddress inetAddress = address.nextElement();
                if(inetAddress instanceof Inet4Address){
                    localHostAddress = inetAddress.getHostAddress();
                }
            }
        }
        return localHostAddress;
    }


}
