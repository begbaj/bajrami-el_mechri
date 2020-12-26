package com.umidity.api;

public class IsReadOnlyException extends Exception{
    IsReadOnlyException(){
    super("is read only");
    }
}
