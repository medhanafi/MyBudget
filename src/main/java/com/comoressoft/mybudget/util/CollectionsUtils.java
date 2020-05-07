package com.comoressoft.mybudget.util;


import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CollectionsUtils {

	public static <T, R> List<R> apply(Collection<T> coll, Function<? super T, ? extends R> mapper) {
		return coll.stream().map(mapper).collect(Collectors.toList());
	}

}