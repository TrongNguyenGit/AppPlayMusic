package com.nvt.trongnguyen.musicplayapp;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    TextView txtTitle, timeRuning, timeTotal;
    SeekBar runTime;
    ImageButton btnPlay, btnStop, btnPre, btnNext, btnExit;
    ImageView disc;

    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    Animation animetion ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        AddSong();
        animetion = AnimationUtils.loadAnimation(this,R.anim.disc_rotate);

        KhoiTaoMediaPlayer();
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                btnPlay.setImageResource(R.drawable.play);
                KhoiTaoMediaPlayer();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.play);
                }
                else
                {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pause);
                }
                SetTimeTotal();
                UpdateTime();
                disc.startAnimation(animetion);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
                if(position >= arraySong.size())
                    position = 0;
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTime();
                disc.startAnimation(animetion);
            }
        });

        btnPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position--;
                if(position <0)
                    position = arraySong.size() -1;
                if(mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                SetTimeTotal();
                UpdateTime();
                disc.startAnimation(animetion);

            }
        });
        runTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(runTime.getProgress());
            }
        });
    }
    private  void UpdateTime()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                timeRuning.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()));
                runTime.setProgress(mediaPlayer.getCurrentPosition());

                //kiem tra ket thuc bai hat
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position >= arraySong.size())
                            position = 0;
                        if(mediaPlayer.isPlaying())
                        {
                            mediaPlayer.stop();
                            mediaPlayer.release();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        SetTimeTotal();
                        UpdateTime();
                    }
                });
                handler.postDelayed(this, 500);
            }
        },100);
    }
    private  void SetTimeTotal()
    {
        SimpleDateFormat DinhDangGio = new SimpleDateFormat("mm:ss");
        timeTotal.setText(DinhDangGio.format(mediaPlayer.getDuration()));
        runTime.setMax(mediaPlayer.getDuration());
    }
    private  void KhoiTaoMediaPlayer ()
    {
        mediaPlayer = MediaPlayer.create(MainActivity.this, arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());

    }

    private void AddSong() {
        arraySong = new ArrayList<>();
        arraySong.add(new Song("Ai chờ  ai",R.raw.ai_cho_ai));
        arraySong.add(new Song("All fall down",R.raw.all_fall_down));
        arraySong.add(new Song("All I wanna do",R.raw.all_i_wana_do));
        arraySong.add(new Song("Alone",R.raw.alone));
    }
    private void AnhXa()
    {
        timeRuning   = (TextView) findViewById(R.id.timestart);
        timeTotal   = (TextView) findViewById(R.id.timetotal);
        txtTitle    = (TextView) findViewById(R.id.TextViewTitle);

        runTime     = (SeekBar)findViewById(R.id.seekbar);

        btnPlay     = (ImageButton)findViewById(R.id.playpause);
        btnStop     = (ImageButton)findViewById(R.id.stop);
        btnNext     = (ImageButton)findViewById(R.id.next);
        btnPre      = (ImageButton)findViewById(R.id.prebutton);
        disc        = (ImageView)findViewById(R.id.distview);
        btnExit     = (ImageButton)findViewById(R.id.exit);

    }
}