package com.example.myapplication.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.myapplication.Adapter.PopularAdapter;
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

        initRecycleView();
        
    }

    private void initRecycleView() {
        ArrayList<PopularDomain> items = new ArrayList<>();
        items.add(new PopularDomain("iPhone 15 Pro Max ","item_1",15,4,2,999,"Khung Titanium bền bỉ chống va đập và tối ưu trọng lượng tốt hơn các phiên bản khung nhôm. Thêm nữa, viền màn hình máy thu nhỏ mang đến diện mạo hoàn toàn mới, sang trọng và cuốn hút. \n" +
                "\n" +
                "Cổng sạc USB-C 3 thay thế cổng Lightning hỗ trợ tốc độ truyền tải lên đến 10Gbps. Người dùng có thể dễ dàng mượn sạc sử dụng trong trường hợp quên mang theo\n" +
                "\n" +
                "Về phần cứng, con chip A17 Pro cho thấy hiệu năng vượt trội 10% nhân CPU và 20% nhân GPU so với A16 Bionic. Ngoài ra, vi xử lý mới còn hỗ trợ ray tracing nhanh gấp 4 lần mang đến trải nghiệm đồ họa gaming mượt mà. \n" +
                "\n" +
                "Đều đặn mỗi năm, Apple sẽ ra mắt một phiên bản màu sắc mới, năm nay cũng không phải ngoại lệ với sự xuất hiện của màu Titan tự nhiên. Gam màu độc đáo hứa hẹn sẽ được nhiều người dùng đón nhận. "));
        items.add(new PopularDomain("iPhone 14 Pro","item_3",50,4.8,15,899,""));
        items.add(new PopularDomain("Apple Watch Ultra","cat4",24,4,25,699,""));
        items.add(new PopularDomain("iPad Pro 2023","cat3",11,3,26,799,""));
        items.add(new PopularDomain("MacBook Pro 16'","item_4",16,4.3,16,1999,""));

        binding.popularView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        binding.popularView.setAdapter(new PopularAdapter(items));
    }
}