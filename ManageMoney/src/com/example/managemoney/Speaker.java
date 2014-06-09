package com.example.managemoney;

import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class Speaker {
	private TextToSpeech ttobj;

	public Speaker(Context context) {
		ttobj = new TextToSpeech(context,
				new TextToSpeech.OnInitListener() {

					@Override
					public void onInit(int status) {
						// TODO Auto-generated method stub
						if (status != TextToSpeech.ERROR) {
							ttobj.setLanguage(Locale.ENGLISH);
						}
					}
				});
	}

	public void speakText(String toSpeak) {
		ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
	}
}
