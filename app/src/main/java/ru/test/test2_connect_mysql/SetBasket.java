package ru.test.test2_connect_mysql;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.INotificationSideChannel;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
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

public class SetBasket extends AppCompatActivity implements NumberPicker.OnValueChangeListener, View.OnClickListener
{

    public Basket Bas;
    public TextView titleN, priceN;
    public NumberPicker Count;
    public View view1;
    public Button buttonBuy, buttonDelete;
    public Context bas;
    public String type;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basket);
    }


    public String getTitleN() {
        return this.titleN.getText().toString();
    }

    public void setTitleN(String title, View view, Basket bas) {
        Client.setBasket = this;
        this.Bas = bas;     //Сам this корзины
        this.bas = bas;     //Контекстовый
        this.view1 = view;
        titleN = view.findViewById(R.id.Name_Product);
        this.titleN.setText(title);
    }

    public int getCountN() {
        Count = view1.findViewById(R.id.Counter);
        return Count.getValue();
    }

    public void setCountN(Integer count, View view) {
        Count = view.findViewById(R.id.Counter);

        Count.setOnValueChangedListener(this);

        Count.setMaxValue(100);
        Count.setMinValue(1);
        Count.setWrapSelectorWheel(true);
        Count.setValue(count);
    }

    public String getPrice() {
        return this.priceN.getText().toString();
    }

    public void setPrice(String price, View view){
        priceN = view.findViewById(R.id.Price);
        this.priceN.setText(price);
    }

    public int getButtonBuyId(){
        return this.buttonBuy.getId();
    }

    public void setButtonBuyId(int id, View view){
        buttonBuy = view.findViewById(R.id.ButtonOrder);
        buttonBuy.setId(id);
        buttonBuy.setOnClickListener(this);
    }

    public int getButtonDeleteId(){
        return this.buttonDelete.getId();
    }

    public void setButtonDeleteId(int id, View view){
        buttonDelete = view.findViewById(R.id.ButtonDelete);
        buttonDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Client.id_product = v.getId();
        Client.count_product = getCountN();

        if (v.getId() == R.id.ButtonDelete)
        {
            new DeleteBasketProduct(getButtonBuyId(), this, view1, this.Bas).execute();
        } else if (Client.CountTypeOfPayment < 1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(bas);
            builder.setTitle("Заказ").setIcon(R.drawable.ic_launcher_add_foreground);
            builder.setMessage("Безналичный способ оплаты?").setPositiveButton("Да", dialogClickListener).setNegativeButton("Нет", dialogClickListener).show();
        } else if (Client.TypeOfPayment) {
            new AddOrder(Client.id_product, Client.count_product, "Безналичный", Client.setBasket, view1, Bas).execute();
        } else
            new AddOrder(Client.id_product, Client.count_product, "Наличный", Client.setBasket, view1, Bas).execute();
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        String price = getPrice().substring(0, getPrice().indexOf("₽"));

        if (Double.parseDouble(price) % oldVal != 0) {
            Double Now_price = Double.parseDouble(price) / oldVal;
            setPrice(String.valueOf(newVal * Now_price) + "₽", this.view1);
        }else {
            Integer Now_price = Integer.parseInt(price) / oldVal;
            setPrice(String.valueOf(newVal * Now_price) + "₽", this.view1);
        }

        new UpdateBasketProduct(getButtonBuyId(), newVal, this, this.view1).execute();
    }

    public void outUpdateBasket(String Out, View view)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            String vivod1, vivod2;

            vivod1 = Out.substring(105);
        } else {
            Toast toast = Toast.makeText(bas, "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void outAddOrder(String Out, View view, Context bas)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            String Message;

            Message = Out.substring(105);
            Message = Message.substring(0, Message.indexOf("\n"));

            //Нужно обновить basket
            Intent intent = new Intent(bas, Basket.class);

            bas.startActivity(intent);

            Toast toast = Toast.makeText(bas, Message, Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(bas, "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void outDeleteBasket(String Out, Context bas)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            String vivod1 = Out.substring(105);

            Intent intent = new Intent(bas, Basket.class);

            bas.startActivity(intent);
        } else {
            Toast toast = Toast.makeText(bas, "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    //Yes button clicked
                        new AddOrder(Client.id_product, Client.count_product, "Безналичный", Client.setBasket, view1, Bas).execute();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                        new AddOrder(Client.id_product, Client.count_product, "Наличный", Client.setBasket, view1, Bas).execute();
                    break;
            }
        }
    };
}
