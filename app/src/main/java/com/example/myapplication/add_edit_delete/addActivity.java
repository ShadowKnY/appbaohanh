package com.example.myapplication.add_edit_delete;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.Display.DetailActivity;
import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;
import java.util.UUID;

public class addActivity extends AppCompatActivity {

    private EditText title, description, price, review, score, numberIC;
    private Button add_btn;
    private ImageView back_btn;
    private DatabaseReference databaseRef;
    private String category;

    ImageView uploadPicture1;
    String imageURL1;
    Uri uri;
    boolean isUploadImg1;
    //nhận kết quả từ các hoạt động con
    private ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //khởi tạo biến và ánh xạ các view
        title = findViewById(R.id.title_txt);
        description = findViewById(R.id.description_txt);
        price = findViewById(R.id.price_txt);
        review = findViewById(R.id.review_txt);
        score = findViewById(R.id.score_txt);
        numberIC = findViewById(R.id.numberInChart_txt);
        add_btn = findViewById(R.id.add_btn);
        back_btn = findViewById(R.id.backBtn);
        uploadPicture1 = findViewById(R.id.uploadPicture1);

        //intent đưuọc truyền từ activity hiển thị
        category = getIntent().getStringExtra("category");

        // Thêm sản phẩm khi nhấn nút "Thêm"
        findViewById(R.id.add_btn).setOnClickListener(v -> {
            String titleStr = title.getText().toString().trim();
            String reviewStr = review.getText().toString().trim();
            String scoreStr = score.getText().toString().trim();
            String numberICStr = numberIC.getText().toString().trim();
            String priceStr = price.getText().toString().trim();
            String descriptionStr = description.getText().toString().trim();

            // Kiểm tra xem các trường input có rỗng hay không
            if (titleStr.isEmpty() || isUploadImg1 == false || reviewStr.isEmpty() || scoreStr.isEmpty() || numberICStr.isEmpty() || priceStr.isEmpty() || descriptionStr.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                //nêu các trường đã đủ và có cả ảnh thì sang sI để lưu ảnh đó vào database
                saveImage();
            }
        });

        //mở trình chọn ảnh
        uploadPicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);//mở thư viện ảnh
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        //kiểm tra xem ảnh đã được chọn chưa
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    uri = data.getData();//nhận địa chỉ của hình ảnh vừa add
                    uploadPicture1.setImageURI(uri);
                    isUploadImg1 = true;
                    //đã chọn thành công, set ảnh bằng giá trị của uri(getData từ thư viện)
                } else {
                    isUploadImg1 = false;
                    Toast.makeText(addActivity.this, "Không có ảnh nào được chọn", Toast.LENGTH_LONG).show();
                }
            }
        });

        //nut quay lại
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void saveImage() {
        if (uri != null) {
            //tham chiếu dữ liệu đến nút productImage, sử dụng phần cuối uri làm tên tệp
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ProductImage").child(Objects.requireNonNull(uri.getLastPathSegment()));
            //putfile dể tải lên ảnh vào path
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    //getDown để lấy đường dẫn URL của ảnh vừa tải, dùng url ấy để truy cập hình ảnh từ FB
                    while (!uriTask.isComplete());
                    Uri urlImage = uriTask.getResult();
                    imageURL1 = String.valueOf(urlImage);//lấy xong thì gán biến vào URL1 và goi addProduct
                    addProductToFirebase();
                }
            });
        }
    }

    // Thêm sản phẩm vào cơ sở dữ liệu Firebase
    private void addProductToFirebase() {
        // lấy các dữ liệu từ input
        String titleStr = title.getText().toString().trim();
        String reviewStr = review.getText().toString().trim();
        String scoreStr = score.getText().toString().trim();
        String numberICStr = numberIC.getText().toString().trim();
        String priceStr = price.getText().toString().trim();
        String descriptionStr = description.getText().toString().trim();

        int review = Integer.parseInt(reviewStr);
        double score = Double.parseDouble(scoreStr);
        int numberInChart = Integer.parseInt(numberICStr);
        double price = Double.parseDouble(priceStr);

        //tham chiếu đến Product trong FB
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Product/" + category);
        UUID uuid = UUID.randomUUID();
        String productId = uuid.toString(); // Tạo một ID mới cho sản phẩm
        //tạo 1 đối tượng PD mới với dữ liệu đã được gán vào URL1
        PopularDomain product = new PopularDomain(titleStr, imageURL1, review, score, numberInChart, price, descriptionStr,productId,category);
        databaseRef.child(productId).setValue(product).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Sản phẩm đã được thêm vào " + category, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}