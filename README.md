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

<div align=center><img width="270" height="510" src="https://github.com/YinZiHao1994/YinAdapter/blob/master/demoimage/same_type.png"/></div>

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

    @Nullable
    @Override
    public DataTypeForTheRest<PersonBean> getDefaultDataTypesForRest() {
        return new DataTypeForTheRest<PersonBean>() {
            @Override
            public int getLayoutRes() {
                return R.layout.item_the_rest;
            }

            @Override
            public void dataBind(CommonViewHolder viewHolder, PersonBean data, int position) {
                viewHolder.getTextView(R.id.tv_name).setText(data.getName());
                viewHolder.<TextView>getView(R.id.tv_sex).setText("特别");
            }

            @Override
            public void onItemClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {

            }

            @Override
            public boolean onItemLongClick(CommonViewHolder commonViewHolder, View view, PersonBean data, int position) {
                return false;
            }
        };
    }
    };

```
  
`CommonAdapter` 需要你自己在 `getDataTypes()` 抽象方法中列出你需要区分显示和处理的 `DataType` ，然后拼装成列表返回。
注意每个 `DataType` 中的 `isMatching()` 方法需要判断数据源是否应该属于当前的 `DataType`，并且注意唯一性，如果一个数据即匹配一种 `DataType` 又匹配了另一种 `DataType`，则会抛出异常。  
`getDefaultDataTypesForRest()` 方法中可以返回一个 `DataTypeForTheRest` 对象，当数据源不匹配 `getDataTypes()` 中的任何类型时，会匹配上此对象，你可以统一处理它们的显示。

<div align=center><img width="270" height="510" src="https://github.com/YinZiHao1994/YinAdapter/blob/master/demoimage/different_type.png"/></div>

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
        public void onRefresh() {
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

<div align=center><img width="270" height="510" src="https://github.com/YinZiHao1994/YinAdapter/blob/master/demoimage/load_more.gif"/></div>

