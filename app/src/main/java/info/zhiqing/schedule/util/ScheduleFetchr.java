package info.zhiqing.schedule.util;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.zhiqing.schedule.models.Course;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by zhiqing on 16-10-11.
 */

public class ScheduleFetchr {
    private static final String TAG = "info.zhiqing.HtmlFetchr";

    private OkHttpClient client = new OkHttpClient();

    private final String baseUrl = "http://61.139.105.138";
    private final String loginUrl = "/default2.aspx";

    private String cookie = "";
    private String csrf = "";


    ScheduleFetchr(){
        setCookieAndCsrf();
    }

    void setCookieAndCsrf(){
        Request request = new Request.Builder()
                .url(baseUrl + loginUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            cookie = response.header("Set-Cookie");
            Document doc = Jsoup.parse(response.body().string());
            csrf = doc.select("input[name=__VIEWSTATE]").first().attr("value");
        } catch (IOException ioe) {

        }
    }

    void logIn(String number, String pass, String code){
        Request request;
        Response response;

        /*RequestBody body = new FormBody.Builder()
                .add("__VIEWSTATE", csrf)
                .add("txtUserName", number)
                .add("TextBox2", pass)
                .add("txtSecretCode", code)
                .add("RadioButtonList1", "学生")
                .build();*/


        //由于教务系统采用的GB2312编码，需手动拼接表单数据
        try{
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"),
                    "__VIEWSTATE=" + URLEncoder.encode(csrf, "gb2312")
                            + "&txtUserName=" + URLEncoder.encode(number,"gb2312") //名字
                            + "&TextBox2=" + URLEncoder.encode(pass, "gb2312") // 密码
                            + "&txtSecretCode=" + URLEncoder.encode(code,"gb2312")
                            + "&RadioButtonList1=" + URLEncoder.encode("学生", "gb2312") //连接gb2312编码的 "学生" 字符串
                            + "&Button1=&lbLanguage=&hidPdrs=&hidsc=");

            request = new Request.Builder()
                    .url(baseUrl + loginUrl)
                    .addHeader("Cookie", cookie)
                    .addHeader("Referer", baseUrl + loginUrl)
                    .post(body)
                    .build();

            response = client.newCall(request).execute();
        } catch (IOException ioe){

        }
    }

    public List<Course> fetchSchedule(String number, String pass, String code) {
        String scheduleUrl = "/xskbcx.aspx?xh=" + number;

        Pattern r = Pattern.compile("(^[<>]+)<br>((.+)<br>)?(.+)<br>(.+)<br>(.+)");

        List<Course> courses = new ArrayList<Course>();

        Request request;
        Response response;

        logIn(number, pass, code);

        try{
            request = new Request.Builder()
                    .url(baseUrl + scheduleUrl)
                    .addHeader("Cookie", cookie)
                    .addHeader("Referer", baseUrl + scheduleUrl)
                    .build();

            response = client.newCall(request).execute();

            System.out.println(response.code());

            Document doc = Jsoup.parse(response.body().string());
            Elements eles = doc.select("#Table1 td:contains({)");

            List<String> coursesStrings = new ArrayList<>();

            for(Element ele : eles){
                String courseString = ele.html();
                String[] strs = courseString.split("<br><br>");
                for(String str : strs) {
                    coursesStrings.add(str);
                }
            }

            for(String str : coursesStrings){
                System.out.println(str);
            }

            
        } catch (IOException ioe) {

        }

        return courses;
    }

    public byte[] fetchCodeImageBytes() throws IOException{
        Request request = new Request.Builder()
                .url(baseUrl + "/CheckCode.aspx")
                .addHeader("Cookie", cookie)
                .build();

        Response response = client.newCall(request).execute();

        if(!response.isSuccessful()){
            Log.e(TAG, "服务器错误:" + response);
        }

        return response.body().bytes();

    }



    public static void main(String[] args) throws IOException {
        ScheduleFetchr fetchr = new ScheduleFetchr();

        byte[] bytes = fetchr.fetchCodeImageBytes();
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("/home/zhiqing/code.gif"));
        dos.write(bytes);
        dos.flush();

        Scanner in = new Scanner(System.in);
        String code = in.nextLine();

        fetchr.fetchSchedule("14101010607", "lzq1997201", code);
    }
}
