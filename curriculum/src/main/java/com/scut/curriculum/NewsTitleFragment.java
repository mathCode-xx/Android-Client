package com.scut.curriculum;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {
    private boolean isTwopane;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_title_frag, container, false);
        RecyclerView newsTitleRecycleView = (RecyclerView) view.findViewById(R.id
                .news_titile_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        newsTitleRecycleView.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter(getNews());
        newsTitleRecycleView.setAdapter(adapter);
        return view;
    }

    private List<News> getNews() {
        List<News> newsList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            News news = new News();
            if (i==1){
                news.setTitle("我和冰雪奇缘不得不说的故事");//在这里添加标题
                //在下面添加内容
                news.setContent("  那位姐姐好像见过，大概是梦里花落时见过吧。\n" +
                        "  正值秋天的季节，南方的秋却从来不会显有半分枯黄的感觉，天色湛蓝，白云千里，这就是广州的秋吗，竟如此温柔可人。\n" +
                        "  “同学，了解一下，现在办校园电话卡有优惠，就选我们电信的卡啊，网速有保障，欸，同学，别走那么快啊，再看看啊。”\n" +
                        "  我早就不是这里的新生，甚至也不能算和这个学校有什么过多的关系，自从那件“事情”发生后，我就离开了这里，但我并不后悔，这是我心中的少年做出的选择。虽然如此，" +
                        "哪处没有欺骗新生的各种手段，当年深入传销一线和敌人的美人计斗智斗勇可仍历历在目，我的学生时代，从来都是闪耀着金色灿烂的光芒，我从不展现也从不述说，电话卡" +
                        "欺诈不过尔尔。\n" +
                        "  不过今天我是来找人的，来寻找那些已经快在我记忆中淡去的记忆，或许找到他们，就能找到“少年”。 ————by ccch 未完待续\n" );

            }else{
                news.setTitle("测试");
                news.setContent("测试");

            }

            newsList.add(news);
        }
        return newsList;
    }

    private String getRandomLengthContent(String content) {
        Random random = new Random();
        int length = random.nextInt(20) + 1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(content);
        }
        return builder.toString();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isTwopane = false;
    }

    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        private List<News> mNewsList;

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView newsTitleText;

            public ViewHolder(View View) {
                super(View);
                newsTitleText = (TextView) View.findViewById(R.id.news_title);
            }
        }

        public NewsAdapter(List<News> newsList) {
            mNewsList = newsList;
        }


        @NonNull
        @Override
        public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.news_item, parent, false);
            final ViewHolder holder = new ViewHolder(view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if (isTwopane) {
                        //如果是双页模式
                        assert getFragmentManager() != null;
                        NewsContentFragment newsContentFragment =
                                (NewsContentFragment) getFragmentManager()
                                        .findFragmentById(R.id.news_content_fragment);
                        assert newsContentFragment != null;
                        newsContentFragment.refresh(news.getTitle(), news.getContent());
                    } else {
                        //如果为单页模式
                        NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getContent());

                    }
                }

            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull NewsAdapter.ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());
        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }


    }

}
