package com.example.akihiro.schoolwork8.Common.Usuful;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

public class Texture {
	Context context;
	int textureIndex;

	Texture(Context context) {
		this.context = context;
	}

	public void load(String filename, GL10 gl) {
		try {
			InputStream stream = context.getResources().getAssets()
					.open(filename);
			Bitmap bitmap = BitmapFactory.decodeStream(stream);

			int[] texture = new int[1];
			gl.glGenTextures(1, texture, 0);
			textureIndex = texture[0];

			gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIndex);
			gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
			gl.glTexParameterx(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
			GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

			bitmap.recycle();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void set(GL10 gl) {
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureIndex);
	}
}
