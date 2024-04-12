package com.example.petconnect.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.petconnect.CustomDropdown;
import com.example.petconnect.CustomTextfield;
import com.example.petconnect.Item;
import com.example.petconnect.R;
import com.example.petconnect.databinding.ActivityCreateNewPetBinding;
import com.example.petconnect.databinding.ActivityMainBinding;
import com.example.petconnect.manager.UserManager;
import com.example.petconnect.models.PetType;
import com.example.petconnect.services.ApiService;
import com.example.petconnect.services.pet.CreateNewPetProfileRequest;
import com.example.petconnect.services.pet.CreateNewPetProfileResponse;
import com.example.petconnect.services.petType.GetPetTypeListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.squareup.picasso.Picasso;

public class CreateNewPetActivity extends DrawerBaseActivity {
    LinearLayout pet_characteristic, pet_favorite_food, pet_basic_information;

    CustomDropdown pet_type_name, pet_breed_name, pet_sex;

    ImageView pet_avatar;
    CustomTextfield pet_birthday, pet_name, pet_description, pet_other_food;
    ActivityCreateNewPetBinding activityCreateNewPetBinding;
    Button pet_next_step_2, pet_next_step_3, pet_confirm;
    UserManager userManager;
    Map<String, List<PetType>> groupedPetTypes = new HashMap<>();
    CheckBox dry, wet, fresh, cooked, seafood, raw;

    String selectedImage, favoriteFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCreateNewPetBinding = ActivityCreateNewPetBinding.inflate(getLayoutInflater());
        setContentView(activityCreateNewPetBinding.getRoot());

        userManager = new UserManager(this);

        fetchPetTypeList();

        pet_basic_information = findViewById(R.id.pet_basic_information);
        pet_favorite_food = findViewById(R.id.pet_favorite_food);
        pet_characteristic = findViewById(R.id.pet_characteristic);

        pet_basic_information.setVisibility(View.VISIBLE);

        pet_type_name = findViewById(R.id.pet_type_name);
        pet_breed_name = findViewById(R.id.pet_breed_name);
        pet_sex = findViewById(R.id.pet_sex);
        pet_birthday = findViewById(R.id.pet_birthday);
        pet_avatar = findViewById(R.id.pet_avatar);
        pet_name = findViewById(R.id.pet_name);
        pet_description = findViewById(R.id.pet_description);

        pet_next_step_2 = findViewById(R.id.pet_next_step_2);

        ArrayList<Item> list = new ArrayList<>();

        list.add(new Item("male", "Male"));
        list.add(new Item("female", "Female"));
        pet_sex.setItems(list);

        pet_next_step_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pet_breed_name.getSelectedItemKey() == null || pet_name.getText() == "" || pet_sex.getSelectedItemKey() == null || pet_birthday.getText() == "" || pet_description.getText() == "") {
                    Toast.makeText(CreateNewPetActivity.this, "Need fill all value.", Toast.LENGTH_SHORT).show();
                    return;
                }
                pet_basic_information.setVisibility(View.GONE);
                pet_favorite_food.setVisibility(View.VISIBLE);
            }
        });

//SECTION 2

        dry = findViewById(R.id.dry);
        wet = findViewById(R.id.wet);
        fresh = findViewById(R.id.fresh);
        cooked = findViewById(R.id.cooked);
        seafood = findViewById(R.id.seafood);
        raw = findViewById(R.id.raw);
        pet_other_food = findViewById(R.id.pet_other_food);
        pet_next_step_3 = findViewById(R.id.pet_next_step_3);


        pet_next_step_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> selectedFoods = new ArrayList<>();

                if (dry.isChecked()) {
                    selectedFoods.add(dry.getText().toString());
                }
                if (wet.isChecked()) {
                    selectedFoods.add(wet.getText().toString());
                }
                if (fresh.isChecked()) {
                    selectedFoods.add(fresh.getText().toString());
                }
                if (cooked.isChecked()) {
                    selectedFoods.add(cooked.getText().toString());
                }
                if (seafood.isChecked()) {
                    selectedFoods.add(seafood.getText().toString());
                }
                if (raw.isChecked()) {
                    selectedFoods.add(raw.getText().toString());
                }

                if (selectedFoods.size() == 0) {
                    Toast.makeText(CreateNewPetActivity.this, "Please select at least 1 food.", Toast.LENGTH_SHORT).show();
                } else {
                    favoriteFoods = TextUtils.join(",", selectedFoods) + pet_other_food.getText().trim();
                    pet_favorite_food.setVisibility(View.GONE);
                    pet_characteristic.setVisibility(View.VISIBLE);
                }

            }
        });


