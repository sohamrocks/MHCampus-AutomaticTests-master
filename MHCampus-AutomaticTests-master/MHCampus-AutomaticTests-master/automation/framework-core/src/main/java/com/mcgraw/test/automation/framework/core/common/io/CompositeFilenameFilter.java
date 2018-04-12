package com.mcgraw.test.automation.framework.core.common.io;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

/**
 * Composite filename filter with OR and AND filtering
 *
 * @author Andrei Varabyeu
 *
 */
public abstract class CompositeFilenameFilter implements FilenameFilter {

	public static CompositeFilenameFilter or(List<FilenameFilter> filters) {
		return new OrFileNameFilter(filters);
	}

	public static CompositeFilenameFilter or(FilenameFilter... filters) {
		return new OrFileNameFilter(Arrays.asList(filters));
	}

	public static CompositeFilenameFilter and(List<FilenameFilter> filters) {
		return new AndFileNameFilter(filters);
	}

	public static CompositeFilenameFilter and(FilenameFilter... filters) {
		return new AndFileNameFilter(Arrays.asList(filters));
	}

	protected List<FilenameFilter> filenameFilters;

	private CompositeFilenameFilter(List<FilenameFilter> filters) {
		filenameFilters = Preconditions.checkNotNull(filters,
				"Filters shouldn't be null");
	}

	private static class AndFileNameFilter extends CompositeFilenameFilter {

		public AndFileNameFilter(List<FilenameFilter> filters) {
			super(filters);
		}

		public boolean accept(File file, String name) {
			if (this.filenameFilters.isEmpty()) {
				return false;
			}
			for (FilenameFilter fileFilter : this.filenameFilters) {
				if (!(fileFilter.accept(file, name))) {
					return false;
				}
			}
			return true;
		}
	}

	private static class OrFileNameFilter extends CompositeFilenameFilter {

		public OrFileNameFilter(List<FilenameFilter> filters) {
			super(filters);
		}

		public boolean accept(File file, String name) {
			for (FilenameFilter fileFilter : this.filenameFilters) {
				if (fileFilter.accept(file, name)) {
					return true;
				}
			}
			return false;
		}
	}
}
