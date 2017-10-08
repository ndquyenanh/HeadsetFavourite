package com.qa.headsetfavourite;

import android.app.AlertDialog;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

/**
 * Created by sev_user on 19-May-15.
 */
public  class HeadsetService extends Service implements AdapterView.OnItemClickListener {

    private AlertDialog mDialog;
    AppAdapter mAdapter;

    List<AppDetail> mAppDetails;
    View mainView;
    ListView mListView;

    private Receiver mReceiver;

    public HeadsetService() {

    }

    private void init(){
        mDialog = new AlertDialog.Builder(this).create();

        mainView = LayoutInflater.from(this).inflate(R.layout.activity_main, null, false);
        mListView = (ListView) mainView.findViewById(R.id.list_app);

        mAppDetails = Utils.getAppDetails(this);
        mAdapter = new AppAdapter(this, mAppDetails);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

        mDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        mDialog.setTitle("Select an app");
        mDialog.setIcon(R.mipmap.ic_launcher);
        mDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mDialog.dismiss();
            }
        });

        mDialog.setView(mainView);
    }

    /**
     * Return the communication channel to the service.  May return null if
     * clients can not bind to the service.  The returned
     * {@link IBinder} is usually for a complex interface
     * that has been <a href="{@docRoot}guide/components/aidl.html">described using
     * aidl</a>.
     * <p/>
     * <p><em>Note that unlike other application components, calls on to the
     * IBinder interface returned here may not happen on the main thread
     * of the process</em>.  More information about the main thread can be found in
     * <a href="{@docRoot}guide/topics/fundamentals/processes-and-threads.html">Processes and
     * Threads</a>.</p>
     *
     * @param intent The Intent that was used to bind to this service,
     *               as given to {@link Context#bindService
     *               Context.bindService}.  Note that any extras that were included with
     *               the Intent at that point will <em>not</em> be seen here.
     * @return Return an IBinder through which clients can call on to the
     * service.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        init();

        mReceiver = new Receiver();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(mReceiver, intentFilter);
        return START_STICKY;
    }

    /**
     * Callback method to be invoked when an item in this AdapterView has
     * been clicked.
     * <p/>
     * Implementers can call getItemAtPosition(position) if they need
     * to access the data associated with the selected item.
     *
     * @param parent   The AdapterView where the click happened.
     * @param view     The view within the AdapterView that was clicked (this
     *                 will be a view provided by the adapter)
     * @param position The position of the view in the adapter.
     * @param id       The row id of the item that was clicked.
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getPackageManager().getLaunchIntentForPackage(mAppDetails.get(position).pkg));
        startActivity(intent);

        mDialog.dismiss();
    }

    private class Receiver extends BroadcastReceiver {

        public Receiver() {
        }

        /**
         * This method is called when the BroadcastReceiver is receiving an Intent
         * broadcast.  During this time you can use the other methods on
         * BroadcastReceiver to view/modify the current result values.  This method
         * is always called within the main thread of its process, unless you
         * explicitly asked for it to be scheduled on a different thread using
         * {@link Context#registerReceiver(BroadcastReceiver,
         * IntentFilter, String, Handler)}. When it runs on the main
         * thread you should
         * never perform long-running operations in it (there is a timeout of
         * 10 seconds that the system allows before considering the receiver to
         * be blocked and a candidate to be killed). You cannot launch a popup dialog
         * in your implementation of onReceive().
         * <p/>
         * <p><b>If this BroadcastReceiver was launched through a &lt;receiver&gt; tag,
         * then the object is no longer alive after returning from this
         * function.</b>  This means you should not perform any operations that
         * return a result to you asynchronously -- in particular, for interacting
         * with services, you should use
         * {@link Context#startService(Intent)} instead of
         * {@link Context#bindService(Intent, ServiceConnection, int)}.  If you wish
         * to interact with a service that is already running, you can use
         * {@link #peekService}.
         * <p/>
         * <p>The Intent filters used in {@link Context#registerReceiver}
         * and in application manifests are <em>not</em> guaranteed to be exclusive. They
         * are hints to the operating system about how to find suitable recipients. It is
         * possible for senders to force delivery to specific recipients, bypassing filter
         * resolution.  For this reason, {@link #onReceive(Context, Intent) onReceive()}
         * implementations should respond only to known actions, ignoring any unexpected
         * Intents that they may receive.
         *
         * @param context The Context in which the receiver is running.
         * @param intent  The Intent being received.
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_HEADSET_PLUG)) {

                int state = intent.getIntExtra(Define.HEADSET_STATE, -1);
                switch (state) {

                    case Define.HEADSET_UNPLUG:
                        Utils.showToast(HeadsetService.this, "Headset UnPlug");
                        mDialog.dismiss();
                        break;

                    case Define.HEADSET_PLUG:
                        mDialog.show();
                        break;

                    default:
                        Log.v("qnv96", "Data media");
                        break;
                }
            }
        }
    }
}
