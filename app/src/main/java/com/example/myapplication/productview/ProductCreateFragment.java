package com.example.myapplication.productview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.NavigationHost;
import com.example.myapplication.R;
import com.example.myapplication.productview.dto.ProductCreateDTO;
import com.example.myapplication.productview.dto.ProductCreateInvalidDTO;
import com.example.myapplication.productview.dto.ProductCreateResultDTO;
import com.example.myapplication.productview.network.ProductNetworkService;
import com.example.myapplication.utils.network.CommonUtils;
import com.example.myapplication.utils.network.FileUtil;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCreateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    public static final int PICKFILE_RESULT_CODE = 1;
    ImageView chooseImage;
    String chooseImageBase64;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_product_create, container, false);

        Button btnSelectImage = view.findViewById(R.id.btnSelectImage);

        chooseImage = (ImageView) view.findViewById(R.id.chooseImage);

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                chooseFile.setType("image/*");
                chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);
            }
        });

        MaterialButton addButton = view.findViewById(R.id.add_button);
        final TextInputEditText titleEditText = view.findViewById(R.id.product_title_edit_text);
        final TextInputEditText priceEditText = view.findViewById(R.id.price_edit_text);
        final TextView errormessage = view.findViewById(R.id.error_message);


        // Set an error if the password is less than 8 characters.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  ((NavigationHost) getActivity()).navigateTo(new ProductCreateFragment(), false);

               // Toast.makeText(getActivity(), "Add ", Toast.LENGTH_SHORT).show();
                ProductCreateDTO productCreateDTO=new ProductCreateDTO(titleEditText.getText().toString(),
                        priceEditText.getText().toString(), chooseImageBase64);
                CommonUtils.showLoading(getActivity());
                ProductNetworkService.getInstance()
                        .getJSONApi()
                        .CreateRequest(productCreateDTO)
                        .enqueue(new Callback<ProductCreateResultDTO>() {
                            @Override
                            public void onResponse(@NonNull Call<ProductCreateResultDTO> call, @NonNull Response<ProductCreateResultDTO> response) {
                                CommonUtils.hideLoading();
                                errormessage.setText("");
                                if (response.isSuccessful()) {
                                    ProductCreateResultDTO resultDTO = response.body();
                                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the products Fragment
                                  //  Log.e(TAG, "*************GOOD Request***********" + tokenDTO.getToken());
                                } else {
                                  //  Log.e(TAG, "_______________________" + response.errorBody().charStream());

                                    try {
                                        String json = response.errorBody().string();
                                        Gson gson  = new Gson();
                                        ProductCreateInvalidDTO resultBad = gson.fromJson(json, ProductCreateInvalidDTO.class);
                                        //Log.d(TAG,"++++++++++++++++++++++++++++++++"+response.errorBody().string());
                                        errormessage.setText(resultBad.getInvalid());
                                    } catch (Exception e) {
                                        //Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(@NonNull Call<ProductCreateResultDTO> call, @NonNull Throwable t) {
                                CommonUtils.hideLoading();
                                Log.e("ERROR","*************ERORR request***********");
                                t.printStackTrace();

                            }
                        });

            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICKFILE_RESULT_CODE:
                if (resultCode == -1) {
                    Uri fileUri = data.getData();
                    try {
                        File imgFile = FileUtil.from(this.getActivity(), fileUri);

                        byte[] buffer = new byte[(int) imgFile.length() + 100];
                        int length = new FileInputStream(imgFile).read(buffer);
                        chooseImageBase64 = Base64.encodeToString(buffer, 0, length, Base64.NO_WRAP);
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        chooseImage.setImageBitmap(myBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
        }
    }
}