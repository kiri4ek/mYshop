package ru.test.test2_connect_mysql;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class Basket extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public int[] s_id, s_count;
    public String str;
    public ActionBarDrawerToggle mToggle;
    public DrawerLayout mDrawerLayout;
    public NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Activity.activity_basket = true;

        Activity.activity_discounts = false;
        Activity.activity_sets = false;
        Activity.activity_menu = false;
        Activity.activity_register = false;
        Activity.activity_login = false;
        Activity.activity_main = false;


        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Корзина");

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        new LoadBasket(this).execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem)
    {
        int id = menuItem.getItemId();

        switch (id)
        {
            case R.id.nav_home:
                startActivity(new Intent(this, MainActivity.class));
                break;

            case R.id.nav_menu:
                startActivity(new Intent(this, Menu.class));
                break;

            case R.id.nav_sets:
                startActivity(new Intent(this, Sets.class));
                break;

            case R.id.nav_discounts:
                startActivity(new Intent(this, Discounts.class));
                break;

            case R.id.nav_basket:
                startActivity(new Intent(this, Basket.class));
                break;

            case R.id.nav_setting:
                startActivity(new Intent(this, Settings.class));
                break;

            case R.id.nav_out:
                startActivity(new Intent(this, Login.class));
                break;
        }

        return false;
    }

    public void ReloadBasketProduct()
    {
        final LinearLayout linear = findViewById(R.id.linear);
        ReloadBasketProduct basketProduct = new ReloadBasketProduct();

        if (!str.equals(""))
        {

            String[] str_id = str.split("\n");
            s_id = new int[str_id.length];
            s_count = new int[str_id.length];

            for (int i = 0; i < str_id.length; i++)
            {
                s_id[i] = Integer.parseInt(str_id[i].substring(0, str_id[i].indexOf(" ")));
                s_count[i] = Integer.parseInt(str_id[i].substring(str_id[i].indexOf(" ") + 1, str_id[i].length()));
                final View view = getLayoutInflater().inflate(R.layout.basket, null);
                basketProduct.ReloadBasketProduct(linear, view, s_id[i], s_count[i], str_id.length, i, this);
            }
        }
    }

    public void out(String Out)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            str = Out.substring(105);
            this.ReloadBasketProduct();

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
