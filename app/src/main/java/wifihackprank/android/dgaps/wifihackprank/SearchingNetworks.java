package wifihackprank.android.dgaps.wifihackprank;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.List;

public class SearchingNetworks extends AppCompatActivity {
    private  Element [] nets;
    private WifiManager wifiManager;
    private List<ScanResult> wifiList;
    int counter =1;
    Button onOff;
    ToggleButton toggleButton;
    ListView netList;

    public static final String SSID = "ssid";
    public static final String SECURITY = "security";
    public static final String LEVEL = "level";

    String SendName;
    String SendSecurity;
    String SendLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching_networks);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        onOff = (Button) findViewById(R.id.button);
        //  detectWifi();
        //  onOff.setText("ON");


        netList = (ListView) findViewById(R.id.listItem);
        //    toggleButton = (ToggleButton) findViewById(R.id.togglebutton);
        detectWifi();
        //   toggleButton.setTextOn("ON");

    }


    public  void detectWifi()
    {
        try {
            this.wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            this.wifiManager.startScan();
            this.wifiList = this.wifiManager.getScanResults();

            this.nets = new Element[wifiList.size()];

            for (int i = 0; i < wifiList.size(); i++) {


                String item = wifiList.get(i).toString();
                String[] vector_item = item.split(",");
                String item_essid = vector_item[0];
                String item_capabillities = vector_item[2];
                String item_level = vector_item[3];
                String ssid = item_essid.split(":")[1];
                String conName = item_essid.split(":")[0];
                SendName=ssid;
                String seurity = item_capabillities.split(":")[1];
                String level = item_level.split(":")[1];

                nets[i] = new Element(ssid, seurity, level, conName);

                AdapterElements adapterElements = new AdapterElements(this);


                netList.setAdapter(adapterElements);

                netList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                      //  String value = netList.getItemAtPosition(position).toString();
                        //display value here

                      //  Toast.makeText(SearchingNetworks.this, value + " ", Toast.LENGTH_LONG).show();


                        Intent intent = new Intent(SearchingNetworks.this, ShowDetails.class);
                        intent.putExtra(SSID,  SendName);

                        startActivity(intent);
                    }
                });


            }
        }
        catch (Exception e)
        {


        }

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


    //changeConnection

    public void onToggleClicked(View view) {
        // Is the toggle on?
        boolean on = ((Button) view).isPressed();

        if (on) {

            detectWifi();
            onOff.setText("ON");
            netList.setVisibility(View.VISIBLE);



            // Enable vibrate
        } else {
            // Disable vibrate
            onOff.setText("OFF");
            netList.setVisibility(View.INVISIBLE);


        }
    }




    public  class AdapterElements extends ArrayAdapter<Object>
    {

        Activity context;

        public  AdapterElements (Activity context)
        {

            super(context,R.layout.items,nets);
            this.context = context;
        }

        public  View getView (int position, View  convertView, ViewGroup parent)
        {

            LayoutInflater inflater = context.getLayoutInflater();
            View item =  inflater.inflate(R.layout.items,null);

            TextView tvSsid = (TextView) item.findViewById(R.id.tvSSID);
            tvSsid.setText(nets[position].getTitle());

            TextView tvSecurity = (TextView) item.findViewById(R.id.tvSecurity);
            tvSecurity.setText(nets[position].getSecurity());

            TextView conName = (TextView) item.findViewById(R.id.conName);
            conName.setText(nets[position].getConName());

            TextView tvLevel = (TextView) item.findViewById(R.id.tvLevel);
            String level = nets[position].getLevel();

            try
            {

                int i = Integer.parseInt(level);

                if (i >-50)
                {
                    tvLevel.setText("High");
                }
                else if (i < 50 && i > -80)
                {

                    tvLevel.setText("Medium");
                }
                else  if (i<= -80)
                {
                    tvLevel.setText("Low");
                }

            }
            catch (NumberFormatException e)
            {


            }

            return  item;
        }
    }
}

