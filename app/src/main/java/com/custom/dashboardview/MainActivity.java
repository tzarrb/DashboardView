package com.custom.dashboardview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.custom.dashboardview.view.DashboardView;

public class MainActivity extends AppCompatActivity {

    DashboardView dashboardView;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = (SeekBar)this.findViewById(R.id.seekBar);
        dashboardView = (DashboardView)this.findViewById(R.id.dashboardView);
        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListenerImp());

        init();
    }

    public void init(){
        dashboardView.postDelayed(new Runnable() {
            @Override
            public void run() {
                dashboardView.setPercent(0.85f, 30);
                seekBar.setProgress(85);
            }
        }, 1000);
    }


    private class OnSeekBarChangeListenerImp implements
            SeekBar.OnSeekBarChangeListener {

        // 触发操作，拖动
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            dashboardView.setPercent(progress/100.0f, 30);
        }

        // 表示进度条刚开始拖动，开始拖动时候触发的操作
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        // 停止拖动时候
        public void onStopTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
        }
    }
}