//        STEP 3

        pet_confirm = findViewById(R.id.pet_confirm);

        pet_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService.apiService.createPetProfile("Bearer " + userManager.getAccessToken(), new CreateNewPetProfileRequest(
                        pet_name.getText(), pet_birthday.getText(),
                        pet_sex.getSelectedItemKey(), pet_description.getText(),
                        selectedImage, favoriteFoods, Integer.parseInt(pet_breed_name.getSelectedItemKey()),
                        getAnswer(R.id.radioGroupFriendlyWithDogs),
                        getAnswer(R.id.radioGroupFriendlyWithCats),
                        getAnswer(R.id.radioGroupFriendlyWithChildren),
                        getAnswer(R.id.radioGroupTrainedToilet),
                        getAnswer(R.id.radioGroupHyperactive),
                        getAnswer(R.id.radioGroupUsuallyShy)
                )).enqueue(new Callback<CreateNewPetProfileResponse>() {
                    @Override
                    public void onResponse(Call<CreateNewPetProfileResponse> call, Response<CreateNewPetProfileResponse> response) {
                        Toast.makeText(CreateNewPetActivity.this, "Create new pet profile success", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<CreateNewPetProfileResponse> call, Throwable t) {
                        Toast.makeText(CreateNewPetActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }

    private boolean getAnswer(int radioGroupId) {
        RadioGroup radioGroup = findViewById(radioGroupId);
        boolean answer = radioGroup.getCheckedRadioButtonId() == radioGroup.getChildAt(0).getId();
        return answer;
    }


    public void fetchPetTypeList() {

        ApiService.apiService.getPetTypeList("Bearer " + userManager.getAccessToken()).enqueue(new Callback<GetPetTypeListResponse>() {
            @Override
            public void onResponse(Call<GetPetTypeListResponse> call, Response<GetPetTypeListResponse> response) {
                groupPetsByType(response.body().getData());
                updateUIWithPetTypes();
            }

            @Override
            public void onFailure(Call<GetPetTypeListResponse> call, Throwable t) {
            }
        });
    }


    private void updateUIWithPetTypes() {
        ArrayList<Item> petTypeItems = new ArrayList<>();
        for (String petType : groupedPetTypes.keySet()) {
            petTypeItems.add(new Item(petType, petType));

        }

        String defaultKey = petTypeItems.get(0).getKey();
        pet_type_name.setItems(petTypeItems);
        pet_type_name.setDefaultItem(defaultKey);
        setBreedName(defaultKey);

        pet_type_name.setOnItemSelectedListener(new CustomDropdown.OnItemSelectedListener() {
            @Override
            public void onItemSelected(String key) {
                setBreedName(key);
            }
        });
    }

    private void setBreedName(String key) {
        ArrayList<Item> petBreedItems = new ArrayList<>();

        for (PetType pet : groupedPetTypes.get(key)) {
            petBreedItems.add(new Item(String.valueOf(pet.getId()), pet.getName()));
        }

        pet_breed_name.setItems(petBreedItems);

        pet_breed_name.setOnItemSelectedListener(new CustomDropdown.OnItemSelectedListener() {
            @Override
            public void onItemSelected(String id) {

                for (PetType pet : groupedPetTypes.get(key)) {
                    if (pet.getId() == Integer.parseInt(id)) {
                        selectedImage = pet.getImage();
                        Picasso.with(CreateNewPetActivity.this).load(pet.getImage()).fit().into(pet_avatar);
                        break;
                    }
                }


            }
        });
    }

    private void groupPetsByType(ArrayList<PetType> pets) {
        Map<String, List<PetType>> petsByType = new HashMap<>();

        for (PetType pet : pets) {
            petsByType.computeIfAbsent(pet.getType(), k -> new ArrayList<>()).add(pet);
        }

        groupedPetTypes = petsByType;
    }
}