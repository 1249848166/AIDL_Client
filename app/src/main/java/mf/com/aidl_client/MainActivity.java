package mf.com.aidl_client;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mf.com.aidl_service.AIDL;
import mf.com.aidl_service.model.Person;

public class MainActivity extends AppCompatActivity {

    AIDL aidl;

    Button add;

    ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            aidl=AIDL.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            aidl=null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent();
        intent.setComponent(new ComponentName("mf.com.aidl_service","mf.com.aidl_service.AIDLService"));
        bindService(intent,connection,BIND_AUTO_CREATE);

        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Person p=new Person("person"+aidl.getPersons().size());
                    aidl.addPerson(p);
                    System.out.println("添加了"+p.getName());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
