package com.example.hong.dhproject3;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class wifiActivity extends AppCompatActivity {
   private List<WifiData> wifiList;
    private ListView listView;
    private WifiManager wifiManager;
    private List<ScanResult> scanDatas;
    String id;

    private BroadcastReceiver receiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent){
            final String action = intent.getAction();
            if(action.equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)){
                scanDatas = wifiManager.getScanResults();
                wifiList = new ArrayList<>();
                for(ScanResult select : scanDatas){
                    String BBSID = select.BSSID;
                    String SSID = select.SSID;
                    WifiData wifiData = new WifiData(BBSID, SSID, true);
                    wifiList.add(wifiData);
                }

                ArrayAdapter adapter = new WifiAdapter(getApplicationContext(), R.layout.item_layout, wifiList);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        id = wifiList.get(i).getSSID();
                        Toast.makeText(getApplicationContext(), id, Toast.LENGTH_LONG).show();
                        send(id);

                    }
                });
                adapter.notifyDataSetChanged();
            } else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
                sendBroadcast(new Intent("wifi.ON_NETWORK_STATE_CHANGED"));
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi);
        listView = (ListView)findViewById(R.id.listView);
        wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        registerReceiver(receiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        wifiManager.startScan();
    }

    public void send(String a){
        Intent intent = new Intent();
        intent.putExtra("ID", a);
        setResult(0, intent);
        finish();
    }
    @Override
    public void onResume(){
        super.onResume();
        /*IntentFilter intentFilter = new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(receiver, intentFilter);
        wifiManager.startScan();
        listView.setFocusable(true);*/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},87);
            }
        }
    }

    @Override
    public void onPause(){
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void showWifiList(View v){
        wifiManager = (WifiManager) getSystemService(this.WIFI_SERVICE);
    }
}
