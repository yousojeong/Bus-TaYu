package org.devTayu.busTayu.ui.liked;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import org.devTayu.busTayu.R;
import org.devTayu.busTayu.adapter.LikedAdapter;
import org.devTayu.busTayu.database.TaYuDatabase;
import org.devTayu.busTayu.model.LikedDB;
import org.devTayu.busTayu.ui.station.LikedAPI;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LikedFragment extends Fragment {

    private RecyclerView mPostRecyclerView;
    private LikedAdapter mAdpater;
    private List<LikedDB> mDatas;
    private TaYuDatabase db;
    private LikedDB likedDB;
    private TextView mResultTextView;
    LikedAPI likedAPI;

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_liked, container, false);

        new Thread(new Runnable() {
            @Override
            public void run() {
                // likedAPI = new LikedAPI();
                try {
                    // mDatas = likedAPI.liked_arsId("15172", "양천04");
                    // 데이터 추가
                    mDatas = new ArrayList<>();
                    // mDatas.add(new LikedDB("버스 번호", "[ 정류장 번호 ]", "정류장 명"));

                    Log.d("유소정 bindList ", "LikedFragment : recyclerView: " + mDatas.size());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            bindList(root);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }).start();

        // 쓸어서 새로고침 : swipeRefreshLayout
        /*SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.liked_SwipeLayout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getContext(), "새로고침1", Toast.LENGTH_SHORT).show();

                        // bindList(station_num, bus_name);
                        Log.d("유소정 bindList ", "스크롤로 bindList 호출");

                        mSwipeRefreshLayout.setRefreshing(false); // false : 새로고침 중지
                    }
                }, 500);
            }
        });*/

        // 플로팅 새로고침 : floatingActionButton
        /*FloatingActionButton fab = (FloatingActionButton) getView().findViewById(R.id.liked_floatingbtn);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "새로고침2", Toast.LENGTH_SHORT).show();

                // bindList(station_num, bus_name);
                Log.d("유소정 bindList ", "플로팅으로 bindList 호출");
            }
        });*/

        // 데이터베이스 생성
        db = Room.databaseBuilder(getContext(), TaYuDatabase.class, "TaYu_database").build();
        // UI 갱신 (라이브데이터 Observer 이용, 해당 디비값이 변화가생기면 실행됨)
        db.likedDAO().getAll().observe(getViewLifecycleOwner(), new Observer<List<LikedDB>>() {
            @Override
            public void onChanged(List<LikedDB> dataList) {
                // mResultTextView.setText(dataList.toString());
                mDatas.addAll(dataList);
                /*
                mDatas.addAll(dataList); 와 같음
                for (int i = 0; i < dataList.size(); i++) {
                    mDatas.add(dataList.get(i));
                }
                */
            }
        });

        /* 임시 사용 */
        // mResultTextView = root.findViewById(R.id.textview11111);
        // mResultTextView.setText(db.likedDAO().getAll().toString());

        return root;
    }

    public void bindList(View root) {
        mPostRecyclerView = getView().findViewById(R.id.recyclerView_liked);
        mPostRecyclerView.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화

        // Adapter, LayoutManager 연결
        mAdpater = new LikedAdapter(mDatas);
        mPostRecyclerView.setAdapter(mAdpater);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        /*LikedAPI 임시 실행 코드*/
        //likedAPI = new LikedAPI();
        //likedAPI.liked_arsId("01004", "N37");
    }
}
/*@Override
public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    new Thread(new Runnable() {
        @Override
        public void run() {

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //initUI(root);
                }
            });
        }
    }).start();
}*/
/*
public class LikedFragment extends Fragment {
    private LikedViewModel likedViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        likedViewModel =
                new ViewModelProvider(this).get(LikedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_liked, container, false);
        final TextView textView = root.findViewById(R.id.text_liked);
        likedViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
*/