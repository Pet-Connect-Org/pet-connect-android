package com.example.petconnect.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petconnect.R;
import com.example.petconnect.adapter.TagCharacterAdapter;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.ExtendedPet;
import com.example.petconnect.models.TagCharacter;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.pet.GetPetProfileRequest;
import com.example.petconnect.services.pet.GetPetProfileRespone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetProfileActivity extends AppCompatActivity {
    TextView typeOfPet, breedTV, nameTV, genderTV, dobTV, favouriteFoodTV;
    ImageView petImage;
    RecyclerView recyclerViewTagCharacter;
    UserManager userManager;
    private List<TagCharacter> tagCharacterList;
    private TagCharacterAdapter tagCharacterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        userManager = new UserManager(this);

        // Ánh xạ các thành phần giao diện
        typeOfPet = findViewById(R.id.type_of_pet);
        breedTV = findViewById(R.id.breed);
        nameTV = findViewById(R.id.name);
        dobTV = findViewById(R.id.dob);
        genderTV = findViewById(R.id.gender);
        favouriteFoodTV = findViewById(R.id.favorite_food);
        petImage = findViewById(R.id.pet_img);
        recyclerViewTagCharacter = findViewById(R.id.recyclerViewTagCharacter);

        // Khởi tạo danh sách tagCharacterList
        tagCharacterList = new ArrayList<>();

        // Khởi tạo adapter cho RecyclerView
        tagCharacterAdapter = new TagCharacterAdapter(this, tagCharacterList);
        recyclerViewTagCharacter.setAdapter(tagCharacterAdapter);
        recyclerViewTagCharacter.setLayoutManager(new LinearLayoutManager(this));

        // Lấy petId từ Intent và tải dữ liệu của thú cưng
        Intent intent = getIntent();
        if (intent != null) {
            int petId = intent.getIntExtra("petId", -1);
            if (petId != -1) {
                loadPetData(petId);
            } else {
                Toast.makeText(this, "Invalid pet data.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No pet data found.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadPetData(int petId) {
        String token = userManager.getAccessToken();
        // Tạo đối tượng GetPetProfileRequest và đặt id cho nó
        GetPetProfileRequest request = new GetPetProfileRequest(petId);
        ApiService.apiService.getPetProfile("Bearer " + token, request.getId()).enqueue(new Callback<GetPetProfileRespone>() {
            @Override
            public void onResponse(Call<GetPetProfileRespone> call, Response<GetPetProfileRespone> response) {
                if (response.isSuccessful()) {
                    GetPetProfileRespone petResponse = response.body();
                    if (petResponse != null) {
                        ExtendedPet pet = petResponse.getData();
                        if (pet != null) {
                            Log.d("PetProfileActivity", "Med data: " + pet.getMed().toString());
                            displayPetData(pet);
                        } else {
                            Toast.makeText(PetProfileActivity.this, "Invalid pet data received from server", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(PetProfileActivity.this, "Invalid response from server", Toast.LENGTH_SHORT).show();
                    }
                } else {
//                    Toast.makeText(PetProfileActivity.this, "Failed to fetch pet data." + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("PetProfileActivity", "Failed to fetch pet data: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<GetPetProfileRespone> call, Throwable t) {
//                Toast.makeText(PetProfileActivity.this, "Failed to fetch pet data." + t.getCause(), Toast.LENGTH_SHORT).show();
//                Log.e("PetProfileActivity", "Failed to fetch pet data: " + t.getMessage());

            }
        });
    }


    private void displayPetData(ExtendedPet pet) {
        // Hiển thị thông tin cơ bản
        typeOfPet.setText(pet.getPet_type().getType());
        breedTV.setText(pet.getPet_type().getName());
        nameTV.setText(pet.getName());
        dobTV.setText(pet.getBirthday());
        genderTV.setText(pet.getSex());


        // Hiển thị favorite food
        String favoriteFood = pet.getMed().getFavoriteFood();
        List<String> favoriteFoodList = Arrays.asList(favoriteFood.split(","));
        favouriteFoodTV.setText(TextUtils.join(", ", favoriteFoodList));

        // Hiển thị các tag character
        List<TagCharacter> tagCharacters = new ArrayList<>();
        if (pet.getMed().isFriendlyWithDog()) {
            tagCharacters.add(new TagCharacter("Friendly with Dogs"));
        }
        if (pet.getMed().isFriendlyWithCat()) {
            tagCharacters.add(new TagCharacter("Friendly with Cats"));
        }
        if (pet.getMed().isCleanProperly()) {
            tagCharacters.add(new TagCharacter("Clean Properly"));
        }
        if (pet.getMed().isHyperactive()) {
            tagCharacters.add(new TagCharacter("Hyperactive"));
        }
        if (pet.getMed().isFriendlyWithKid()) {
            tagCharacters.add(new TagCharacter("Friendly with Kids"));
        }
        if (pet.getMed().isShy()) {
            tagCharacters.add(new TagCharacter("Shy"));
        }

        // Cập nhật RecyclerView
        tagCharacterList.clear();
        tagCharacterList.addAll(tagCharacters);
        tagCharacterAdapter.notifyDataSetChanged();

        }
    }
