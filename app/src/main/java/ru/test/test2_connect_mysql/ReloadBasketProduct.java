package ru.test.test2_connect_mysql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReloadBasketProduct extends AppCompatActivity
{

    //Создаем список вьюх которые будут создаваться
    public Basket Bas;
    public Context basContext;
    public List<View> allEds;
    public View view;
    public LinearLayout linear;
    public int[] s_count, s_id;
    public Integer id, k = 0;
    public String ProductName, DescriptionGoods, DescriptionGoodsshort, Price, Kol, URL_Photo, Data_item, Chance_to_buy, Adress;


    protected void ReloadBasketProduct(LinearLayout linear, View view, Integer id, Integer Count, Integer len, Integer i, Basket bas)
    {
        if (i == 0) {
            this.s_count = new int[len];
            this.s_id = new int[len];
        }
        this.Bas = bas;
        this.basContext = bas;
        this.s_count[i] = Count;
        this.s_id[i] = id;
        this.linear = linear;

        allEds = new ArrayList<View>();
        new LoadBasketProducts(s_id[i], this, view, this.Bas).execute();

    }

    public void out(String Out, View view)
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

            vivod1 = vivod2.substring(this.Price.length() + 1);
            Kol = vivod1.substring(0, vivod1.indexOf("\n"));

            vivod2 = vivod1.substring(Kol.length() + 1);
            URL_Photo = vivod2.substring(0, vivod2.indexOf("\n"));

            vivod1 = vivod2.substring(URL_Photo.length() + 1);
            Data_item = vivod1.substring(0, vivod1.indexOf("\n"));

            vivod2 = vivod1.substring(Data_item.length() + 1);
            Chance_to_buy = vivod2.substring(0, vivod2.indexOf("\n"));

            vivod1 = vivod2.substring(Chance_to_buy.length() + 1);
            Adress = vivod1.substring(0, vivod1.indexOf("\n"));

            SetBasket setBask = new SetBasket();

            setBask.setTitleN(ProductName, view, this.Bas);
            setBask.setCountN(s_count[k], view);
            setBask.setPrice(Integer.parseInt(Price) * s_count[k] + "₽", view);
            setBask.setButtonBuyId(s_id[k], view);
            setBask.setButtonDeleteId(s_id[k], view);
            k++;

            //добавляем все что создаем в массив
            allEds.add(view);

            linear.addView(view);
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
