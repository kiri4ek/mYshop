package ru.test.test2_connect_mysql;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.view.View;
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



public class LoadBasketProducts extends AsyncTask<String, Void, String>
{
    public Integer id;
    public ReloadBasketProduct rBP;
    public View view;
    private ProgressDialog dialog;
    private Context context;


    public LoadBasketProducts(Integer id, ReloadBasketProduct rbp, View view, Context context)
    {
        this.id = id;
        this.rBP = rbp;
        this.view = view;
        this.context = context;
    }

    protected void onPreExecute() {
        this.dialog = new ProgressDialog(context);
        this.dialog.setMessage("Загрузка");

        if (!this.dialog.isShowing()){
            this.dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try {
            String link = "https://xoxol1898.000webhostapp.com/index2.php";
            String data = URLEncoder.encode("host", "UTF-8") + "=localhost&" + URLEncoder.encode("user","UTF-8") + "=id12516608_admin&" + URLEncoder.encode("pas","UTF-8") + "=moloko123&" + URLEncoder.encode("database","UTF-8") + "=id12516608_baseshop&" + URLEncoder.encode("type","UTF-8") + "=LoadProduct&" + URLEncoder.encode("id","UTF-8") + "=" + id;
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

            wr.write(data);
            wr.flush();

            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line;

            //Read Server
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }

            return sb.toString();
        } catch (Exception e) {
            return ("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result) {
        dialog.dismiss();
        this.rBP.out(Html.fromHtml(result).toString(), view);
    }
}
