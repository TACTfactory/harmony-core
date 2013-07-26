package ${project_namespace}.provider.utils.base;

import java.util.ArrayList;

import android.content.Context;

public abstract class ProviderUtilsBase<T> {
	public abstract int insert(final Context ctx, final T item);
	public abstract int delete(final Context ctx, final T item);
	public abstract T query(final Context ctx, final int id);
	public abstract ArrayList<T> queryAll(final Context ctx);
	public abstract int update(final Context ctx, final T item);
}

