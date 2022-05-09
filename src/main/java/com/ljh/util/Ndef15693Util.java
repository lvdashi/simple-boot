package com.ljh.util;


/**
 * nfc计算
 *
 * @author jinhuilv
 * @date 2022/04/28 19:20
 **/
public class Ndef15693Util {


    private int all_payload_length=0;//payload总长度
    private String all_payload_length_hex="";
    private String TNF_INFO="D101";//标识符信息
    private String RTD_INFO="55"; //代表‘U’ 声明ndef是url
    private String RTD_TYPE="";
    private String url_info="";//url payload信息
    private String url_info_hex="";
    private int url_info_length=0;//url payload长度
    private String url_info_length_hex="";
    private String memInfo="";//存储区信息
    private String chip_info="";//ndef芯片信息

    /**
     * 15693 网址换算层成user区的16进制数据
     * @param maxMemeryNum 芯片区块数量
     * @param url 网址
     * @return
     */
    public String urlToHex(int maxMemeryNum,String url){
        calcRTD(url);
        String chipMemInfo=String.format("%02x", maxMemeryNum/2);
        chip_info=chipMemInfo+"01";
        url_info_length=1+url_info.length();
        url_info_length_hex=String.format("%02x", url_info_length);
        all_payload_length=5+url_info.length();
        all_payload_length_hex=String.format("%02x", all_payload_length);
        url_info_hex=strTo16(url_info).toUpperCase();
        memInfo="E140"+chip_info+"03"+all_payload_length_hex+TNF_INFO+url_info_length_hex+RTD_INFO+RTD_TYPE+url_info_hex;


        String men=memInfo.toUpperCase();
        if(men.length()%8!=0){
            int len=8-(men.length()%8);
            for (int i = 0; i < len; i++) {
                men+="0";
            }
        }
        System.out.println("15693 完整区块信息 =>"+men.toUpperCase());
        for (int i = 0; i < men.length()/8; i++) {
            String blockInfo=men.substring(i*8,(i+1)*8);
            blockInfo=blockInfo.substring(0,2)+" "+blockInfo.substring(2,4)+" "+blockInfo.substring(4,6)+" "+blockInfo.substring(6,8);
            System.out.println("[ "+String.format("%02x", i).toUpperCase()+" ] 块"+(i+1)+":  "+blockInfo);
        }

        return men;
    }

