package ru.test.test2_connect_mysql;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.support.v4.app.INotificationSideChannel;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener
{

    private EditText etEmail, etPassword;
    private CardView bLogin;
    private TextView tvRegisterLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // установка главноего View
        setContentView(R.layout.activity_login);

        //для проверки действующего окна
        Activity.activity_login = true;

        Activity.activity_basket = false;
        Activity.activity_discounts = false;
        Activity.activity_sets = false;
        Activity.activity_menu = false;
        Activity.activity_register = false;
        Activity.activity_main = false;

        getSupportActionBar().hide();

        // связка полей из активити к классу Login
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        bLogin = findViewById(R.id.bLogin);
        tvRegisterLink = findViewById(R.id.tvRegisterLink);

        // привязка событий к клавишам
        bLogin.setOnClickListener(this);
        tvRegisterLink.setOnClickListener(this);

    }

    // обработчик события нажатия клавиш
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            // нажали войти
            case R.id.bLogin:
                // считывание введенных полей
                String Email = etEmail.getText().toString(), Password = etPassword.getText().toString();

                // запрос серверу на вход текущему пользователю
                new LoginClient(Email, Password, this).execute();
                break;

            // нажали авторизироваться
            case R.id.tvRegisterLink:
                startActivity(new Intent(this, Register.class));
                break;
        }
    }

    // выходной метод после AsyncTask - LoginClient
    public void out(String Out)
    {
        // проверка на доступность подключения к серверу
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            //обработка полученного ответа от сервера
            String vivod0, vivod1, Message, podtv;

            vivod0 = Out.substring(105);
            Message = vivod0.substring(0, vivod0.indexOf("\n"));

            vivod1 = vivod0.substring(Message.length() + 1);
            podtv = vivod1.substring(0, vivod1.indexOf("\n"));

            if (Objects.equals(podtv, "1")) {
                vivod0 = vivod1.substring(podtv.length() + 1);
                Client.id = Integer.parseInt(vivod0.substring(0, vivod0.indexOf("\n")));

                Toast toast = Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG);
                toast.show();
                startActivity(new Intent(this, MainActivity.class));

            } else {
                System.out.println(Message);
                Toast toast = Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
