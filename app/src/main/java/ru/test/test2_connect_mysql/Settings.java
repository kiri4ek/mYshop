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

public class Settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    public ActionBarDrawerToggle mToggle;
    public DrawerLayout mDrawerLayout;
    public NavigationView navigationView;
    public MenuItem home, menu, sets, discounts, basket;
    private String NameClient, SurnameClient, Email, Pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Activity.activity_main = true;

        Activity.activity_basket = false;
        Activity.activity_discounts = false;
        Activity.activity_sets = false;
        Activity.activity_menu = false;
        Activity.activity_register = false;
        Activity.activity_login = false;

        mDrawerLayout = findViewById(R.id.drawer_layout_settings);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setTitle("Настройки");

        navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);

        new LoadSettings(this).execute();

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

    public void ReloadSettings()
    {
        final LinearLayout linear = findViewById(R.id.linear_settings);
        final View view = getLayoutInflater().inflate(R.layout.settings, null);

        SetSetting setSetting = new SetSetting();

        setSetting.setName(NameClient, view, this);
        setSetting.setSurname(SurnameClient);
        setSetting.setEmail(Email);
        setSetting.setPass(Pass);
        setSetting.setButtonSave();

        //Будущее обновление в базе данных clients добавить тип оплаты и здесь изменять
        setSetting.setTypePay(Client.TypeOfPayment);
        //Client.TypeOfPayment = setSetting.getTypePay();
        //----------------------------------------------------------

        linear.addView(view);

    }

    public void out(String Out)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            String vivod1, vivod2;

            vivod1 = Out.substring(105);
            NameClient = vivod1.substring(0, vivod1.indexOf("\n"));

            vivod2 = vivod1.substring(NameClient.length() + 1);
            SurnameClient = vivod2.substring(0, vivod2.indexOf("\n"));

            vivod1 = vivod2.substring(SurnameClient.length() + 1);
            Email = vivod1.substring(0, vivod1.indexOf("\n"));

            vivod2 = vivod1.substring(Email.length() + 1);
            Pass = vivod2.substring(0, vivod2.indexOf("\n"));

            this.ReloadSettings();
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}