package ru.test.test2_connect_mysql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.INotificationSideChannel;
import android.os.Bundle;
import android.text.TextWatcher;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Switch;
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
import java.util.regex.Pattern;

public class SetSetting extends AppCompatActivity implements View.OnClickListener
{
    public EditText Name, Surname, Email, Pass;
    public String Past_Name, Past_Surname, Past_Email, Past_Pass;
    public Switch Sw;
    public View view1;
    public CardView buttonSave;
    public Context context;
    public Settings S;
    public SetSetting SS;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
    }


    public String getName() {
        return this.Name.getText().toString();
    }

    public void setName(String name, View view, Settings s) {
        this.SS = this;
        this.context = s;
        this.S = s;
        this.view1 = view;
        Past_Name = name;
        Name = view.findViewById(R.id.Name);
        Name.setText(name);
    }

    public String getSurname() {
        Surname = view1.findViewById(R.id.Surname);
        return Surname.getText().toString();
    }

    public void setSurname(String surname) {
        Past_Surname = surname;
        Surname = view1.findViewById(R.id.Surname);
        Surname.setText(surname);
    }

    public String getEmail() {
        Email = view1.findViewById(R.id.Email);
        return Email.getText().toString();
    }

    public void setEmail(String email) {
        Past_Email = email;
        Email = view1.findViewById(R.id.Email);
        Email.setText(email);
    }

    public String getPass() {
        Pass = view1.findViewById(R.id.Password);
        return Pass.getText().toString();
    }

    public void setPass(String pass) {
        Past_Pass = pass;
        Pass = view1.findViewById(R.id.Password);
        Pass.setText(pass);
    }

    public Boolean getTypePay(){
        Sw = view1.findViewById(R.id.Type_pay);
        return Sw.getSplitTrack();
    }

    public void setTypePay(Boolean typePay){
        Sw = view1.findViewById(R.id.Type_pay);
        Sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                String type = "Наличный";

                if(isChecked){
                    Client.TypeOfPayment = true;
                    type = "Безналичный";

                } else {
                    Client.TypeOfPayment = false;
                }

                Client.CountTypeOfPayment++;
                new EditTypeOfPayment(type, SS).execute();
            }
        });

        Sw.setChecked(typePay);
    }

    public int getButtonSaveId(){
        return this.buttonSave.getId();
    }

    public void setButtonSave(){
        buttonSave = view1.findViewById(R.id.ButtonSave);
        buttonSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.ButtonSave:
                String Name = getName(), Surname = getSurname(), Email = getEmail(), Password = getPass();
                String Out = new String();
                String patternName = "^([А-Я]{1}[а-яё]{1,23}|[A-Z]{1}[a-z]{1,23})$";
                String patternSurname = patternName;
                String patternEmail = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";

                if (Pattern.matches(patternName, Name)) {
                    if (Pattern.matches(patternSurname, Surname)) {
                        if (Pattern.matches(patternEmail, Email)) {
                            if (!Objects.equals(Password, "") && (Password.length() >= 8)) {
                                if (!Objects.equals(Name, Past_Name) || !Objects.equals(Surname, Past_Surname) || !Objects.equals(Email, Past_Email) || !Objects.equals(Password, Past_Pass)) {

                                    new EditClient(Name, Surname, Email, Password, Out, this, this.context).execute();

                                } else {
                                    Toast toast = Toast.makeText(context, "Cохранилось без изменений", Toast.LENGTH_SHORT);
                                    toast.show();
                                }

                            } else if (Objects.equals(Password, "")) {
                                Toast toast = Toast.makeText(context, "Введите Пароль!", Toast.LENGTH_LONG);
                                toast.show();
                            } else if (Password.length() < 8) {
                                Toast toast = Toast.makeText(context, "Длина пароля должна составлять не менее 8 символов!", Toast.LENGTH_LONG);
                                toast.show();
                            }

                        } else if (Objects.equals(Email, "")) {
                            Toast toast = Toast.makeText(context, "Введите Email!", Toast.LENGTH_LONG);
                            toast.show();
                        } else if (!Pattern.matches(patternEmail, Email)) {
                            Toast toast = Toast.makeText(context, "Некорректный формат email! Принимается формат типа: ****@mail.ru", Toast.LENGTH_LONG);
                            toast.show();
                        }

                    } else if (Objects.equals(Surname, "")) {
                        Toast toast = Toast.makeText(context, "Введите Фамилию!", Toast.LENGTH_LONG);
                        toast.show();
                    } else if (!Pattern.matches(patternSurname, Surname)) {
                        Toast toast = Toast.makeText(context, "Некорректный формат Фамилии! Принимаются начиная с большой буквы Рус/Англ", Toast.LENGTH_LONG);
                        toast.show();
                    }

                } else if (Objects.equals(Name, "")) {
                    Toast toast = Toast.makeText(context, "Введите Имя!", Toast.LENGTH_LONG);
                    toast.show();
                } else if (!Pattern.matches(patternName, Name)) {
                    Toast toast = Toast.makeText(context, "Некорректный формат Имени! Принимаются начиная с большой буквы Рус/Англ", Toast.LENGTH_LONG);
                    toast.show();
                }

                break;
        }
    }

    public void outEditClient(String Out)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            String Message;

            Message = Out.substring(105);
            Message = Message.substring(0, Message.indexOf("\n"));

            Intent intent = new Intent(S, Settings.class);

            S.startActivity(intent);

            Toast toast = Toast.makeText(context, Message, Toast.LENGTH_LONG);
            toast.show();
        } else {
            Toast toast = Toast.makeText(context, "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void outEditTypePay(String Out)
    {
        if (!Objects.equals(Out,"Exception: Unable to resolve host \"xoxol1898.000webhostapp.com\": No address associated with hostname")) {

            String Message;

            Message = Out.substring(105);
            Message = Message.substring(0, Message.indexOf("\n"));

            Intent intent = new Intent(S, Settings.class);
        } else {
            Toast toast = Toast.makeText(context, "Отсутствует подключение к серверу проверьте подключение к интернету", Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
