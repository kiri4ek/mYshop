package ru.test.test2_connect_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReloadGlobalProduct extends AppCompatActivity
{

    //Создаем список вьюх которые будут создаваться
    public List<View> allEds;
    public View view;
    public LinearLayout linear;
    public Integer id;
    public String ProductName, DescriptionGoods, DescriptionGoodsshort, Price, Kol, URL_Photo, Data_item, Chance_to_buy, Adress;
    public Integer colorFrame, colorBloks;
    public Context Context;
    public MainActivity ma;

    SetProduct[] products;

    protected void ReloadGlobalProduct(LinearLayout linear, View view, Integer id, Integer colorFrame, Integer colorBloks, Context context)
    {
        this.view = view;
        this.id = id;
        this.linear = linear;
        this.colorFrame = colorFrame;
        this.colorBloks = colorBloks;
        this.Context = context;

        allEds = new ArrayList<View>();
        new LoadProducts(id, this, view, this.Context).execute();

    }

    public void out(String Out, View view, Integer id)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            String vivod1, vivod2;

            vivod1 = Out.substring(105);
            ProductName = vivod1.substring(0, vivod1.indexOf("\n"));

            vivod2 = vivod1.substring(ProductName.length() + 1);
            DescriptionGoods = vivod2.substring(0, vivod2.indexOf("$"));

            vivod1 = vivod2.substring(DescriptionGoods.length() + 2);
            DescriptionGoodsshort = vivod1.substring(0, vivod1.indexOf("$"));

            vivod2 = vivod1.substring(DescriptionGoodsshort.length() + 2);
            Price = vivod2.substring(0, vivod2.indexOf("\n"));

            vivod1 = vivod2.substring(Price.length() + 1);
            Kol = vivod1.substring(0, vivod1.indexOf("\n"));

            vivod2 = vivod1.substring(Kol.length() + 1);
            URL_Photo = vivod2.substring(0, vivod2.indexOf("\n"));

            vivod1 = vivod2.substring(URL_Photo.length() + 1);
            Data_item = vivod1.substring(0, vivod1.indexOf("\n"));

            vivod2 = vivod1.substring(Data_item.length() + 1);
            Chance_to_buy = vivod2.substring(0, vivod2.indexOf("\n"));

            vivod1 = vivod2.substring(Chance_to_buy.length() + 1);
            Adress = vivod1.substring(0, vivod1.indexOf("\n"));

            SetProduct globPr = new SetProduct();

            globPr.setTitleN(ProductName, view, Context);
            globPr.setDescription(DescriptionGoodsshort, view);
            globPr.setPrice(Price + "₽", view);
            globPr.setImage(URL_Photo, view);
            globPr.setButtonAddBasketId(id, view);
            globPr.setColorBloks(colorFrame, colorBloks, view);

            //добавляем все что создаем в массив
            allEds.add(view);

            linear.addView(view);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
