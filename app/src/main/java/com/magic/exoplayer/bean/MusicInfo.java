package com.magic.exoplayer.bean;

import android.net.Uri;

/**
 * @author Magic
 * @date on 2019/9/22
 * @description
 */
public class MusicInfo {
    private String musicName;
    private Uri uri;

    public String getMusicName() {
        return musicName;
    }

    public void setMusicName(String musicName) {
        this.musicName = musicName;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public String toString() {
        return "MusicInfo{" +
                "musicName='" + musicName + '\'' +
                ", uri=" + uri +
                '}';
    }
}
