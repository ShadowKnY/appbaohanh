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

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //gọi hàm để đổ dữ liệu vào recycleView
        initRecycleView();

        //khai báo các biến
        ImageView im_iph, im_mac, im_ip, im_watch,im_more;
        LinearLayout menu_cart;

        //gán id các view của mainActivity vào biến đã tạo
        im_iph = findViewById(R.id.dis_ip);
        im_mac = findViewById(R.id.dis_mac);
        im_ip = findViewById(R.id.dis_ipad);
        im_watch = findViewById(R.id.dis_watch);
        im_more = findViewById(R.id.dis_more);
        menu_cart = findViewById(R.id.menu_cart);

        //tạo sự kiện  nhấn vào view
        im_iph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpadDisplay.class);

                //lấy id của view để đối chiếu với firebase
                intent.putExtra("category","iPhone");
                startActivity(intent);

                //chấm dứt MainActivity
                finish();
            }
        });

        im_mac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpadDisplay.class);

                //lấy id của view để đối chiếu với firebase
                intent.putExtra("category","mac");
                startActivity(intent);

                //chấm dứt MainActivity
                finish();
            }
        });

        im_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpadDisplay.class);

                //lấy id của view để đối chiếu với firebase
                intent.putExtra("category","iPad");
                startActivity(intent);

                //chấm dứt MainActivity
                finish();
            }
        });

        im_watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpadDisplay.class);

                //lấy id của view để đối chiếu với firebase
                intent.putExtra("category","appleWatch");
                startActivity(intent);
                //chấm dứt MainActivity
                finish();
            }
        });

        im_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, IpadDisplay.class);

                //lấy id của view để đối chiếu với firebase
                intent.putExtra("category", "Product");
                intent.putExtra("showAllProducts", true); // Thêm dòng này để chỉ định hiển thị tất cả các sản phẩm
                startActivity(intent);
                //chấm dứt MainActivity
                finish();
            }
        });
        menu_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Cart.class);
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