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
import info.zhiqing.schedule.models.Score;
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

    //some constant
    public static final int LOGIN = 0;
    public static final int SCORE = 1;

    //Http client
    private OkHttpClient client = new OkHttpClient();

    //URLs
    private String baseUrl = "http://61.139.105.138";
    private String loginUrl = "/default2.aspx";
    private String scheduleUrl = "/xskbcx.aspx";
    private String scoreUrl = "/xscjcx_dq.aspx";

    //cookie strings
    private String cookie = "";

    //student login information
    private String number = "";
    private String pass = "";
    private String params = "";
    private String name = "";

    /**
     * Constructor
     * @param number student number
     * @param pass the password
     */
    ScheduleFetchr(String number, String pass){
        this.number = number;
        this.pass = pass;
        params = "?xh=" + number;
        setCookie();
    }


    //setter an getter
    /**
     * number setter
     * @param number student number
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * password setter
     * @param pass the password
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    //some tools

    /**
     * encode encode url to urlcode with gb2312
     * @param url source string
     * @return urlcoded string
     */
    private String codeUrl(String url) {
        String str = "";
        try{
            str = URLEncoder.encode(url, "gb2312");
        } catch (IOException ioe) {

        }
        return str;
    }

    /**
     *
     * @param resp response
     * @return
     */
    private String decodeResponse(Response resp) {
        String str = "";
        if(resp == null){
            return str;
        }
        try{
            str =  new String(resp.body().bytes(), "gb2312");
        } catch (IOException ioe) {

        }

        return str;
    }

    private float toFloat(String str){
        float f = 0;
        try{
            f = Float.parseFloat(str);
        } catch (NumberFormatException e){
            f = 0;
        }
        return f;
    }


    /**
     * request cookies and save it
     */
    private void setCookie(){
        Request request = new Request.Builder()
                .url(baseUrl + loginUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            cookie = response.header("Set-Cookie");
        } catch (IOException ioe) {

        }
    }

    /**
     * request the csrf value and save it
     * @param type SCHEDULE or SCORE
     */
    private String getCsrf(int type){
        String csrf = "";
        String url = baseUrl + loginUrl;

        //build request with url
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Referer", url)
                .build();

        //request the page and find the csrf value
        try {
            Response response = client.newCall(request).execute();
            Document doc = Jsoup.parse(response.body().string());
            csrf = doc.select("input[name=__VIEWSTATE]").first().attr("value");
        } catch (IOException ioe) {

        }
        return csrf;
    }

    void logIn(String code){
        String url = baseUrl + loginUrl;

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
        RequestBody body = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"),
                "__VIEWSTATE=" + getCsrf(LOGIN)
                        + "&txtUserName=" + number // number
                        + "&TextBox2=" + pass // password
                        + "&txtSecretCode=" + code
                        + "&RadioButtonList1=" + codeUrl("学生") //连接gb2312编码的 "学生" 字符串
                        + "&Button1=&lbLanguage=&hidPdrs=&hidsc=");

        request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .addHeader("Referer", url)
                .post(body)
                .build();

        try{
            response = client.newCall(request).execute();
            Document doc = Jsoup.parse(response.body().string());
            name = doc.select("span#xhxm").first().text();
            System.out.println(name);
        } catch (IOException ioe){

        }
    }

    public List<Course> fetchSchedule() {
        String url = baseUrl + scheduleUrl + params;

        Pattern r = Pattern.compile("(^[<>]+)<br>((.+)<br>)?(.+)<br>(.+)<br>(.+)");

        List<Course> courses = new ArrayList<>();

        Request request;
        Response response;

        request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .addHeader("Referer", url)
                .build();

        try{
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

    public List<Score> fetchScore() {
        String url = baseUrl + scoreUrl + params;
        String csrf = "";

        List<Score> scoreList = new ArrayList<>();

        Pattern r = Pattern.compile("");

        Request request;
        Response response;

        request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .addHeader("Referer", url)
                .build();

        try {
            response = client.newCall(request).execute();
            Document doc = Jsoup.parse(response.body().string());
            csrf = doc.select("input[name=__VIEWSTATE]").first().attr("value");
        } catch (IOException ioe) {

        }

        RequestBody body = RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded;charset=gb2312"),
                "__EVENTTARGET=&__EVENTARGUMENT="
                        + "&__VIEWSTATE=" + codeUrl(csrf)
                        + "&ddlxn=" + codeUrl("全部")
                        + "&ddlxq=" + codeUrl("全部")
                        + "&btnCx=" + codeUrl(" 查  询 "));

        request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookie)
                .addHeader("Referer", url)
                .post(body)
                .build();

        try{
            response = client.newCall(request).execute();
            Document doc = Jsoup.parse(response.body().string());
            Elements eles = doc.select("table#DataGrid1 tr:not(.datelisthead)");
            for (Element ele : eles) {
                Score score = new Score(ele.child(0).text(),
                        ele.child(1).text(),
                        ele.child(2).text(),
                        ele.child(3).text(),
                        ele.child(4).text(),
                        toFloat(ele.child(6).text()),
                        toFloat(ele.child(7).text()),
                        toFloat(ele.child(9).text()),
                        toFloat(ele.child(11).text()),
                        toFloat(ele.child(12).text()));
                scoreList.add(score);
            }
            System.out.println(eles.size());
        } catch (IOException ioe) {

        }
        return scoreList;
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
        ScheduleFetchr fetchr = new ScheduleFetchr("14101010607", "lzq1997201");

        byte[] bytes = fetchr.fetchCodeImageBytes();
        DataOutputStream dos = new DataOutputStream(new FileOutputStream("/home/zhiqing/code.gif"));
        dos.write(bytes);
        dos.flush();

        Scanner in = new Scanner(System.in);
        String code = in.nextLine();

        fetchr.logIn(code);
        fetchr.fetchSchedule();
        System.out.println(fetchr.fetchScore());
    }
}
