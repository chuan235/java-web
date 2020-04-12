package com.gc.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HttpJsoup {

    public static String rootUrl = "http://pic.netbian.com/4kyouxi/";

    public static String baiduUrl = "http://image.baidu.com/search/acjson?tn=resultjson_com&ipn=rj&ct=201326592&is=&fp=result&queryWord=abc&cl=2&lm=-1&ie=utf-8&oe=utf-8&adpicid=&st=-1&z=&ic=0&hd=&latest=&copyright=&word=abc&s=&se=&tab=&width=&height=&face=0&istype=2&qc=&nc=1&fr=&expermode=&force=&pn=30&rn=30&gsm=&1573869743459=";

    public static void main(String[] args) throws IOException {
        Connection connect = Jsoup.connect(baiduUrl);
        System.out.println(connect.get());
    }

    public static void demo2() throws IOException {
        // 403处理
        Connection connect = Jsoup.connect(rootUrl);
        Map<String,String> headers = new HashMap<>();
        headers.put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3");
        headers.put("Accept-Encoding","gzip, deflate");
        headers.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
        headers.put("Cache-Control","no-cache");
        headers.put("Connection","keep-alive");
        headers.put("Cookie"," __cfduid=db3c76d8db3ca618235fc1da73ffb8da61560736781; yjs_id=45e29a41b7ed7477f64cf209a5ef834e; cf_clearance=0cfc3d6107916a1297c9b9104e1d261ff787aa4c-1573868918-0-250; security_session_verify=701952981f8a627652c70a6af3dfa83c; Hm_lvt_14b14198b6e26157b7eba06b390ab763=1573645396,1573647817,1573798934,1573868920; Hm_lpvt_14b14198b6e26157b7eba06b390ab763=1573868920");
        headers.put("Host"," www.netbian.com");
        headers.put("Pragma"," no-cache");
        headers.put("Referer","http://www.netbian.com/");
        headers.put("Upgrade-Insecure-Requests"," 1");
        headers.put("User-Agent"," Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.97 Safari/537.36");
        connect.headers(headers);
        System.out.println(connect.get());
    }
    public static void demo1(){
        URL url = null;
        DataInputStream inputStream = null;
        FileOutputStream fos = null;
        HttpURLConnection conn = null;
        try {
            for (int i = 0; i < 90; i++) {
                url = new URL(rootUrl );
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(50000);
                conn.setReadTimeout(50000);
                conn.addRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
                conn.connect();
//                inputStream = new DataInputStream(conn.getInputStream());
//                fos = new FileOutputStream(new File("D:/file/images/" + getName(i)));
//                byte[] bytes = new byte[1024];
//                int len = 0;
//                while ((len = inputStream.read(bytes)) != -1) {
//                    fos.write(bytes, 0, len);
//                }
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("下载失败");
        }
    }
    public static String getName(int seq) {
        String result = "";
        if (seq < 10) {
            result = "00" + seq;
        } else if (seq < 100) {
            result = "0" + seq;
        } else {
            result = "" + seq;
        }
        return result + ".jpg";
    }
}
