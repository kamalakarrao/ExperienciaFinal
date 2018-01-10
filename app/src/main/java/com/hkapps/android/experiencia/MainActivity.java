package com.hkapps.android.experiencia;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.UUID;

import im.delight.android.location.SimpleLocation;


public class MainActivity extends AppCompatActivity {
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
    private static String url = "http://api.openweathermap.org/data/2.5/weather?lat=17.3457&lon=78.5522&mode=json&units=metric&cnt=1&appid=eb58cce858efc91908a747c48fd1cf81";
    ImageView cloud, rain, light, sun;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSocket mmSocket;
    BluetoothDevice mmDevice, device;
    OutputStream mmOutputStream;
    InputStream mmInputStream;
    Thread workerThread;
    byte[] readBuffer;
    int readBufferPosition;
    int counter;
    String type = "";
    String icon="";
    String place = "";
    int id,cl;
    boolean connect = false;
    volatile boolean stopWorker;
    SimpleLocation location;
    JSONArray mode, sss;
    JSONObject mode2;
    EditText locator;
    Button loc;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button loc = (Button) findViewById(R.id.loc);
        locator = (EditText) findViewById(R.id.locator);


        location = new SimpleLocation(this);

        // if we can't access the location yet
      /*  if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(this);
        }*/


        findBT();
        try {
            openBT();
        } catch (IOException ex) {
        }


        sun = (ImageView) findViewById(R.id.sun);
        rain = (ImageView) findViewById(R.id.rain);
        cloud = (ImageView) findViewById(R.id.cloud);
        light = (ImageView) findViewById(R.id.light);

