package ru.test.test2_connect_mysql;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.MalformedURLException;



public class KolProductChance extends AsyncTask<String, Void, String>
{
    public double chance1, chance2;
    public String type, str;
    public MainActivity gPC;
    private ProgressDialog dialog;
    public Context context;

    // Конструктор класса
    public KolProductChance(String type, Double chance1, Double chance2, MainActivity gPC)
    {
        this.chance1 = chance1;
        this.chance2 = chance2;
        this.type = type;
        this.gPC = gPC;
        this.context = gPC;
    }

    // Метод работает до начала самой главной асинхронной задачи и имеет связь с интерфейсом
    protected void onPreExecute() {
        this.dialog = new ProgressDialog(this.gPC);
        this.dialog.setMessage("Загрузка");

        if (!this.dialog.isShowing()){
            this.dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try {
            // адрес веб-сервера
            String link = "https://xoxol1898.000webhostapp.com/index2.php";

            // данные для отправки с кодировкой UTF-8 поддерживаемой сервером
            String data = URLEncoder.encode("host", "UTF-8") + "=localhost&" + URLEncoder.encode("user","UTF-8") + "=id12516608_admin&" + URLEncoder.encode("pas","UTF-8") + "=moloko123&" + URLEncoder.encode("database","UTF-8") + "=id12516608_baseshop&" + URLEncoder.encode("type","UTF-8") + "=KolProductChance&" + URLEncoder.encode("chance1","UTF-8") + "=" + chance1 + "&" + URLEncoder.encode("chance2","UTF-8") + "=" + chance2 + "&" + URLEncoder.encode("type_product","UTF-8") + "=" + type;
            URL url = new URL(link);

            // получаем объект conn от HttpURLConnection по заданному URL соединению
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();

            // устанавливаем тип содержимого - параметр ContentType
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            // для отправки используется метод Post
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            // передается тело запроса HTTP в OutputStream
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            // пишем в сформированное тело запроса write() и обязательно чистим буффер
            wr.write(data);
            wr.flush();

            // читаем данные в переменную буффера BufferedReader
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            // чтение из буффера
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return sb.toString();
        } catch (Exception e) {
            return ("Exception: " + e.getMessage());
        }
    }

    // переход в основной поток класса MainActivity метода out
    @Override
    protected void onPostExecute(String result) {
        this.dialog.dismiss();
        this.gPC.out(Html.fromHtml(result).toString());
    }
}
