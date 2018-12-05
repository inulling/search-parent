package net.vipmro.search.core.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * IP工具
 *
 * @author fengxiangyang
 * @date 2018/11/30
 */
public class IpUtils {

    /**
     * 获取客户端IP
     *
     * @param request
     * @return
     */
    public static String getRemoteAddress(HttpServletRequest request) {
        String ip = request.getHeader("Cdn-Src-Ip");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String[] ips = ip.split(",");
        String trueIp = "";
        for (int i = 0; i < ips.length; i++) {
            if (!("unknown".equalsIgnoreCase(ips[i]))) {
                trueIp = ips[i];
                break;
            }
        }
        if ("0:0:0:0:0:0:0:1".equals(trueIp)) {
            trueIp = "127.0.0.1";
        }
        return trueIp;
    }

}
