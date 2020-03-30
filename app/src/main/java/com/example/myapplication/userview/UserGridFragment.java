package com.example.myapplication.userview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.jsonretro.ProdNetworkService;
import com.example.myapplication.jsonretro.ProductDTO;
import com.example.myapplication.jsonretro.UserDTO;
import com.example.myapplication.jsonretro.UserNetworkService;
import com.example.myapplication.network.ProductEntry;
import com.example.myapplication.network.UserEntry;
import com.example.myapplication.productview.ProductCardRecyclerViewAdapter;
import com.example.myapplication.productview.ProductGridFragment;
import com.example.myapplication.productview.ProductGridItemDecoration;
import com.example.myapplication.utils.network.CommonUtils;
import com.example.myapplication.utils.network.RequestErrorNavigate;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserGridFragment extends Fragment {

    private static final String TAG = UserGridFragment.class.getSimpleName();

    RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_grid, container, false);

        // Set up the RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1,
                GridLayoutManager.VERTICAL, false));

//        List<UserEntry> list = UserEntry.initUserEntryList(getResources());
//        UserCardRecyclerViewAdapter adapter = new UserCardRecyclerViewAdapter(list);
//
//        recyclerView.setAdapter(adapter);

        int largePadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing);
        int smallPadding = getResources().getDimensionPixelSize(R.dimen.shr_product_grid_spacing_small);

        recyclerView.addItemDecoration(new UserGridItemDecoration(largePadding, smallPadding));

        UserNetworkService.getInstance()
                .getJSONApi()
                .getAllUsers()
                .enqueue(new Callback<List<UserDTO>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<UserDTO>> call, @NonNull Response<List<UserDTO>> response) {
                        CommonUtils.hideLoading();
                        List<UserDTO> list = response.body();
                       // String res = list.get(0).toString();
                        // Log.d(TAG, "--------result server-------" + res);

                        List<UserEntry> newlist = new ArrayList<UserEntry>();//ProductEntry.initProductEntryList(getResources());
                        for (UserDTO item : list) {
                            UserEntry pe = new UserEntry(item.getName(),item.getUrl(),item.getUrl(),item.getSurname(),item.getCountry(),item.getCity(), item.getAge());
                            newlist.add(pe);
                        }
                        UserCardRecyclerViewAdapter newAdapter = new UserCardRecyclerViewAdapter(newlist);

                        recyclerView.swapAdapter(newAdapter, false);


                        //Toast.makeText(getActivity(), "Hello result"+ res.size(), Toast.LENGTH_LONG);
                    }

                    @Override
                    public void onFailure(Call<List<UserDTO>> call, Throwable t) {
                        CommonUtils.hideLoading();
                        ((RequestErrorNavigate) getActivity()).navigateErrorPage(new ProductGridFragment(), false, t.getMessage()); // Navigate to the next Fragment

                        t.printStackTrace();
                    }
                });
        return view;
    }
}
