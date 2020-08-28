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



public class AddOrder extends AsyncTask<String, Void, String>
{
    public Integer id_product, count_product;
    public String TypeOfPayment;
    public SetBasket sb;
    public View view;
    public Context bas;
    private ProgressDialog dialog;

    public AddOrder(Integer id_product, Integer count_product, String typePay, SetBasket SB, View view, Context BAS)
    {
        this.id_product = id_product;
        this.count_product = count_product;
        this.TypeOfPayment = typePay;
        this.sb = SB;
        this.view = view;
        this.bas = BAS;
    }

    protected void onPreExecute() {
        this.dialog = new ProgressDialog(this.bas);
        this.dialog.setMessage("Оформление заказа");

        if (!this.dialog.isShowing()){
            this.dialog.show();
        }
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        try {
            String link = "https://xoxol1898.000webhostapp.com/index2.php";
            String data = URLEncoder.encode("host", "UTF-8") + "=localhost&" + URLEncoder.encode("user","UTF-8") + "=id12516608_admin&" + URLEncoder.encode("pas","UTF-8") + "=moloko123&" + URLEncoder.encode("database","UTF-8") + "=id12516608_baseshop&" + URLEncoder.encode("type","UTF-8") + "=AddOrder&" + URLEncoder.encode("id_client","UTF-8") + "=" + Client.id + "&" + URLEncoder.encode("id_product","UTF-8") + "=" + id_product + "&" + URLEncoder.encode("kol_product","UTF-8") + "=" + count_product + "&" + URLEncoder.encode("type_of_payment","UTF-8") + "=" + TypeOfPayment;
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
        this.sb.outAddOrder(Html.fromHtml(result).toString(), view, this.bas);
    }
}
