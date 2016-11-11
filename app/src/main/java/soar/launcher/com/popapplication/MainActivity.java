package soar.launcher.com.popapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = (TextView)findViewById(R.id.test);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPop generalAccessDialog = new DialogPop.Builder(MainActivity.this)
                        .setTitle("gaofei")
                        .setTitleImage(R.drawable.test)
                        .setDialogBackColor(Color.RED)
                        .setDialogOutBackColor(Color.parseColor("#5500ff00"))
                        .setMessage("this is test")
                        .setNegativeButton("test-na", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).setPositiveButton("test-ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        }).setDialogDismissLisenter(new DialogPop.DialogDismissLisenter() {
                            @Override
                            public void onDismiss() {

                            }
                        }).build()
                        .show();



            }
        });

    }
}
