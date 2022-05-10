package com.ljh.util;

/**
 * 控制台打印彩色工具
 */
public class ConsolePrintUtil {

    /**
     * @param colour  颜⾊代号：背景颜⾊代号(41-46)；前景⾊代号(31-36)
     * @param type    样式代号：0⽆；1加粗；3斜体；4下划线
     * @param content 要打印的内容
     */
    public static String getFormatLogString(String content, int colour, int type) {
        boolean hasType = type != 1 && type != 3 && type != 4;
        if (hasType) {
            return String.format("\033[%dm%s\033[0m", colour, content);
        } else {
            return String.format("\033[%d;%dm%s\033[0m", colour, type, content);
        }
    }
}
