package com.google.android.pinnedheader.headerlist;

import java.util.List;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;

import ${project_namespace}.R;
import com.google.android.pinnedheader.SelectionItemView;
import com.google.android.pinnedheader.headerlist.ListPinnedHeaderView;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView;
import com.google.android.pinnedheader.headerlist.PinnedHeaderListView.PinnedHeaderAdapter;

public abstract class HeaderAdapter<T> extends ArrayAdapter<T> implements PinnedHeaderAdapter{    
    private boolean mSectionHeaderDisplayEnabled;
    private boolean mPinnedPartitionHeadersEnabled;
    private SectionIndexer mIndexer;
    private int mIndexedPartition = 0;
    
    public static final int PARTITION_HEADER_TYPE = 0;
    private View mHeader;
    private boolean mHeaderVisibility[];

    /**
     * Constructor.
     * 
     * @param context The context
     */
    protected HeaderAdapter(android.content.Context context) {
        super(context, -1);
        
        this.init();
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public HeaderAdapter(android.content.Context context, int resource, int textViewResourceId,
            List<T> objects) {
        super(context, resource, textViewResourceId, objects);
        
        this.init();
    }

    /**
     * Constructor.
     *
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public HeaderAdapter(android.content.Context context, int resource, int textViewResourceId,
            T[] objects) {
        super(context, resource, textViewResourceId, objects);

        this.init();
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param resource The resource
     * @param textViewResourceId The resource id of the text view
     */
    public HeaderAdapter(android.content.Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
        
        this.init();
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public HeaderAdapter(android.content.Context context, int textViewResourceId,
            List<T> objects) {
        super(context, textViewResourceId, objects);
        
        this.init();
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     * @param objects The list of objects of this adapter
     */
    public HeaderAdapter(android.content.Context context, int textViewResourceId, T[] objects) {
        super(context, textViewResourceId, objects);
        
        this.init();
    }

    /**
     * Constructor.
     * 
     * @param context The context
     * @param textViewResourceId The resource id of the text view
     */
    public HeaderAdapter(android.content.Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        
        this.init();
    }
    
    /**
     * Initialize the headers.
     */
    private void init() {
        //Force display for header to true
        this.setSectionHeaderDisplayEnabled(true);
        
        //Force Pinned Header enable to true
        this.setPinnedPartitionHeadersEnabled(true);
        
    }

    public void setData(HeaderSectionIndexer<T> sectionIndexer) {
        this.clear();
        
        if (sectionIndexer != null) {
            List<T> data = sectionIndexer.getItems();            
            if (data != null) {
                this.mIndexer = sectionIndexer;
                for (T item : data) {
                    this.add(item);
                }            
            }
        }
    }
    
    public void setData(Iterable<T> data) {
        this.clear();
                    
        if (data != null) {
            for (T item : data) {
                this.add(item);
            }            
        }
    }
    
    /** (non-Javadoc)
     * @see android.widget.ArrayAdapter#getView(int, android.view.View, android.view.ViewGroup)
     */
    @Override 
    public View getView(int position, View convertView, ViewGroup parent) {
        T item = this.getItem(position);        
        return this.bindView(convertView, 0, item, position);
    }
    
    protected abstract View bindView(View itemView, int partition, T item, int position);
    
    protected void bindSectionHeaderAndDivider(SelectionItemView view, int position) {
        if (isSectionHeaderDisplayEnabled()) {
            Placement placement = getItemPlacementInSection(position);

            // First position, set the contacts number string
//            if (position == 0 && cursor.getInt(ContactQuery.CONTACT_IS_USER_PROFILE) == 1) {
//                view.setCountView(getContactsCount());
//            } else {
//                view.setCountView(null);
//            }
            view.setSectionHeader(placement.sectionHeader);
            view.setDividerVisible(!placement.lastInSection);
        } else {
            view.setSectionHeader(null);
            view.setDividerVisible(true);
//            view.setCountView(null);
        }
    }
    
    public boolean isSectionHeaderDisplayEnabled() {
        return mSectionHeaderDisplayEnabled;
    }

    public void setSectionHeaderDisplayEnabled(boolean flag) {
        this.mSectionHeaderDisplayEnabled = flag;
    }
    
    public int getIndexedPartition() {
        return mIndexedPartition;
    }

    public void setIndexedPartition(int partition) {
        this.mIndexedPartition = partition;
    }
    
    /**
     * Computes the item's placement within its section and populates the {@code placement}
     * object accordingly.  Please note that the returned object is volatile and should be
     * copied if the result needs to be used later.
     */
    public Placement getItemPlacementInSection(int position) {
        if (mPlacementCache.position == position) {
            return mPlacementCache;
        }

        mPlacementCache.position = position;
        if (isSectionHeaderDisplayEnabled()) {
            int section = getSectionForPosition(position);
            if (section != -1 && getPositionForSection(section) == position) {
                mPlacementCache.firstInSection = true;
                mPlacementCache.sectionHeader = (String)getSections()[section];
            } else {
                mPlacementCache.firstInSection = false;
                mPlacementCache.sectionHeader = null;
            }

            mPlacementCache.lastInSection = (getPositionForSection(section + 1) - 1 == position);
        } else {
            mPlacementCache.firstInSection = false;
            mPlacementCache.lastInSection = false;
            mPlacementCache.sectionHeader = null;
        }
        return mPlacementCache;
    }
    
    public SectionIndexer getIndexer() {
        return mIndexer;
    }

    public void setIndexer(SectionIndexer indexer) {
        mIndexer = indexer;
        mPlacementCache.invalidate();
    }

    public Object[] getSections() {
        if (mIndexer == null) {
            return new String[] { " " };
        } else {
            return mIndexer.getSections();
        }
    }
    
    /**
     * @return relative position of the section in the indexed partition
     */
    public int getPositionForSection(int sectionIndex) {
        if (mIndexer == null) {
            return -1;
        }

        return mIndexer.getPositionForSection(sectionIndex);
    }

    /**
     * @param position relative position in the indexed partition
     */
    public int getSectionForPosition(int position) {
        if (mIndexer == null) {
            return -1;
        }

        return mIndexer.getSectionForPosition(position);
    }
    
    public boolean getPinnedPartitionHeadersEnabled() {
        return mPinnedPartitionHeadersEnabled;
    }

    public void setPinnedPartitionHeadersEnabled(boolean flag) {
        this.mPinnedPartitionHeadersEnabled = flag;
    }
    
    /**
     * An item view is displayed differently depending on whether it is placed
     * at the beginning, middle or end of a section. It also needs to know the
     * section header when it is at the beginning of a section. This object
     * captures all this configuration.
     */
    public static final class Placement {
        private int position = ListView.INVALID_POSITION;
        public boolean firstInSection;
        public boolean lastInSection;
        public String sectionHeader;

        public void invalidate() {
            position = ListView.INVALID_POSITION;
        }
    }

    private Placement mPlacementCache = new Placement();

    protected boolean isPinnedPartitionHeaderVisible(int partition) {
        return mPinnedPartitionHeadersEnabled 
//                && hasHeader(partition)
//                && !isPartitionEmpty(partition)
                ;
    }
    
    @Override
    public int getPinnedHeaderCount() {
        if (isSectionHeaderDisplayEnabled() && mPinnedPartitionHeadersEnabled) {
            return this.mIndexer.getSections().length;// + 1;
        } else {
            return 0;
        }
    }

    @Override
    public View getPinnedHeaderView(int viewIndex, View convertView, ViewGroup parent) {
        View view = null;
        
        if (isSectionHeaderDisplayEnabled() && viewIndex == getPinnedHeaderCount() - 1) {
            if (mHeader == null) {
                mHeader = createPinnedSectionHeaderView(this.getContext(), parent);
            }
            view = mHeader;
        } else {
            if (mPinnedPartitionHeadersEnabled/*hasHeader(partition)*/) {
                if (convertView != null) {
                    Integer headerType = (Integer)convertView.getTag();
                    if (headerType != null && headerType == PARTITION_HEADER_TYPE) {
                        view = convertView;
                    }
                }
                if (view == null) {
                    view = newHeaderView(getContext(), viewIndex, null, parent);
                    view.setTag(PARTITION_HEADER_TYPE);
                    view.setFocusable(false);
                    view.setEnabled(false);
                }
                
                if (this.mIndexer != null)
                    bindHeaderView(view, viewIndex, this.mIndexer.getSections()[viewIndex].toString());
            }
        }
        
        return view;
    }

    @Override
    public void configurePinnedHeaders(PinnedHeaderListView listView) {
        if (!mPinnedPartitionHeadersEnabled || this.mIndexer == null) {
            return;
        }

        int size = this.mIndexer.getSections().length;

        // Cache visibility bits, because we will need them several times later on
        if (mHeaderVisibility == null || mHeaderVisibility.length != size) {
            mHeaderVisibility = new boolean[size];
        }

        if (!isSectionHeaderDisplayEnabled()) {
            return;
        }

        int index = getPinnedHeaderCount() - 1;
        if (mIndexer == null || getCount() == 0) {
            listView.setHeaderInvisible(index, false);
        } else {
            int listPosition = listView.getPositionAt(listView.getTotalTopPinnedHeaderHeight());
            int position = listPosition - listView.getHeaderViewsCount();
            int section = mIndexer.getSectionForPosition(position);

            if (section == -1) {
                listView.setHeaderInvisible(index, false);
            } else {
                setPinnedSectionTitle(mHeader, (String)mIndexer.getSections()[section]);
                if (section == 0) {
                    setPinnedHeaderContactsCount(mHeader);
                } else {
                    clearPinnedHeaderContactsCount(mHeader);
                }
                
                // Compute the item position where the next section begins
                int nextSectionPosition = getPositionForSection(section + 1);
                boolean isLastInSection = position == nextSectionPosition - 1;
                listView.setFadingHeader(index, listPosition, isLastInSection);
            }
        }        
    }

    @Override
    public int getScrollPositionForHeader(int viewIndex) {
        return 1;// getPositionForPartition(viewIndex);
    }
    
    protected View createPinnedSectionHeaderView(android.content.Context context, ViewGroup parent) {
        return new ListPinnedHeaderView(context, null);
    }

    protected void setPinnedSectionTitle(View pinnedHeaderView, String title) {
        ((ListPinnedHeaderView)pinnedHeaderView).setSectionHeader(title);
    }

    protected void setPinnedHeaderContactsCount(View header) {
        clearPinnedHeaderContactsCount(header);
    }

    protected void clearPinnedHeaderContactsCount(View header) {
        ((ListPinnedHeaderView)header).setCountView(null);
    }
    
    protected View newHeaderView(android.content.Context context, int partition, android.database.Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.directory_header, parent, false);
    }
    
    protected void bindHeaderView(View view, int partitionIndex, String header) {
//        Partition partition = getPartition(partitionIndex);
//        if (!(partition instanceof DirectoryPartition)) {
//            return;
//        }
//
//        DirectoryPartition directoryPartition = (DirectoryPartition)partition;
//        long directoryId = directoryPartition.getDirectoryId();
//        TextView labelTextView = (TextView)view.findViewById(R.id.label);
//        TextView displayNameTextView = (TextView)view.findViewById(R.id.display_name);
//        if (directoryId == Directory.DEFAULT || directoryId == Directory.LOCAL_INVISIBLE) {
//            labelTextView.setText(R.string.local_search_label);
//            displayNameTextView.setText(null);
//        } else {
//            labelTextView.setText(R.string.directory_search_label);
//            String directoryName = directoryPartition.getDisplayName();
//            String displayName = !TextUtils.isEmpty(directoryName)
//                    ? directoryName
//                    : directoryPartition.getDirectoryType();
//            displayNameTextView.setText(displayName);
//        }
//
//        TextView countText = (TextView)view.findViewById(R.id.count);
//        if (directoryPartition.isLoading()) {
//            countText.setText(R.string.search_results_searching);
//        } else {
//            int count = cursor == null ? 0 : cursor.getCount();
//            if (directoryId != Directory.DEFAULT && directoryId != Directory.LOCAL_INVISIBLE
//                    && count >= getDirectoryResultLimit()) {
//                countText.setText(mContext.getString(
//                        R.string.foundTooManyContacts, getDirectoryResultLimit()));
//            } else {
//                countText.setText(getQuantityText(
//                        count, R.string.listFoundAllContactsZero, R.plurals.searchFoundContacts));
//            }
//        }
    }
}
