package com.maanvith.app;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import java.util.UUID;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private TextView profileName, updateProfile;
    private CircleImageView profilePhoto;
    private String image;
    private String filePath;
    private FirebaseAuth auth;
    private Uri imageUri;
    private Boolean imageControl = false;
    private Dialog dialog;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseUser firebaseUser;

    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;

    private TextView personalDetails, changePassword, publishedRides;

    private Handler handler;
    ALoadingDialog aLoadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        profileName = view.findViewById(R.id.profileName);
        profilePhoto = view.findViewById(R.id.yourRidesProfile);
        updateProfile = view.findViewById(R.id.profileEditImage);
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        TextView logout = view.findViewById(R.id.profileLogout);
        personalDetails = view.findViewById(R.id.profileEditDetails);
        changePassword = view.findViewById(R.id.profileChangePassword);
        publishedRides = view.findViewById(R.id.publishedRides);

        handler = new Handler(Looper.getMainLooper());
        aLoadingDialog = new ALoadingDialog(getContext());

        loadProfileData();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                auth.signOut();
                getActivity().finish();
                startActivity(new Intent(getContext(), LoginPage.class));

            }
        });

        publishedRides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(),PublishedRides.class);
                startActivity(i);
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ChangePassword.class);
                startActivity(i);
            }
        });

        personalDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), UpdateProfileDetailsActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    public void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(profilePhoto);
            imageControl = true;
            dialog = new Dialog(getContext());
            dialog.setContentView(R.layout.activity_save_changes_dialoge);
            dialog.getWindow().setBackgroundDrawable(getContext().getDrawable(R.drawable.background_dialoge));
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.setCancelable(false);
            dialog.show();
            Button yes = dialog.findViewById(R.id.saveChangesYes);
            Button no = dialog.findViewById(R.id.saveChangesNo);
            yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateProfile();
                }
            });
            no.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        } else {
            imageControl = false;
        }
    }

    public void updateProfile() {
        dialog.dismiss();
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
                                            showToast("Updated successfully");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            showToast("Error in updating picture!");
                                        }
                                    });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    showToast("Error in uploading image!");
                }
            });
        } else {
            reference.child("Users").child(auth.getUid()).child("image").setValue(image);
        }
    }

    private void loadProfileData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String userId = auth.getUid();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);

                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String firstName = snapshot.child("First Name").getValue(String.class);
                        String LastName = snapshot.child("Last Name").getValue(String.class);
                        image = snapshot.child("image").getValue().toString();

                        if (firstName != null) {
                            updateUI(new Runnable() {
                                @Override
                                public void run() {
                                    profileName.setText(firstName + " " + LastName);
                                }
                            });
                        }

                        if (image.equals("null")) {
                            updateUI(new Runnable() {
                                @Override
                                public void run() {
                                    profilePhoto.setImageResource(R.drawable.account);
                                }
                            });
                        } else {
                            updateUI(new Runnable() {
                                @Override
                                public void run() {
                                    Picasso.get().load(image).into(profilePhoto);
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).start();
    }

    private void updateUI(Runnable runnable) {

        handler.post(runnable);
    }

    private void showToast(String message) {
        updateUI(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
