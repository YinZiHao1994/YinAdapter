# YinAdapter
一个用于RecycleView的通用Adapter库

# 引入
compile 'com.yinzihao:YinAdapter:{latest-version}'

# 使用指南

## BaseAdapter&lt;T&gt;  
适用于只有一种 ItemViewType 的列表。其中泛型 T 表示数据源的类型。  

```
    BaseAdapter<PersonBean> baseAdapter = new BaseAdapter<PersonBean>(this, personBeanList, R.layout.item_male){
            @Override
            public void onDataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                //绑定数据
                viewHolder.getTextView(R.id.tv_name).setText(data.getName());
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, int position) {
                //整个列表项的点击事件
                Toast.makeText(context, "onItemClick position = " + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position) {
                //整个列表项的长按事件
                Toast.makeText(context, "onItemLongClick position = " + position, Toast.LENGTH_SHORT).show();
                return false;
            }
        }
```
  
在构造函数中传入数据源和列表项的布局文件，在`onDataBind`中绑定数据源内容到布局中既可完成显示。

![same_type](https://github.com/YinZiHao1994/YinAdapter/blob/master/sample/src/main/res/drawable/same_type.png)

## CommonAdapter&lt;T&gt;  
适用于有超过一种 ItemViewType 的列表（例如聊天列表分我的和对方的）。  

```
    CommonAdapter<PersonBean> commonAdapter = new CommonAdapter<PersonBean>(context, personBeanList) {
        @Override
        public List<DataType<PersonBean>> getDataTypes() {
            DataType<PersonBean> maleDateType = new DataType<PersonBean>() {
                @Override
                public int getLayoutId() {
                    return R.layout.item_male;
                }

                @Override
                public boolean isMatching(PersonBean data, int position) {
                    return data.getSex() == 1;
                }

                @Override
                public void dataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                    viewHolder.getTextView(R.id.tv_name).setText(data.getName());
                    viewHolder.<TextView>getView(R.id.tv_sex).setText("男");
                }

                @Override
                public void onItemClick(CommonViewHolder commonViewHolder, View view, int position) {
                    Toast.makeText(context, "onItemClick position = " + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position) {
                    Toast.makeText(context, "onItemLongClick position = " + position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            };

            DataType<PersonBean> femaleDateType = new DataType<PersonBean>() {
                @Override
                public int getLayoutId() {
                    return R.layout.item_female;
                }

                @Override
                public boolean isMatching(PersonBean data, int position) {
                    return data.getSex() == 0;
                }

                @Override
                public void dataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                    viewHolder.getTextView(R.id.tv_name).setText(data.getName());
                    viewHolder.<TextView>getView(R.id.tv_sex).setText("女");
                }

                @Override
                public void onItemClick(CommonViewHolder commonViewHolder, View view, int position) {
                    Toast.makeText(context, "onItemClick position = " + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, int position) {
                    Toast.makeText(context, "onItemLongClick position = " + position, Toast.LENGTH_SHORT).show();
                    return false;
                }
            };
            List<DataType<PersonBean>> list = new ArrayList<>();
            list.add(maleDateType);
            list.add(femaleDateType);
            return list;
        }
    };

```
  
`CommonAdapter` 需要你自己拼装出 `DataType` 的列表在抽象方法中实现返回。  
注意每个 `DataType` 中的 `isMatching()` 方法需要判断数据源是否应该属于当前的 `DataType`，并且注意唯一性，如果一个数据即匹配一种 `DataType` 又匹配了另一种 `DataType`，则会抛出异常。  
  
![different_type]
(https://github.com/YinZiHao1994/YinAdapter/blob/master/sample/src/main/res/drawable/different_type.png)

## LoadMoreWrapperAdapter<PersonBean>&lt;T&gt;  
当需要上拉列表加载更多功能时，只需要使用装饰者模式在原来的 `adapter` 上装饰 `LoadMoreWrapperAdapter` 作为新的 `adapter` 既可。  
```
LoadMoreWrapperAdapter<PersonBean> loadMoreWrapperAdapter = new LoadMoreWrapperAdapter<>(adapter);
```
  
如果需要自定义加载更多栏的布局样式，则使用:
```
LoadMoreWrapperAdapter<PersonBean> loadMoreWrapperAdapter = new LoadMoreWrapperAdapter<>(adapter, R.layout.load_more_layout);
```
  
在 `recyclerView` 的 `addOnScrollListener()` 中设置 `LoadMoreWrapperAdapter.OnLoadMoreListener` 实现加载更多事件的回调  

```
    recyclerView.addOnScrollListener(new LoadMoreWrapperAdapter.OnLoadMoreListener() {
        @Override
        public void onLoadMore() {
            if (personBeanList.size() > 22 * 10) {
                loadMoreWrapperAdapter.noMoreToLoad();
                return;
            }
            List<PersonBean> init = PersonBean.init();
            personBeanList.addAll(init);
            loadMoreWrapperAdapter.loadFinish();
            loadMoreWrapperAdapter.notifyDataSetChanged();
        }
    });
```
  
注意，当本次加载结束时需要手动调用 `loadMoreWrapperAdapter` 的 `loadFinish()` 方法。当没有更多数据时需要手动调用 `loadMoreWrapperAdapter` 的 `noMoreToLoad()` 方法。

![load_more](https://github.com/YinZiHao1994/YinAdapter/blob/master/sample/src/main/res/drawable/load_more.gif)

