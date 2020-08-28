package ru.test.test2_connect_mysql;
import androidx.appcompat.app.AppCompatActivity;
import android.support.v4.app.INotificationSideChannel;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;
import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener
{

    private EditText etUsername, etSurname, etEmail, etPassword;
    private CardView bRegister;
    private TextView tvLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // установка главноего View
        setContentView(R.layout.activity_register);

        //для проверки действующего окна
        Activity.activity_register = true;

        Activity.activity_basket = false;
        Activity.activity_discounts = false;
        Activity.activity_sets = false;
        Activity.activity_menu = false;
        Activity.activity_login = false;
        Activity.activity_main = false;

        getSupportActionBar().hide();

        // связка полей из активити к классу Register
        etUsername = findViewById(R.id.etUsername);
        etSurname = findViewById(R.id.etSurname);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        bRegister = findViewById(R.id.bRegister);
        tvLoginLink = findViewById(R.id.tvLoginLink);

        // привязка событий к клавишам
        bRegister.setOnClickListener(this);
        tvLoginLink.setOnClickListener(this);
    }

    // обработчик события нажатия клавиш
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            // нажали зарегистрироваться
            case R.id.bRegister:

                // считывание полей и добавление регулярок
                String Username = etUsername.getText().toString(), Surname = etSurname.getText().toString() ,Email = etEmail.getText().toString(), Password = etPassword.getText().toString();
                String Out = new String();
                String patternName = "^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$";
                String patternSurname = patternName;
                String patternLogin = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

                // проверки на регулярки и на пустоты
                if (Pattern.matches(patternName, Username))
                {
                    if (Pattern.matches(patternSurname, Surname))
                    {
                        if(Pattern.matches(patternLogin, Email))
                        {
                            if (!Objects.equals(Password,"") && (Password.length() >= 8))
                            {
                                // запрос серверу на регистрацию нового пользователя
                                new RegisterClient(Username, Surname, Email, Password, Out, this).execute();

                            }
                            else if (Objects.equals(Password,""))
                            {
                                Toast toast = Toast.makeText(getApplicationContext(), "Введите Пароль!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                            else if (Password.length() < 8)
                            {
                                Toast toast = Toast.makeText(getApplicationContext(), "Длина пароля должна составлять не менее 8 символов!", Toast.LENGTH_LONG);
                                toast.show();
                            }
                        }
                        else if (Objects.equals(Email, ""))
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Введите Email!", Toast.LENGTH_LONG);
                            toast.show();
                        }
                        else if (!Pattern.matches(patternLogin, Email))
                        {
                            Toast toast = Toast.makeText(getApplicationContext(), "Некорректный формат email! Принимается формат типа: ****@mail.ru", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    }
                    else if (Objects.equals(Surname, ""))
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Введите Фамилию!", Toast.LENGTH_LONG);
                        toast.show();
                    }
                    else if (!Pattern.matches(patternSurname, Surname))
                    {
                        Toast toast = Toast.makeText(getApplicationContext(), "Некорректный формат Фамилии! Принимаются начиная с большой буквы Рус/Англ", Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                else if (Objects.equals(Username, ""))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Введите Имя!", Toast.LENGTH_LONG);
                    toast.show();
                }
                else if (!Pattern.matches(patternName, Username))
                {
                    Toast toast = Toast.makeText(getApplicationContext(), "Некорректный формат Имени! Принимаются начиная с большой буквы Рус/Англ", Toast.LENGTH_LONG);
                    toast.show();
                }

                break;

            // нажали авторизироваться
            case  R.id.tvLoginLink:
                startActivity(new Intent(this, Login.class));
                break;
        }
    }

    // выходной метод после AsyncTask - RegisterClient
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
                Toast toast = Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG);
                toast.show();
                startActivity(new Intent(this, Login.class));

            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Извините регистрация в данный момент не возможна, повторите попытку позже", Toast.LENGTH_LONG);
                toast.show();
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
