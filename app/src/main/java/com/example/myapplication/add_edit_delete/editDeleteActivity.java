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
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.domain.PopularDomain;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Objects;

public class editDeleteActivity extends AppCompatActivity {

    private EditText titleTxt, descriptionTxt, priceTxt, reviewTxt, scoreTxt, numberInChartTxt;
    private Button saveBtn, deleteBtn;
    private ImageView backBtn;
    private DatabaseReference databaseRef;
    private String category;
    private String title; // Changed from productId to title
    private PopularDomain productData;
    ImageView uploadPicture1;
    String imageURL1;
    Uri uri;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    boolean isUploadImg1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        // Ánh xạ các view
        titleTxt = findViewById(R.id.title_txt);
        descriptionTxt = findViewById(R.id.description_txt);
        priceTxt = findViewById(R.id.price_txt);
        reviewTxt = findViewById(R.id.review_txt);
        scoreTxt = findViewById(R.id.score_txt);
        numberInChartTxt = findViewById(R.id.numberInChart_txt);
        saveBtn = findViewById(R.id.saveBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        backBtn = findViewById(R.id.backBtn);
        uploadPicture1 = findViewById(R.id.uploadPicture1);

        // nút quay lai
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Khởi tạo DatabaseReference
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Product");

        // Lấy thông tin sản phẩm từ DP, trong detail
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productString = extras.getString("DataProduct");
            Gson gson = new Gson();
            productData = gson.fromJson(productString, PopularDomain.class);

            uri = Uri.parse(productData.getPicUrl());
            Picasso.get().load(productData.getPicUrl()).into(uploadPicture1);
            category = productData.getCategory();
            titleTxt.setText(productData.getTitle());
            descriptionTxt.setText(productData.getDecription());
            priceTxt.setText(String.valueOf(productData.getPrice()));
            reviewTxt.setText(String.valueOf(productData.getReview()));
            scoreTxt.setText(String.valueOf(productData.getScore()));
            numberInChartTxt.setText(String.valueOf(productData.getNumberInChart()));
            isUploadImg1 = true;
        } else {
            Toast.makeText(this, "Không nhận được thông tin sản phẩm", Toast.LENGTH_SHORT).show();
            finish();
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(editDeleteActivity.this, "Đang cập nhật", Toast.LENGTH_SHORT).show();

                if(isUploadImg1){
                    //ktra xem đây có phải ảnh cũ trên FB không
                    if (uri.toString().contains("https")) {
                        //đúng rồi thì ko thay đổi và sửa
                        imageURL1 = uri.toString();
                        uploadData();
                    } else {
                        saveImage();
                    }
                }
            }
        });


        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác nhận việc xóa sản phẩm
                AlertDialog.Builder builder = new AlertDialog.Builder(editDeleteActivity.this);
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                        .setPositiveButton("Có", (dialog, which) -> {
                            // Thực hiện xóa sản phẩm khỏi cơ sở dữ liệu Firebase
                            deleteProductFromFirebase();
                        })
                        .setNegativeButton("Không", null)
                        .show();
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    uri = data.getData();
                    uploadPicture1.setImageURI(uri);
                    isUploadImg1 = true;
                } else {
                    isUploadImg1 = false;
                    Toast.makeText(editDeleteActivity.this, "No image selected", Toast.LENGTH_LONG).show();
                }
            }
        });

        uploadPicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
    }

    private void deleteProductFromFirebase() {
        if(productData != null){
            if(productData.getCategory() != null){
                category = productData.getCategory(); // Giá trị mặc định cho category khi không có giá trị được truyền vào từ bên ngoài

                DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Product").child(category).child(productData.getItemId());

                productRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(editDeleteActivity.this, "Sản phẩm đã được xóa", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(editDeleteActivity.this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                    }
                });
            }else {
                Toast.makeText(editDeleteActivity.this, "product này không có category", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void saveImage() {
        if (uri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("ProductImage").child(Objects.requireNonNull(uri.getLastPathSegment()));
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isComplete()) ;
                    Uri urlImage = uriTask.getResult();
                    imageURL1 = String.valueOf(urlImage);
                    uploadData();
                }
            });
        }
    }

    public void uploadData(){
        String title = titleTxt.getText().toString().trim();
        String description = descriptionTxt.getText().toString().trim();
        String priceStr = priceTxt.getText().toString().trim();
        String picUrl = imageURL1;
        String reviewStr = reviewTxt.getText().toString().trim();
        String scoreStr = scoreTxt.getText().toString().trim();
        String numberInChartStr = numberInChartTxt.getText().toString().trim();
        if(productData != null){
            if (title.isEmpty() || picUrl.isEmpty() || reviewStr.isEmpty() || scoreStr.isEmpty() || numberInChartStr.isEmpty() || priceStr.isEmpty() || description.isEmpty()) {
                Toast.makeText(editDeleteActivity.this, "Hãy điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                if(productData.getCategory() != null){
                    int review = Integer.parseInt(reviewStr);
                    double score = Double.parseDouble(scoreStr);
                    int numberInChart = Integer.parseInt(numberInChartStr);
                    double price = Double.parseDouble(priceStr);

                    // Lưu thông tin đã chỉnh sửa vào Firebase Database
                    String category = productData.getCategory(); // Thay thế bằng biến chứa tên danh mục sản phẩm

                    DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Product").child(category).child(productData.getItemId());

                    PopularDomain product = new PopularDomain(title, picUrl, review, score, numberInChart, price, description);
                    HashMap<String, Object> productValues = product.toMap(); // Assume PopularDomain has a method toMap() returning a HashMap

                    productRef.updateChildren(productValues).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(editDeleteActivity.this, "Sản phẩm đã được cập nhật", Toast.LENGTH_SHORT).show();
                            finish(); // Kết thúc activity sau khi cập nhật thành công
                        } else {
                            Toast.makeText(editDeleteActivity.this, "Đã xảy ra lỗi. Vui lòng thử lại sau.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(editDeleteActivity.this, "Product này không có category", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}