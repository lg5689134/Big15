/**
 * @filename JavaSpeech.java
 * @author lg
 * @date 2019年3月29日 下午2:55:38
 * @version 1.0
 * Copyright (C) 2019 
 */

package com.byzx.speech;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

public class JavaSpeech {
	public static void main(String[] args) {
	ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
	Dispatch sapo = sap.getObject();
	try {
		// 音量 0-100  
		sap.setProperty("Volume", new Variant(100));
		// 语音朗读速度 -10 到 +10  
		sap.setProperty("Rate", new Variant(2));
		// 执行朗读  
		Dispatch.call(sapo, "Speak", new Variant("java,unique new 呈現出的訂單！"));
			
		} catch (Exception e) {
		e.printStackTrace();
		} finally {
		sapo.safeRelease();
		sap.safeRelease();
		}

	}
}
