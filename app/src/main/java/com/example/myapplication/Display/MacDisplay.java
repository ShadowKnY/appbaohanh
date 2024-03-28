package com.example.myapplication.Display;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.Adapter.DisplayAdapter;
import com.example.myapplication.Adapter.PopularAdapter;
import com.example.myapplication.R;
import com.example.myapplication.databinding.IphoneDisplayBinding;
import com.example.myapplication.databinding.MacDisplayBinding;
import com.example.myapplication.domain.PopularDomain;

import java.util.ArrayList;
import java.util.List;

public class MacDisplay extends AppCompatActivity {
    MacDisplayBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.iphone_display);
//
//        //khai báo và khởi tạo danh sách sp
//        List<PopularDomain> productList = new ArrayList<>();
//        productList.add(new PopularDomain("iPhone 14 Pro","item_3",50,4.8,15,899,""));
//        productList.add(new PopularDomain("Apple Watch Ultra","cat4",24,4,25,699,""));
//        productList.add(new PopularDomain("iPad Pro 2023","cat3",11,3,26,799,""));
//
//        //kết nối RecyclerView từ layoutXML
//        RecyclerView recyclerView = findViewById(R.id.recycler_view_products);
//
//        //thiết lập gridlayoutmanager cho recyclerView với spanCount là 2 (2sp trên 1 hàng)
//        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
//        recyclerView.setLayoutManager(layoutManager);
//
//        //thiết lập adapter cho recyclerView
//        DisplayAdapter adapter = new DisplayAdapter(productList);
//        recyclerView.setAdapter(adapter);
        binding = MacDisplayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initRecyclerView();
    }
    private void initRecyclerView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("Mac Pro M1","item_3",50,4.8,15,899,""));
        items.add(new PopularDomain("Apple Watch Ultra","cat4",24,4,25,699,""));
        items.add(new PopularDomain("iPad Pro 2023","cat3",11,3,26,799,""));
        items.add(new PopularDomain("MacBook Pro 16'","item_4",16,4.3,16,1999,""));

        // Sử dụng GridLayoutManager với spanCount = 2 (hai sản phẩm trên một hàng)
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        binding.macView.setLayoutManager(layoutManager);
        binding.macView.setAdapter(new PopularAdapter(items));
    }
}