    /**
     * 字符串转换成为16进制(无需Unicode编码)
     * @param str
     * @return
     */
    public static String str2HexStr(String str) {
        char[] chars = "0123456789ABCDEF".toCharArray();
        StringBuilder sb = new StringBuilder("");
        byte[] bs = str.getBytes();
        int bit;
        for (int i = 0; i < bs.length; i++) {
            bit = (bs[i] & 0x0f0) >> 4;
            sb.append(chars[bit]);
            bit = bs[i] & 0x0f;
            sb.append(chars[bit]);
            // sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 字符串转化成为16进制字符串
     * @param s
     * @return
     */
    public static String strTo16(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }

    /**
     * 计算RTD
     * @param url
     */
    public void calcRTD(String url){
        if(url.indexOf("http://www.")!=-1){
            RTD_TYPE="01";
            url_info=url.replace("http://www.","");
        }else if(url.indexOf("https://www.")!=-1){
            RTD_TYPE="02";
            url_info=url.replace("https://www.","");
        }else if(url.indexOf("http://")!=-1){
            RTD_TYPE="03";
            url_info=url.replace("http://","");
        }else if(url.indexOf("https://")!=-1){
            RTD_TYPE="04";
            url_info=url.replace("https://","");
        }else if(url.indexOf("tel:")!=-1){
            RTD_TYPE="05";
            url_info=url.replace("tel:","");
        }else if(url.indexOf("mailto:")!=-1){
            RTD_TYPE="06";
            url_info=url.replace("mailto:","");
        }else if(url.indexOf("ftp://anonymous:anonymous@")!=-1){
            RTD_TYPE="07";
            url_info=url.replace("ftp://anonymous:anonymous@","");
        }else if(url.indexOf("ftp://ftp.")!=-1){
            RTD_TYPE="08";
            url_info=url.replace("ftp://ftp.","");
        }else if(url.indexOf("ftps://")!=-1){
            RTD_TYPE="09";
            url_info=url.replace("ftps://","");
        }else if(url.indexOf("sftp://")!=-1){
            RTD_TYPE="0A";
            url_info=url.replace("sftp://","");
        }else if(url.indexOf("smb://")!=-1){
            RTD_TYPE="0B";
            url_info=url.replace("smb://","");
        }else if(url.indexOf("nfs://")!=-1){
            RTD_TYPE="0C";
            url_info=url.replace("nfs://","");
        }else if(url.indexOf("ftp://")!=-1){
            RTD_TYPE="0D";
            url_info=url.replace("ftp://","");
        }else if(url.indexOf("dav://")!=-1){
            RTD_TYPE="0E";
            url_info=url.replace("dav://","");
        }else if(url.indexOf("news:")!=-1){
            RTD_TYPE="0F";
            url_info=url.replace("news:","");
        }else if(url.indexOf("telnet://")!=-1){
            RTD_TYPE="10";
            url_info=url.replace("telnet://","");
        }else if(url.indexOf("imap:")!=-1){
            RTD_TYPE="11";
            url_info=url.replace("imap:","");
        }else if(url.indexOf("rtsp://")!=-1){
            RTD_TYPE="12";
            url_info=url.replace("rtsp://","");
        }else if(url.indexOf("urn:")!=-1){
            RTD_TYPE="13";
            url_info=url.replace("urn:","");
        }else if(url.indexOf("pop:")!=-1){
            RTD_TYPE="14";
            url_info=url.replace("pop:","");
        }else if(url.indexOf("sip:")!=-1){
            RTD_TYPE="15";
            url_info=url.replace("sip:","");
        }else if(url.indexOf("sips:")!=-1){
            RTD_TYPE="16";
            url_info=url.replace("sips:","");
        }else if(url.indexOf("tftp:")!=-1){
            RTD_TYPE="17";
            url_info=url.replace("tftp:","");
        }else if(url.indexOf("btspp://")!=-1){
            RTD_TYPE="18";
            url_info=url.replace("btspp://","");
        }else if(url.indexOf("btl2cap://")!=-1){
            RTD_TYPE="19";
            url_info=url.replace("btl2cap://","");
        }else if(url.indexOf("btgoep://")!=-1){
            RTD_TYPE="1A";
            url_info=url.replace("btgoep://","");
        }else if(url.indexOf("tcpobex://")!=-1){
            RTD_TYPE="1B";
            url_info=url.replace("tcpobex://","");
        }else if(url.indexOf("irdaobex://")!=-1){
            RTD_TYPE="1C";
            url_info=url.replace("irdaobex://","");
        }else if(url.indexOf("file://")!=-1){
            RTD_TYPE="1D";
            url_info=url.replace("file://","");
        }else if(url.indexOf("urn:epc:id:")!=-1){
            RTD_TYPE="1E";
            url_info=url.replace("urn:epc:id:","");
        }else if(url.indexOf("urn:epc:tag:")!=-1){
            RTD_TYPE="1F";
            url_info=url.replace("urn:epc:tag:","");
        }else if(url.indexOf("urn:epc:pat:")!=-1){
            RTD_TYPE="20";
            url_info=url.replace("urn:epc:pat:","");
        }else if(url.indexOf("urn:epc:raw:")!=-1){
            RTD_TYPE="21";
            url_info=url.replace("urn:epc:raw:","");
        }else if(url.indexOf("urn:epc:")!=-1){
            RTD_TYPE="22";
            url_info=url.replace("urn:epc:","");
        }else if(url.indexOf("urn:nfc:")!=-1){
            RTD_TYPE="23";
            url_info=url.replace("urn:nfc:","");
        }else{
            RTD_TYPE="00";
            url_info=url;
        }
    };

}
