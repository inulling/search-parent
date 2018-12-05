package net.vipmro.search.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 表情符处理
 *
 * @author fengxiangyang
 * @date 2018/11/30
 */
public class EmojiUtils {
    /**
     * 检测是否有emoji字符
     *
     * @param source
     * @return
     */
    public static boolean containsEmoji(String source) {
        if (StringUtils.isBlank(source)) {
            return false;
        }
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (isEmoji(codePoint)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isEmoji(char codePoint) {
        final boolean b = (codePoint == 0x0) ||
                (codePoint == 0x9) ||
                (codePoint == 0xA) ||
                (codePoint == 0xD) ||
                ((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
                ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
                ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
        return !b;
    }

    /**
     * 过滤emoji 或者 其他非文字类型的字符
     *
     * @param source
     * @return
     */
    public static String filterEmoji(String source, String rep) {
        if (!containsEmoji(source)) {
            //如果不包含，直接返回
            return source;
        }
        StringBuilder builder = new StringBuilder(source.length());
        int len = source.length();
        for (int i = 0; i < len; i++) {
            char codePoint = source.charAt(i);
            if (!isEmoji(codePoint)) {
                builder.append(codePoint);
            } else {
                builder.append(rep);
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println("args = [" + URLEncoder.encode("\uD83D\uDCB3adsfasd啊第三方", "UTF-8") + "]");
    }
}
