package com.example.akihiro.schoolwork8.Common.Usuful;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

class Material {
	public String name;
	public float ambient[] = new float[4];
	public float diffuse[] = new float[4];
	public float specular[] = new float[4];
	public float shininess;
	public Texture texture;
}

class Subset {
	Material material;
	public int startIndex;
	public int numIndex;
}

public class ObjLoader {

	private Context context = null;

	private FloatBuffer vertexBuffer;
	private float[] vertexData = null;
	private int vertexNum;

	private FloatBuffer normalBuffer;
	private float[] normalData = null;
	private int normalNum;

	private FloatBuffer texcoordBuffer;
	private float[] texcoordData = null;
	private int texcoordNum;

	private ShortBuffer indexBuffer;
	private short[] indexData = null;
	private int indexNum;

	int bufferObject[];

	ArrayList<Subset> subset = new ArrayList<Subset>();
	ArrayList<Material> material = new ArrayList<Material>();

	public ObjLoader(Context c) {
		context = c;
	}

	// マテリアル読み込み　指定したファイルネームから
	public void loadMaterial(String filename, GL10 gl) {
		AssetManager asset = context.getResources().getAssets();
		InputStream stream = null;
		BufferedReader reader = null;

		try {
			stream = asset.open(filename);
			reader = new BufferedReader(new InputStreamReader(stream));

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.length() < 3)
					continue;

				String s[] = line.split(" ");

				if (s[0].equals("newmtl")) {
					// マテリアル名
					Material mat = new Material();
					mat.name = s[1];
					mat.texture = null;
					material.add(mat);
				} else if (s[0].equals("Ka")) {
					// アンビエント
					Material mat;
					mat = material.get(material.size() - 1);

					mat.ambient[0] = Float.parseFloat(s[1]);
					mat.ambient[1] = Float.parseFloat(s[2]);
					mat.ambient[2] = Float.parseFloat(s[3]);
					mat.ambient[3] = 1.0f;
				} else if (s[0].equals("Kd")) {
					// ディフューズ
					Material mat;
					mat = material.get(material.size() - 1);

					mat.diffuse[0] = Float.parseFloat(s[1]);
					mat.diffuse[1] = Float.parseFloat(s[2]);
					mat.diffuse[2] = Float.parseFloat(s[3]);
					mat.diffuse[3] = 1.0f;
				} else if (s[0].equals("Ks")) {
					// スペキュラ
					Material mat;
					mat = material.get(material.size() - 1);

					mat.specular[0] = Float.parseFloat(s[1]);
					mat.specular[1] = Float.parseFloat(s[2]);
					mat.specular[2] = Float.parseFloat(s[3]);
					mat.specular[3] = 1.0f;
				} else if (s[0].equals("Ns")) {
					// スペキュラ強度
					Material mat;
					mat = material.get(material.size() - 1);
					mat.shininess = Float.parseFloat(s[1]);
				} else if (s[0].equals("d")) {
					// アルファ
					Material mat;
					mat = material.get(material.size() - 1);
					mat.diffuse[3] = Float.parseFloat(s[1]);
				} else if (s[0].equals("map_Kd")) {
					// テクスチャ
					Texture texture;
					texture = new Texture(context);
					texture.load(s[1], gl);

					Material mat;
					mat = material.get(material.size() - 1);
					mat.texture = texture;
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void load(String filename, GL10 gl) {
		ArrayList<Float> vertex = new ArrayList<Float>();
		ArrayList<Float> vertex2 = new ArrayList<Float>();

		ArrayList<Float> texcoord = new ArrayList<Float>();
		ArrayList<Float> texcoord2 = new ArrayList<Float>();

		ArrayList<Float> normal = new ArrayList<Float>();
		ArrayList<Float> normal2 = new ArrayList<Float>();

		ArrayList<Short> index = new ArrayList<Short>();

        //Assetsのリソースの場所を取得している。
		AssetManager asset = context.getResources().getAssets();
		InputStream stream = null;
		BufferedReader reader = null;

		try {
			stream = asset.open(filename);
			reader = new BufferedReader(new InputStreamReader(stream));

			short id = 0;

			String line;
			while ((line = reader.readLine()) != null) {
				if (line.length() < 3)
					continue;

				String s[] = line.split(" ");

				if (s[0].equals("mtllib")) {
					// マテリアルファイル
					loadMaterial(s[1], gl);
				} else if (s[0].equals("v")) {//頂点情報がｖで始まる
					// 頂点
					vertex.add(Float.parseFloat(s[1])); // x
					vertex.add(Float.parseFloat(s[2])); // y
					vertex.add(Float.parseFloat(s[3])); // z
				} else if (s[0].equals("vt")) {
					// テクスチャ座標
					texcoord.add(Float.parseFloat(s[1])); // u
					texcoord.add(Float.parseFloat(s[2])); // v
				} else if (s[0].equals("vn")) {
					// 法線
					normal.add(Float.parseFloat(s[1])); // x
					normal.add(Float.parseFloat(s[2])); // y
					normal.add(Float.parseFloat(s[3])); // z
				} else if (s[0].equals("usemtl")) {
					// マテリアル
					if (subset.size() > 0) {
						Subset oldsub = subset.get(subset.size() - 1);
						oldsub.numIndex = index.size() - oldsub.startIndex;
					}

					Material mat = null;

					for (int i = 0; i < material.size(); i++) {
						mat = material.get(i);

						if (mat.name.equals(s[1])) {
							break;
						}
					}

					Subset sub = new Subset();
					sub.material = mat;
					sub.startIndex = index.size();

					subset.add(sub);
				} else if (s[0].equals("f")) {
					// 面
					if (s.length == 4) {
						// 三角形
						for (int j = 0; j < 3; j++) {
							String ss[] = s[j + 1].split("/");

							int v = (Integer.parseInt(ss[0]) - 1) * 3;
							vertex2.add(vertex.get(v));
							vertex2.add(vertex.get(v + 1));
							vertex2.add(vertex.get(v + 2));

							if (ss[1].length() != 0) {
								int t = (Integer.parseInt(ss[1]) - 1) * 2;
								texcoord2.add(texcoord.get(t));
								texcoord2.add(texcoord.get(t + 1));
							} else {
								texcoord2.add(0.0f);
								texcoord2.add(0.0f);
							}

							int n = (Integer.parseInt(ss[2]) - 1) * 3;
							normal2.add(normal.get(n));
							normal2.add(normal.get(n + 1));
							normal2.add(normal.get(n + 2));

						}

						index.add((short) (id + 0));
						index.add((short) (id + 1));
						index.add((short) (id + 2));
						id += 3;

					} else if (s.length == 5) {
						// 四角形
						for (int j = 0; j < 4; j++) {
							String ss[] = s[j + 1].split("/");

							int v = (Integer.parseInt(ss[0]) - 1) * 3;
							vertex2.add(vertex.get(v));
							vertex2.add(vertex.get(v + 1));
							vertex2.add(vertex.get(v + 2));

							if (ss[1].length() != 0) {
								int t = (Integer.parseInt(ss[1]) - 1) * 2;
								texcoord2.add(texcoord.get(t));
								texcoord2.add(texcoord.get(t + 1));
							} else {
								texcoord2.add(0.0f);
								texcoord2.add(0.0f);
							}

							int n = (Integer.parseInt(ss[2]) - 1) * 3;
							normal2.add(normal.get(n));
							normal2.add(normal.get(n + 1));
							normal2.add(normal.get(n + 2));
						}

						index.add((short) (id + 0));
						index.add((short) (id + 1));
						index.add((short) (id + 2));

						index.add((short) (id + 2));
						index.add((short) (id + 3));
						index.add((short) (id + 0));

						id += 4;
					}
				}
			}

			Subset oldsub = subset.get(subset.size() - 1);
			oldsub.numIndex = index.size() - oldsub.startIndex;

			vertexNum = vertex2.size();
			vertexData = new float[vertexNum];
			for (int i = 0; i < vertexNum; i++) {
				vertexData[i] = vertex2.get(i);
			}
			vertexBuffer = FloatBuffer.wrap(vertexData);

			texcoordNum = texcoord2.size();
			texcoordData = new float[texcoordNum];
			for (int i = 0; i < texcoordNum; i++) {
				texcoordData[i] = texcoord2.get(i);
			}
			texcoordBuffer = FloatBuffer.wrap(texcoordData);

			normalNum = normal2.size();
			normalData = new float[normalNum];
			for (int i = 0; i < normalNum; i++) {
				normalData[i] = normal2.get(i);
			}
			normalBuffer = FloatBuffer.wrap(normalData);

			indexNum = index.size();
			indexData = new short[indexNum];
			for (int i = 0; i < indexNum; i++) {
				indexData[i] = index.get(i);
			}
			indexBuffer = ShortBuffer.wrap(indexData);

			GL11 gl11 = (GL11) gl;

			bufferObject = new int[4];
			gl11.glGenBuffers(4, bufferObject, 0);

			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
			gl11.glBufferData(GL11.GL_ARRAY_BUFFER, 4 * vertexNum,
					vertexBuffer, GL11.GL_STATIC_DRAW);

			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[1]);
			gl11.glBufferData(GL11.GL_ARRAY_BUFFER, 4 * texcoordNum,
					texcoordBuffer, GL11.GL_STATIC_DRAW);

			gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[2]);
			gl11.glBufferData(GL11.GL_ARRAY_BUFFER, 4 * normalNum,
					normalBuffer, GL11.GL_STATIC_DRAW);

			gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, bufferObject[3]);
			gl11.glBufferData(GL11.GL_ELEMENT_ARRAY_BUFFER, 2 * indexNum,
					indexBuffer, GL11.GL_STATIC_DRAW);

			gl11.glEnableClientState(GL11.GL_VERTEX_ARRAY);
			gl11.glEnableClientState(GL11.GL_TEXTURE_COORD_ARRAY);
			gl11.glEnableClientState(GL11.GL_NORMAL_ARRAY);

		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void setContext(Context con)
	{
		this.context = con;
	}

	public void draw(GL10 gl) {
		GL11 gl11 = (GL11) gl;

		// 頂点バッファバインド
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[0]);
		gl11.glVertexPointer(3, GL11.GL_FLOAT, 0, 0);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

		// テクスチャ座標バッファバインド
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[1]);
		gl11.glTexCoordPointer(2, GL11.GL_FLOAT, 0, 0);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

		// 法線バッファバインド
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, bufferObject[2]);
		gl11.glNormalPointer(GL11.GL_FLOAT, 0, 0);
		gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);

		// インデックスバッファバインド
		gl11.glBindBuffer(GL11.GL_ELEMENT_ARRAY_BUFFER, bufferObject[3]);

		// 描画
		for (int i = 0; i < subset.size(); i++) {
			Subset sub = subset.get(i);

			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT,
					sub.material.ambient, 0);
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE,
					sub.material.diffuse, 0);
			gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR,
					sub.material.specular, 0);
			gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS,
					sub.material.shininess);

			// マテリアル内指定のテクスチャを使う場合
			if( sub.material.texture != null ) {
				sub.material.texture.set(gl); } else { gl.glBindTexture(
					GL10.GL_TEXTURE_2D, 0 ); }

			gl11.glDrawElements(GL11.GL_TRIANGLES, sub.numIndex,
					GL11.GL_UNSIGNED_SHORT, sub.startIndex * 2);
		}
	}
}
