package com.ksh.soundstory.results;

//public interface Result{
//    String name();
//}

public interface Result<T extends Result<T>> {
    String name();
}
