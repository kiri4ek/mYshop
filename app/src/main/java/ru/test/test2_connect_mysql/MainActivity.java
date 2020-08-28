package ru.test.test2_connect_mysql;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{


    private String id;
    public int[] s_id;
    public ActionBarDrawerToggle mToggle;
    public DrawerLayout mDrawerLayout;
    public NavigationView navigationView;
    public MenuItem home, menu, sets, discounts, basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        Activity.activity_main = true;

        Activity.activity_basket = false;
        Activity.activity_discounts = false;
        Activity.activity_sets = false;
        Activity.activity_menu = false;
        Activity.activity_register = false;
        Activity.activity_login = false;

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Главная");

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        Double chance1 = 0.0, chance2 = 0.5;
        String type = "Стандарт";

        new KolProductChance(type, chance1, chance2, this).execute();

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

    public void ReloadProduct()
    {
        final LinearLayout linear = findViewById(R.id.linear);
        ReloadGlobalProduct glob = new ReloadGlobalProduct();

        if (!id.equals(""))
        {

            String[] str_id = id.split(" ");
            s_id = new int[str_id.length];

            for (int i = 0; i < s_id.length; i++)
            {
                s_id[i] = Integer.parseInt(str_id[i]);
                final View view = getLayoutInflater().inflate(R.layout.product, null);
                glob.ReloadGlobalProduct(linear, view, s_id[i], Color.rgb(100,34,25), Color.rgb(240,240,240), this);
            }
        }
    }

    public void out(String Out)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            String vivod0;

            vivod0 = Out.substring(105);

            id = vivod0.substring(0, vivod0.indexOf("\n"));

            this.ReloadProduct();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}