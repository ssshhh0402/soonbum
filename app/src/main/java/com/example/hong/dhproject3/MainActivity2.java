package com.example.hong.dhproject3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ActionMenuView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.readystatesoftware.viewbadger.BadgeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity2 extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private static final String TAG = MainActivity2.class.getSimpleName();
    private ListView listView;
    private DevicesAdapter adapter;
    private List<Devices> deviceList;
    private FloatingActionButton fab;
    String id,input,email,nick;
    Boolean status = false;
    Button bt1,bt2;
    TextView tv1,tv2;
    ImageView iv1;
    int count ;
    private final long FINISH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;
    private  String dList_Url,addList_Url,dDevice_Url = "";

    /*static final int REQUEST_ENABLE_BT = 10;
    int mPariedDeviceCount = 0;
    Set<BluetoothDevice> mDevices;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice mRemoteDevice;
    BluetoothSocket mSocket = null;
    OutputStream mOutputStream = null;
    InputStream mInputStream = null;
    String mStrDelimiter = "\n";
    char mCharDelimiter = '\n';
    Thread mWorkerThread = null;
    byte[] readBuffer;
    int readBufferPosition;
    String id;
    // 블루투스 연결용*/

    @Override
    protected void onResume() {
        Log.d("Responsedata onResume", "onResume 실행된다");
        setDefault();
        setBadge();
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View v = navigationView.getHeaderView(0);
        tv1 = (TextView)v.findViewById(R.id.tv2);
        tv2 = (TextView)v.findViewById(R.id.d_number);
        bt1 = (Button)v.findViewById(R.id.button1);
        bt2 = (Button)v.findViewById(R.id.button2);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                final String password = auto.getString("PW", "");
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity2.this);
                ad.setTitle("비밀번호 입력");
                ad.setMessage("비밀번호를 입력해주세요");
                final EditText et = new EditText(MainActivity2.this);
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ad.setView(et);
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input = et.getText().toString();
                        dialogInterface.dismiss();
                        if(input.equals(password)){
                            Intent intent = new Intent(MainActivity2.this, editPage.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity2.this,"비밀번호를 잘못 입력하셨습니다", Toast.LENGTH_LONG).show();
                            dialogInterface.dismiss();
                        }
                    }
                });
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                ad.show();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        /*iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                final String password = auto.getString("PW", "");
                AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity2.this);
                ad.setTitle("비밀번호 입력");
                ad.setMessage("비밀번호를 입력해주세요");
                final EditText et = new EditText(MainActivity2.this);
                et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                ad.setView(et);
                ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        input = et.getText().toString();
                        dialogInterface.dismiss();
                        if(input.equals(password)){
                            Intent intent = new Intent(MainActivity2.this, editPage.class);
                            startActivity(intent);
                        }
                        else{
                            Toast.makeText(MainActivity2.this,"비밀번호를 잘못 입력하셨습니다", Toast.LENGTH_LONG).show();
                            dialogInterface.dismiss();
                        }
                    }
                });
                ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                ad.show();
            }
        });*/
        fab = (FloatingActionButton) findViewById(R.id.fab);
      //  FirebaseInstanceId.getInstance().getToken();
        dList_Url = getResources().getString(R.string.base_Url)+"get_userdevice_list";
        dDevice_Url = getResources().getString(R.string.base_Url)+"device_delete";
        addList_Url = getResources().getString(R.string.base_Url)+"ud_create";

        if(FirebaseInstanceId.getInstance().getToken() != null){
            Log.d(TAG, "TOKEN = " + FirebaseInstanceId.getInstance().getToken());
        }
        listView = (ListView) findViewById(R.id.listview1);
        registerForContextMenu(listView);
        deviceList = new ArrayList<Devices>();
        adapter = new DevicesAdapter(getApplicationContext(),deviceList);
        listView.setAdapter(adapter);
        setDefault();
        forBeginner();

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                String a = auto.getString("u_id", "");
                String b = Integer.toString(deviceList.get(i).getDeviceId());
                String c = deviceList.get(i).getUserType();
                String d = deviceList.get(i).get_master();
                Intent intent2 = new Intent(getApplicationContext(), Main4Activity.class);
                intent2.putExtra("U_ID",a);
                intent2.putExtra("D_ID",b);
                //intent2.putExtra("USER_TYPE", c);
                intent2.putExtra("MASTER", d);
                startActivity(intent2);
            }
        });




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             show();


            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.myInfo){
            SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = auto.edit();
            final String password = auto.getString("PW", "");
            AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity2.this);
            ad.setTitle("비밀번호 입력");
            ad.setMessage("비밀번호를 입력해주세요");
            final EditText et = new EditText(MainActivity2.this);
            et.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            ad.setView(et);
            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    input = et.getText().toString();
                    dialogInterface.dismiss();
                    if(input.equals(password)){
                        Intent intent = new Intent(MainActivity2.this, editPage.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity2.this,"비밀번호를 잘못 입력하셨습니다", Toast.LENGTH_LONG).show();
                        dialogInterface.dismiss();
                    }
                }
            });
            ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            ad.show();
        }
        else if(id==R.id.logOut2){
            logOut();
        }
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                setBadge();
            }
        });
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.allAlarm:
                Intent intent = getIntent();
                String a= intent.getStringExtra("ID");
                Intent intent2 = new Intent(getApplicationContext(), allAlarm.class);
                intent2.putExtra("U_ID", a);
                startActivity(intent2);
                break;
                }
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuInflater inflater=  getMenuInflater();
            inflater.inflate(R.menu.device_menu, menu);
            super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()){
            case R.id.m1:
                final EditText et = new EditText(MainActivity2.this);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("기기 코드");
                int a=  ((AdapterView.AdapterContextMenuInfo)info).position;
                String b = deviceList.get(a).getD_code();
                et.setText(b);
                et.requestFocus();
                et.setClickable(true);
                et.setSelectAllOnFocus(true);
                et.setGravity(Gravity.CENTER);
                builder.setView(et);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.show();
                break;
            case R.id.m2:
                Intent intent = getIntent();
                final String u_id= intent.getStringExtra("ID");
                int c=  ((AdapterView.AdapterContextMenuInfo)info).position;
                final int d_id = deviceList.get(c).getDeviceId();
                AlertDialog.Builder builder2 = new AlertDialog.Builder(MainActivity2.this);
                builder2.setTitle("기기 삭제");
                builder2.setMessage("기기를 삭제하시겠습니까?");
                builder2.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                       deleteDevice(u_id, d_id);

                       dialogInterface.dismiss();
                    }
                });
                builder2.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder2.show();
                break;
                }

        return super.onContextItemSelected(item);
    }
    public void show(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog, null);
            builder.setView(view);
            final LinearLayout linear1 = (LinearLayout) view.findViewById(R.id.linear1);
            final LinearLayout linear2 = (LinearLayout)view.findViewById(R.id.linear2);
            final Button bt1 = (Button)view.findViewById(R.id.btn1);
            final AlertDialog dialog= builder.create();
            linear1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    show2();
                    dialog.dismiss();
                }
            });
            bt1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
            dialog.show();
    }

    public void setBadge() {
        final View target = findViewById(R.id.allAlarm);
        SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor save = auto.edit();
        int a = auto.getInt("count", 0);
        Log.d("ResponseData count2", a + "");
        Log.d("ResponseData Badge22", "뱃지구현되었습니다");
        if (a != 0) {
            BadgeView badge = new BadgeView(MainActivity2.this, target);
            String b = Integer.toString(a);
            badge.setText(b);
            badge.setBackground(getDrawable(R.drawable.badge_background));
            badge.setTextColor(Color.RED);
            badge.setBadgePosition(BadgeView.POSITION_TOP_RIGHT);
            badge.setTextSize(12);
            badge.show();
        }
        else{
            BadgeView badge = new BadgeView(MainActivity2.this, target);
            badge.hide();
        }
    }
    public void show2(){
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity2.this);
        ad.setTitle("기기 추가");
        ad.setMessage("기기 코드를 입력해 주세요");
        final EditText et = new EditText(MainActivity2.this);
        ad.setView(et);
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                String value = et.getText().toString();
                addList2(value);
                dialog.dismiss();
            }
        } );
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        ad.show();
    }
    public void addList2(String a){
        Intent intent = getIntent();
        String u_id = intent.getStringExtra("ID");
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", u_id).add("d_code",a).build();
        Request request = new Request.Builder().url(addList_Url).post(formBody).build();
        client.newCall(request).enqueue(addList2Callback);
    }
    public Callback addList2Callback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {
        }
        //status, message, data(id,d_name)
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responseData = response.body().string();
            Log.d("RESPONSEDATA 123456", responseData);
            try {
                JSONObject ja = new JSONObject(responseData);
                JSONObject jo = ja.getJSONObject("data");
                SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                String u_id = auto.getString("u_id", "");
                JSONObject order = new JSONObject(responseData);
                int deviceId = jo.getInt("id");
                String deviceName = jo.getString("d_name");
                String nickName = jo.getString("nickname");
                String user_type = jo.getString("user_type");
                String d_code = jo.getString("d_code");
                String master = jo.getString("master");
                Boolean aAlarm = jo.getBoolean("v_toggle");
                Boolean bAlarm = jo.getBoolean("t_toggle");
                Boolean cAlarm = jo.getBoolean("d_toggle");
                Devices device = new Devices(u_id, deviceId, deviceName, nickName, user_type, d_code, master, aAlarm,bAlarm,cAlarm);
                deviceList.add(device);

            }catch(JSONException e){
                e.printStackTrace();
            }
           runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   adapter.notifyDataSetChanged();
               }
           });
            //setDefault();
        }
    };

    public void setDefault(){

            Log.d("ResponseData setDefault", "setDefault 실행됐습니다");
            SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = auto.edit();
            id = auto.getString("u_id", "");
            String nickname = auto.getString("NICKNAME", "");
            tv1.setText(nickname);
            deviceList.clear();
            count = 0;
            OkHttpClient client = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder().add("u_id", id).build();
            Request request = new Request.Builder().url(dList_Url).post(formBody).build();
            client.newCall(request).enqueue(setDefaultCallback);


    }

    public Callback setDefaultCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        //status, message, data(id,d_name)
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
            Log.d("Responsedata 123151516", responseData);
            try {
                SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                String u_id = auto.getString("u_id","");

                JSONObject jsonObject = new JSONObject(responseData);
                JSONArray ja = jsonObject.getJSONArray("data");
                for(int i= 0 ; i < ja.length(); i ++) {
                    JSONObject order = ja.getJSONObject(i);
                    int deviceId = order.getInt("id");
                    String deviceName = order.getString("d_name");
                    String nickName = order.getString("nickname");
                    String user_type = order.getString("user_type");
                    String d_code = order.getString("d_code");
                    String master = order.getString("master");
                    Boolean aAlarm = order.getBoolean("v_toggle");
                    Boolean bAlarm = order.getBoolean("t_toggle");
                    Boolean cAlarm = order.getBoolean("d_toggle");
                    Devices device = new Devices(u_id,deviceId, deviceName, nickName, user_type,d_code, master, aAlarm,bAlarm,cAlarm);
                    deviceList.add(device);
                    count++;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        tv2.setText(Integer.toString(count));
                    }
                });
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    };

    public void deleteDevice(String a, int b){
        String c = Integer.toString(b);
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", a).add("d_id",c).build();
        Request request = new Request.Builder().url(dDevice_Url).post(formBody).build();

        client.newCall(request).enqueue(deleteDeviceCallback);
    }
    public Callback deleteDeviceCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        //status, message, data(id,d_name)
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            final String responseData = response.body().string();
            setDefault();

        }
    };
    public void notifyDataChanged(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
    }
    /*
    // 블루투스 관련
    void checkBluetooth(){
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "기기가 블루투스를 지원하지 않습니다", Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            if(!mBluetoothAdapter.isEnabled()){
                Toast.makeText(getApplicationContext(), "현재 블루투스가 비활성화 되어 있습니다", Toast.LENGTH_LONG).show();
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }
            else
                selectDevice();
                inputWifi();
        }
    }
    void selectDevice(){
        mDevices = mBluetoothAdapter.getBondedDevices();
        mPariedDeviceCount = mDevices.size();

        if(mPariedDeviceCount ==0){
            Toast.makeText(getApplicationContext(), "페어링 된 장치가 없습니다", Toast.LENGTH_LONG).show();
            finish();
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        List<String> listItems = new ArrayList<String>();
        for(BluetoothDevice device : mDevices){
            listItems.add(device.getName());
        }
        listItems.add("취소");
        final CharSequence[] items = listItems.toArray(new CharSequence[listItems.size()]);
        listItems.toArray(new CharSequence[listItems.size()]);
        builder.setItems(items, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int item){
                if(item == mPariedDeviceCount){
                    Toast.makeText(getApplicationContext(), "취소를 선택하셨습니다", Toast.LENGTH_LONG).show();
                    finish();
                }
                else{
                    connectToSelectedDevice(items[item].toString());
                }
            }
        });
        builder.setCancelable(false);
        AlertDialog alert = builder.create();
        alert.show();
    }

    void connectToSelectedDevice(String selectedDeviceName){
        mRemoteDevice = getDeviceFromBondedList(selectedDeviceName);
        UUID uuid = java.util.UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try{
            mSocket = mRemoteDevice.createRfcommSocketToServiceRecord(uuid);
            mSocket.connect();
            mOutputStream = mSocket.getOutputStream();
            mInputStream = mSocket.getInputStream();
            beginListenForData();
        } catch(Exception e){
            Toast.makeText(getApplicationContext(),"오류가 발생했습니다", Toast.LENGTH_LONG).show();
            finish();
        }
    }

     BluetoothDevice getDeviceFromBondedList(String name){
        BluetoothDevice selectedDevice = null;
        for(BluetoothDevice device : mDevices){
            if(name.equals(device.getName())){
                selectedDevice = device;
                break;
            }
        }
        return selectedDevice;
    }
    void beginListenForData(){
        final Handler handler = new Handler();
        readBufferPosition = 0;
        readBuffer = new byte[1024];
        mWorkerThread = new Thread(new Runnable()
        {
            @Override
            public void run(){
                while(!Thread.currentThread().isInterrupted()){
                    try{
                        int byteAvailable = mInputStream.available();
                        if(byteAvailable > 0){
                            byte[] packetBytes = new byte[byteAvailable];
                            mInputStream.read(packetBytes);
                            for(int i = 0; i < byteAvailable; i++){
                                byte b = packetBytes[i];
                                if( b == mCharDelimiter){
                                    byte[] encodedBytes = new  byte[readBufferPosition];
                                    System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);

                                    final String data = new String(encodedBytes, "US-ASCII");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable(){
                                        @Override
                                        public void run(){
                                            // 받은 데이터에 대하여 할 행동
                                        }
                                    });
                                }
                                else{
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch(Exception e){
                        Toast.makeText(getApplicationContext(),"데이터 수신 중 오류 발생", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case REQUEST_ENABLE_BT:
                if(resultCode == RESULT_OK){
                    selectDevice();
                }
                else if(resultCode == RESULT_CANCELED){
                    Toast.makeText(getApplicationContext(), "블루투스를 허용해주세요", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            case 1:
                String id=  data.getStringExtra("ID");
                String pw = data.getStringExtra("PW");
                //블루투스로 보내는 코드
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    } //여기까지 블루투스
    public void inputWifi(){
        Intent intent = new Intent(getApplicationContext(), wifiInput.class);
        startActivityForResult(intent,1);
    }*/
    public void logOut(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("로그아웃");
        alert.setMessage("로그아웃 하시겠습니까?");
        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autoLogin = auto.edit();
                autoLogin.putBoolean("AUTO", false);
                autoLogin.commit();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);

            }
        });
        alert.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            long tempTime = System.currentTimeMillis();
            long intervalTime = tempTime - backPressedTime;
            if (0 < intervalTime && FINISH_INTERVAL_TIME >= intervalTime) {
                super.onBackPressed();
            } else {
                backPressedTime = tempTime;
                Toast.makeText(getApplicationContext(), "버튼을 한번 더 입력하시면 어플리케이션이 종료됩니다", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void deviceAdd(){
        AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity2.this);
        ad.setTitle("기기 추가");
        ad.setMessage("기기 코드를 입력해 주세요");
        final EditText et = new EditText(MainActivity2.this);
        ad.setView(et);
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                String value = et.getText().toString();
                addList(value);
                notifyDataChanged();
                dialog.dismiss();
           /*   try{
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormBody.Builder().add("u_id", id).add("d_code",value).build();
                Request request = new Request.Builder().url(addList_Url).post(formBody).build();
                client.newCall(request).execute();
                dialog.dismiss();

              }
                catch(IOException e){}*/

            }
        } );
        ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        ad.show();

    }
    public void addList(String a){
        Intent intent = getIntent();
        id = intent.getStringExtra("ID");
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder().add("u_id", id).add("d_code",a).build();
        Request request = new Request.Builder().url(addList_Url).post(formBody).build();
        client.newCall(request).enqueue(addListCallback);
    }
    public Callback addListCallback = new Callback(){
        @Override
        public void onFailure(Call call, IOException e) {

        }
        //status, message, data(id,d_name)
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String responseData = response.body().string();
            try {
                SharedPreferences auto = getSharedPreferences("setting", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = auto.edit();
                String u_id = auto.getString("u_id", "");
                JSONObject order = new JSONObject(responseData);
                int deviceId = order.getInt("id");
                String deviceName = order.getString("d_name");
                String nickName = order.getString("nickname");
                String user_type = order.getString("user_type");
                String d_code = order.getString("d_code");
                String master = order.getString("master");
                Boolean aAlarm = order.getBoolean("aAlarm");
                Boolean bAlarm = order.getBoolean("bAlarm");
                Boolean cAlarm = order.getBoolean("cAlarm");
                Devices device = new Devices(u_id, deviceId, deviceName, nickName, user_type, d_code, master, aAlarm, bAlarm,cAlarm);
                deviceList.add(device);
            }catch(JSONException e){
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    };
    public void forBeginner(){
        if(this.count == 0){
            AlertDialog.Builder ad = new AlertDialog.Builder(MainActivity2.this);
            ad.setTitle("기기 등록 방법");
            ad.setMessage("기기 등록 방법을 순서대로 보여줄 것입니다");
            ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }
    }
}
