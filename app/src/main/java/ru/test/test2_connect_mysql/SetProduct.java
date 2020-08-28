package ru.test.test2_connect_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.INotificationSideChannel;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class SetProduct extends AppCompatActivity implements View.OnClickListener
{

    public TextView titleN, descriptionN, priceN;
    public ImageView imageN;
    public Button buttonAddBasket;
    public Context Context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product);
    }


    public String getTitleN() {
        return this.titleN.getText().toString();
    }

    public void setTitleN(String title, View view, Context context) {
        this.Context = context;
        this.titleN = view.findViewById(R.id.Name_Product);
        this.titleN.setText(title);
    }

    public void setColorBloks(Integer colorFrame, Integer colorBloks, View view){
        view.setBackgroundColor(colorFrame);           //цвет обводки рамки
        LinearLayout linearLayout = view.findViewById(R.id.blocks);
        linearLayout.setBackgroundColor(colorBloks);   //цвет самого блока
    }

    public String getDescription() {
        return this.descriptionN.getText().toString();
    }

    public void setDescription(String description, View view){
        descriptionN = view.findViewById(R.id.Description_Product);
        this.descriptionN.setText(description);
    }

    public String getPrice() {
        return this.priceN.getText().toString();
    }

    public void setPrice(String price, View view){
        priceN = view.findViewById(R.id.Price);
        priceN.setTextColor(Color.rgb(218,165,32));
        this.priceN.setText(price);
    }

    public Bitmap getImage() {
        Bitmap bitmap = ((BitmapDrawable)this.imageN.getDrawable()).getBitmap();
        //URI uri = Picasso.get().load().into(this.image);
        return bitmap;
    }

    public void setImage(String URL, View view){
        imageN = view.findViewById(R.id.Image);
        Picasso.get().load(URL).into(this.imageN);
    }

    public int getButtonAddBasketId(){
        return this.buttonAddBasket.getId();
    }

    public void setButtonAddBasketId(Integer id, View view){
        buttonAddBasket = view.findViewById(R.id.AddBasket);
        buttonAddBasket.setId(id);
        buttonAddBasket.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Integer id = getButtonAddBasketId();
        new AddBasketProduct(id, this, this.Context).execute();
    }

    public void outAddBasketPr(String Out, Context context)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            String Message;

            Message = Out.substring(105);
            Message = Message.substring(0, Message.length() - 3);

            Toast toast = Toast.makeText(context, Message, Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(Context, "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
