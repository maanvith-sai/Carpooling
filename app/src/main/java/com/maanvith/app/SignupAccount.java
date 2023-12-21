package com.maanvith.app;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class SignupAccount extends AppCompatActivity {

    private CircleImageView imageViewCircle;
    private EditText editTextEmailSignUp,editTextUserNameSignUp;
    private EditText editTextPasswordSignUp;
    private Button buttonRegister;
    boolean imageControl  = false;
    Uri imageUri;
    String filePath;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference reference;

    FirebaseStorage firebaseStorage;
    StorageReference storageReference;
    ALoadingDialog aLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_account);


        imageViewCircle = findViewById(R.id.yourRidesProfile);
        editTextEmailSignUp = findViewById(R.id.editTextEmailSignUp);
        editTextPasswordSignUp = findViewById(R.id.editTextPasswordSignUp);
        editTextUserNameSignUp = findViewById(R.id.editTextUserNameSignUp);
        buttonRegister = findViewById(R.id.buttonRegister);
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference=firebaseStorage.getReference();
        aLoadingDialog = new ALoadingDialog(this);

        imageViewCircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBar));
        }

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = editTextEmailSignUp.getText().toString();
                String password = editTextPasswordSignUp.getText().toString();
                String userName = editTextUserNameSignUp.getText().toString();

                if(!email.equals("")&&!password.equals("")&&!userName.equals("")&& imageControl){
                    aLoadingDialog.show();
                    Handler handler  = new Handler();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            aLoadingDialog.cancel();
                        }
                    };
                    handler.postDelayed(runnable,12000);
                    signup(email,password,userName);
                }
                else{
                    Toast.makeText(SignupAccount.this, "Enter All the details and Select the photo", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    public void imageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");  // Use "image/*" instead of "images/*"
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imageControl = false;
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(imageViewCircle);
            imageControl = true;
        } else {
            imageControl = false;
        }
    }




    public void signup(String email, String password, String userName){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    reference.child("Users").child(auth.getUid()).child("userName").setValue(userName);
                    Toast.makeText(SignupAccount.this, "Details uploaded", Toast.LENGTH_SHORT).show();

                    if (imageControl) {
                        UUID randomID = UUID.randomUUID();
                        String imageName = "images/" + randomID.toString() + ".jpg";


                        StorageReference imageRef = storageReference.child(imageName);

                        UploadTask uploadTask = imageRef.putFile(imageUri);
                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        filePath = uri.toString();
                                        reference.child("Users").child(auth.getUid()).child("image").setValue(filePath)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(SignupAccount.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(SignupAccount.this, DetailsActivity.class);
                                                        intent.putExtra("userName", userName);
                                                        intent.putExtra("email", email);
                                                        intent.putExtra("user",auth.getUid());
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(SignupAccount.this, "Error in updating picture!", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignupAccount.this, "Error in uploading image!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        reference.child("Users").child(auth.getUid()).child("image").setValue("null");
                    }


                } else {
                    Toast.makeText(SignupAccount.this, "Invalid UserName or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}