        txt = (TextView) findViewById(R.id.locc);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }




       /* rain.setImageResource(R.drawable.rainoff);
        rain.setTag("off");
        cloud.setImageResource(R.drawable.cloudoff);
        cloud.setTag("off");
        light.setImageResource(R.drawable.lightningoff);
        light.setTag("off");
        sun.setImageResource(R.drawable.sunshineoff);
        sun.setTag("off");
        try {
            mmOutputStream.write('r');
            mmOutputStream.write('l');
            mmOutputStream.write('s');
            mmOutputStream.write('c');

        } catch (IOException ex) {
            Toast.makeText(getApplicationContext(),"Experiencia is Not Connected",Toast.LENGTH_SHORT).show();
        }



        JSONParser jParser = new JSONParser();
        JSONObject json = jParser.getJSONFromUrl(url);

        try {
            // Getting JSON Array
            mode = json.getJSONArray("weather");
            JSONObject c = mode.getJSONObject(0);

            // Storing  JSON item in a Variable
            type = c.getString("main");
            int id = c.getInt("id");

        } catch (JSONException e) {
            e.printStackTrace();
            //   return null;
        }



        if(type.equals("Mist")||type.equals("Clouds")||type.equals("Haze")||type.equals("smoke")||type.equals("fog"))
        {
            cloud.setImageResource(R.drawable.cloudon);
            cloud.setTag("on");
            try {
                mmOutputStream.write('C');
            } catch (IOException ex) {
            }


        }


        if(type.equals("Rain")||type.equals("Storm")||type.equals("thunderstorm")||type.equals("drizzle")){

            rain.setImageResource(R.drawable.rainon);
            rain.setTag("on");
            try {
                mmOutputStream.write('R');
            } catch (IOException ex) {
            }

        }

        if(type.equals("clear")||type.equals("hot"))
        {

            sun.setImageResource(R.drawable.sunshineon);
            sun.setTag("on");
            try {
                mmOutputStream.write('S');
            } catch (IOException ex) {
            }

        }


        if(type.equals("Storm")||type.equals("thunderstorm")){


            light.setImageResource(R.drawable.lightningon);
            light.setTag("on");
            try {
                mmOutputStream.write('L');
            } catch (IOException ex) {
            }
        }*/


        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (sun.getTag() != null && sun.getTag().toString().equals("on")) {
                    sun.setImageResource(R.drawable.sunshineoff);
                    sun.setTag("off");
                    try {
                        mmOutputStream.write('s');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Turn On Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    sun.setImageResource(R.drawable.sunshineon);
                    sun.setTag("on");
                    light.setImageResource(R.drawable.lightningoff);
                    light.setTag("off");
                    try {
                        mmOutputStream.write('S');
                        mmOutputStream.write('l');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Turn On Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


        rain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rain.getTag() != null && rain.getTag().toString().equals("on")) {
                    rain.setImageResource(R.drawable.rainoff);
                    rain.setTag("off");
                    try {
                        mmOutputStream.write('r');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Turn On Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    rain.setImageResource(R.drawable.rainon);
                    rain.setTag("on");
                    try {
                        mmOutputStream.write('R');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Turn On Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (light.getTag() != null && light.getTag().toString().equals("on")) {
                    light.setImageResource(R.drawable.lightningoff);
                    light.setTag("off");
                    try {
                        mmOutputStream.write('l');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Turn On Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    light.setImageResource(R.drawable.lightningon);
                    light.setTag("on");
                    sun.setImageResource(R.drawable.sunshineoff);
                    sun.setTag("off");
                    try {
                        mmOutputStream.write('L');
                        mmOutputStream.write('s');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Turn On Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


        cloud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cloud.getTag() != null && cloud.getTag().toString().equals("on")) {
                    cloud.setImageResource(R.drawable.cloudoff);
                    cloud.setTag("off");
                    try {
                        mmOutputStream.write('c');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Turn On Bluetooth", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    cloud.setImageResource(R.drawable.cloudon);
                    cloud.setTag("on");
                    try {
                        mmOutputStream.write('C');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Turn On Bluetooth", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //  findBT();

  /*      if(!mmDevice.getName().equals("HC-05")){
            Intent refresh = new Intent(this, MainActivity.class);
            startActivity(refresh);
        }
    */   /* try {

            openBT();
        } catch (IOException ex) {
        }*/
        loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(locator.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);

                String place = locator.getText().toString().replaceAll(" ","");


                url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + place + "&mode=json&units=metric&cnt=1&appid=eb58cce858efc91908a747c48fd1cf81";


                rain.setImageResource(R.drawable.rainoff);
                rain.setTag("off");
                cloud.setImageResource(R.drawable.cloudoff);
                cloud.setTag("off");
                light.setImageResource(R.drawable.lightningoff);
                light.setTag("off");
                sun.setImageResource(R.drawable.sunshineoff);
                sun.setTag("off");
                try {
                    mmOutputStream.write('r');
                    mmOutputStream.write('l');
                    mmOutputStream.write('s');
                    mmOutputStream.write('c');
                    mmOutputStream.write('m');

                } catch (IOException ex) {
                    Toast.makeText(getApplicationContext(), "Experiencia is Not Connected", Toast.LENGTH_SHORT).show();
                }


                JSONParser jParser = new JSONParser();
                JSONObject json = jParser.getJSONFromUrl(url);

                try {
                    // Getting JSON Array
                    String weather = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("main");

                    id = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getInt("id");
                    //  mode2 = mode.getJSONObject(4);

                    // sss =mode2.getJSONArray("weather");
                    icon = json.getJSONArray("list").getJSONObject(0).getJSONArray("weather").getJSONObject(0).getString("icon");

                    cl = json.getJSONArray("list").getJSONObject(0).getInt("clouds");

                    String city = json.getJSONObject("city").getString("name");


                    //JSONObject c = sss.getJSONObject(0);

                    txt.setText(city);

                    // Storing  JSON item in a Variable
              //      type = weather;

                    // int id = c.getInt("id");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Oops! Network Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    //   return null;
                }


            /*    if (type.equals("Mist") || type.equals("Clouds") || type.equals("Haze") || type.equals("Smoke") || type.equals("F3og")) {
                    cloud.setImageResource(R.drawable.cloudon);
                    cloud.setTag("on");
                    try {
                        mmOutputStream.write('C');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Connect to Experiencia", Toast.LENGTH_SHORT).show();
                    }


                }


                if (type.equals("Rain") || type.equals("Storm") || type.equals("Thunderstorm") || type.equals("Drizzle")) {

                    rain.setImageResource(R.drawable.rainon);
                    rain.setTag("on");
                    try {
                        mmOutputStream.write('R');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Connect to Experiencia", Toast.LENGTH_SHORT).show();
                    }

                }

                if (type.equals("clear") || type.equals("Hot") || type.equals("Clear")) {

                    sun.setImageResource(R.drawable.sunshineon);
                    sun.setTag("on");
                    try {
                        mmOutputStream.write('S');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Connect to Experiencia", Toast.LENGTH_SHORT).show();
                    }

                }


                if (type.equals("Storm") || type.equals("Thunderstorm")) {


                    light.setImageResource(R.drawable.lightningon);
                    light.setTag("on");
                    try {
                        mmOutputStream.write('L');
                    } catch (IOException ex) {
                        Toast.makeText(getApplicationContext(), "Connect to Experiencia", Toast.LENGTH_SHORT).show();
                    }
                } */


                try {



                    if(icon.contains("n")){

                        mmOutputStream.write('M');
                    }
                    if(icon.contains("d")){
                        mmOutputStream.write('m');
                    }

                    if(cl > 60 ) {


                        mmOutputStream.write('C');
                        cloud.setImageResource(R.drawable.cloudon);
                        cloud.setTag("on");
                    }
                    switch (id) {

                        case 200:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;

                        case 201:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;

                        case 202:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;


                        case 210:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');
                            break;


                        case 211:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');
                            break;

                        case 212:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');
                            break;

                        case 221:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');
                            break;

                        case 230:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;

                        case 231:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;

                        case 232:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;


                        case 300:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 301:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 302:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 310:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 311:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 312:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 313:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 314:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 321:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;


                        case 500:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;


                        case 501:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 502:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 503:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 504:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 511:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;
                        case 520:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;
                        case 521:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;
                        case 522:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;
                        case 531:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;


                        case 600:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');

                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;


                        case 601:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 602:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 611:
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 612:
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 615:
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 616:
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 620:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 621:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 622:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 701:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 711:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 721:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 731:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 741:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 751:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;
                        case 761:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            break;
                        case 762:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;
                        case 771:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;
                        case 781:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 800:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            break;

                        case 801:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 802:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 803:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 804:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 900:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 901:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 902:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 903:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 904:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 905:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');

                            break;

                        case 951:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');


                            break;

                        case 952:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 953:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 954:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;
                        case 955:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 956:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 957:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 958:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 959:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 960:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');

                            break;

                        case 961:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');

                            break;

                        case 962:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');

                            break;


                    }

                } catch (IOException ex) {

                    Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();


                }

                Toast.makeText(getApplicationContext(), "Syncing with weather", Toast.LENGTH_SHORT).show();

            }
        });







       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!location.hasLocationEnabled())
                    SimpleLocation.openSettings(getApplicationContext());

                float lat = (float) location.getLatitude();
                float lon = (float) location.getLongitude();

                url = "http://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&mode=json&units=metric&cnt=1&appid=eb58cce858efc91908a747c48fd1cf81";


                rain.setImageResource(R.drawable.rainoff);
                rain.setTag("off");
                cloud.setImageResource(R.drawable.cloudoff);
                cloud.setTag("off");
                light.setImageResource(R.drawable.lightningoff);
                light.setTag("off");
                sun.setImageResource(R.drawable.sunshineoff);
                sun.setTag("off");
                try {
                    mmOutputStream.write('r');
                    mmOutputStream.write('l');
                    mmOutputStream.write('s');
                    mmOutputStream.write('c');
                    mmOutputStream.write('m');

                } catch (IOException ex) {
                    Toast.makeText(getApplicationContext(),"Experiencia is Not Connected",Toast.LENGTH_SHORT).show();
                }



                JSONParser jParser = new JSONParser();
                JSONObject json = jParser.getJSONFromUrl(url);

                try {
                    // Getting JSON Array
                    id = json.getJSONArray("weather").getJSONObject(0).getInt("id");
                     icon = json.getJSONArray("weather").getJSONObject(0).getString("icon");
                    //  mode2 = mode.getJSONObject(4);

                    // sss =mode2.getJSONArray("weather");
                    cl = json.getJSONObject("clouds").getInt("all");

                    String city = json.getString("name");


                    //JSONObject c = sss.getJSONObject(0);

                    txt.setText(city);

                    // Storing  JSON item in a Variable
                    //      type = weather;

                    // int id = c.getInt("id");

                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Oops! Network Error", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                    //   return null;
                }


                try {

                    if(icon.contains("n")){

                        mmOutputStream.write('M');
                    }
                    if(icon.contains("d")){
                        mmOutputStream.write('m');
                    }


                    if(cl > 60 ) {


                        mmOutputStream.write('C');
                        cloud.setImageResource(R.drawable.cloudon);
                        cloud.setTag("on");
                    }
                    switch (id) {

                        case 200:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;

                        case 201:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;

                        case 202:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;


                        case 210:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');
                            break;


                        case 211:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');
                            break;

                        case 212:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');
                            break;

                        case 221:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');
                            break;

                        case 230:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;

                        case 231:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;

                        case 232:
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('L');
                            mmOutputStream.write('R');
                            break;


                        case 300:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 301:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 302:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 310:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 311:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 312:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 313:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 314:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 321:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;


                        case 500:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;


                        case 501:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 502:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 503:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 504:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;

                        case 511:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;
                        case 520:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;
                        case 521:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;
                        case 522:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;
                        case 531:

                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            break;


                        case 600:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');

                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;


                        case 601:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 602:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 611:
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 612:
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 615:
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 616:
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");

                            mmOutputStream.write('R');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 620:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 621:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 622:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            Toast.makeText(getApplicationContext(),"Snow!!",Toast.LENGTH_SHORT).show();
                            break;

                        case 701:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 711:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 721:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 731:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 741:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 751:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;
                        case 761:

                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            break;
                        case 762:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;
                        case 771:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;
                        case 781:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 800:

                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');
                            break;

                        case 801:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 802:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 803:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 804:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 900:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 901:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 902:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 903:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 904:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            light.setImageResource(R.drawable.lightningon);
                            light.setTag("on");
                            mmOutputStream.write('L');

                            break;

                        case 905:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');

                            break;

                        case 951:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');


                            break;

                        case 952:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 953:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 954:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;
                        case 955:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 956:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 957:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 958:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;

                        case 959:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            break;


                        case 960:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');

                            break;

                        case 961:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');

                            break;

                        case 962:


                            cloud.setImageResource(R.drawable.cloudon);
                            cloud.setTag("on");
                            mmOutputStream.write('C');
                            rain.setImageResource(R.drawable.rainon);
                            rain.setTag("on");
                            mmOutputStream.write('R');
                            sun.setImageResource(R.drawable.sunshineon);
                            sun.setTag("on");
                            mmOutputStream.write('S');

                            break;


                    }

                } catch (IOException ex) {

                    Toast.makeText(getApplicationContext(), "Connection Failed", Toast.LENGTH_SHORT).show();


                }





                Toast.makeText(getApplicationContext(),"Syncing with weather",Toast.LENGTH_SHORT).show();

            }
        });




    }

    public void loc(View view) {

        startActivity(new Intent(MainActivity.this, LocationActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "No bluetooth adapter available", Toast.LENGTH_SHORT).show();
        }

        if (mBluetoothAdapter.isEnabled()) {
        } else {
            //Ask to the user turn the bluetooth on
            mBluetoothAdapter.enable();

        }

        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                if (device.getName().equals("Experiencia")) {
                    mmDevice = device;

                    break;

                }
            }
        }

        Toast.makeText(this, "Bluetooth Device Found", Toast.LENGTH_SHORT).show();


    }

    void openBT() throws IOException {
        UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb"); //Standard //SerialPortService ID

        mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        mmSocket.connect();
        mmOutputStream = mmSocket.getOutputStream();
        mmInputStream = mmSocket.getInputStream();
        beginListenForData();

        Toast.makeText(this, "Bluetooth Device Connected", Toast.LENGTH_SHORT).show();

    }

    void beginListenForData() {
        final Handler handler = new Handler();
        final byte delimiter = 10; //This is the ASCII code for a newline character

        stopWorker = false;
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        workerThread = new Thread(new Runnable() {
            public void run() {
                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int bytesAvailable = mmInputStream.available();
                        if (bytesAvailable > 0) {
                            byte[] packetBytes = new byte[bytesAvailable];
                            mmInputStream.read(packetBytes);
                            for (int i = 0; i < bytesAvailable; i++) {
                                byte b = packetBytes[i];
                                if (b == delimiter) {
                                    byte[] encodedBytes = new byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;

                                    handler.post(new Runnable() {
                                        public void run() {
                                            // Toast.makeText(this,data,Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (IOException ex) {
                        stopWorker = true;
                    }
                }
            }
        });

        workerThread.start();
    }

    public class JSONParser {


        // constructor
        public JSONParser() {

        }

        public JSONObject getJSONFromUrl(String url) {

            // Making HTTP request
            try {
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);

                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        is, "iso-8859-1"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "n");
                }
                is.close();
                json = sb.toString();
            } catch (Exception e) {
                Log.e("Buffer Error", "Error converting result " + e.toString());
            }

            // try parse the string to a JSON object
            try {
                jObj = new JSONObject(json);
            } catch (JSONException e) {
                Log.e("JSON Parser", "Error parsing data " + e.toString());
            }

            // return JSON String
            return jObj;

        }
    }


}



