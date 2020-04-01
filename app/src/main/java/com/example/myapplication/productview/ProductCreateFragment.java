package com.example.myapplication.productview;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.NavigationHost;
import com.example.myapplication.R;
import com.example.myapplication.productview.dto.ProductCreateDTO;
import com.example.myapplication.productview.dto.ProductCreateResultDTO;
import com.example.myapplication.productview.network.ProductNetworkService;
import com.example.myapplication.utils.network.CommonUtils;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductCreateFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_product_create, container, false);

        MaterialButton addButton = view.findViewById(R.id.add_button);

        // Set an error if the password is less than 8 characters.
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  ((NavigationHost) getActivity()).navigateTo(new ProductCreateFragment(), false);

               // Toast.makeText(getActivity(), "Add ", Toast.LENGTH_SHORT).show();
                ProductCreateDTO productCreateDTO=new ProductCreateDTO("Носки", "25");
                CommonUtils.showLoading(getActivity());
                ProductNetworkService.getInstance()
                        .getJSONApi()
                        .CreateRequest(productCreateDTO)
                        .enqueue(new Callback<ProductCreateResultDTO>() {
                            @Override
                            public void onResponse(@NonNull Call<ProductCreateResultDTO> call, @NonNull Response<ProductCreateResultDTO> response) {
                                if (response.isSuccessful()) {
                                    ProductCreateResultDTO resultDTO = response.body();
                                    ((NavigationHost) getActivity()).navigateTo(new ProductGridFragment(), false); // Navigate to the products Fragment
                                  //  Log.e(TAG, "*************GOOD Request***********" + tokenDTO.getToken());
                                } else {
                                  //  Log.e(TAG, "_______________________" + response.errorBody().charStream());

//                                    try {
//                                        String json = response.errorBody().string();
//                                        Gson gson = new Gson();
//                                        LoginDTOBadRequest resultBad = gson.fromJson(json, LoginDTOBadRequest.class);
//                                        //Log.d(TAG,"++++++++++++++++++++++++++++++++"+response.errorBody().string());
//                                        errorMessage.setText(resultBad.getInvalid());
//                                    } catch (Exception e) {
//                                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                                    }
                                }
                                CommonUtils.hideLoading();

                                //Log.d(TAG,tokenDTO.toString());
                                //CommonUtils.hideLoading();
                            }

                            @Override
                            public void onFailure(@NonNull Call<ProductCreateResultDTO> call, @NonNull Throwable t) {
                                //CommonUtils.hideLoading();
                                Log.e("ERROR","*************ERORR request***********");
                                t.printStackTrace();
                                CommonUtils.hideLoading();
                            }
                        });

            }
        });


        return view;
    }
}