package com.fennec.e_media.myInterface;

public interface IonHandler {

    public void onSucces(Object obj);
    public void onFailed(Object obj);

    /**200 => 'OK',
     201 => 'Created',
     204 => 'No Content',
     404 => 'Not Found',
     406 => 'Not Acceptable',
     401 => 'Unauthorized'*/
}
