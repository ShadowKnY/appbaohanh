package com.example.myapplication.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Adapter.PopularAdapter;
import com.example.myapplication.Display.IpadDisplay;
import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.example.myapplication.domain.PopularDomain;

import java.util.ArrayList;

import info.info;

public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
LinearLayout linearLayoutinfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecycleView();

        //chuyển sang các layout Display iph,ip,...
                ImageView im_iph, im_mac, im_ip, im_watch;

        im_iph = findViewById(R.id.dis_ip);
        im_mac = findViewById(R.id.dis_mac);
        im_ip = findViewById(R.id.dis_ipad);
        im_watch = findViewById(R.id.dis_watch);
        linearLayoutinfo = findViewById(R.id.layout4);
        im_iph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpadDisplay.class);
                intent.putExtra("category","iPhone");
                startActivity(intent);
            }
        });

        im_mac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpadDisplay.class);
                intent.putExtra("category","mac");
                startActivity(intent);
            }
        });

        im_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpadDisplay.class);
                intent.putExtra("category","iPad");
                startActivity(intent);
            }
        });

        im_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpadDisplay.class);
                intent.putExtra("category","appleWatch");
                startActivity(intent);
            }
        });

        linearLayoutinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, info.class);
                startActivity(intent);
            }
        });

    }

    private void initRecycleView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("iPhone 15 Pro Max ","item_1",15,4,2,999,""));
        items.add(new PopularDomain("iPhone 14 Pro","item_3",50,4.8,15,899,""));
        items.add(new PopularDomain("Apple Watch Ultra","cat4",24,4,25,699,""));
        items.add(new PopularDomain("iPad Pro 2023","cat3",11,3,26,799,""));
        items.add(new PopularDomain("MacBook Pro 16'","item_4",16,4.3,16,1999,""));

        binding.popularView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.popularView.setAdapter(new PopularAdapter(items));
    }
}